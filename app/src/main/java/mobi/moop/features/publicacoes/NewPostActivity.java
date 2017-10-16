package mobi.moop.features.publicacoes;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.condominio.CondominioPreferences;
import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.rotas.RotaFeed;
import mobi.moop.model.rotas.impl.RotaFeedImpl;
import mobi.moop.model.singleton.UsuarioSingleton;
import mobi.moop.utils.FotoUtils;

public class NewPostActivity extends AppCompatActivity implements RotaFeed.FeedPublishHandler {


    private static final int CAMERA = 1;
    private static final int GALERIA = 2;

    @BindView(R.id.imgPost)
    ImageView imgPost;

    @BindView(R.id.icRemoveImage)
    ImageView imgRemoveImage;

    @BindView(R.id.toolbarPublicacao)
    Toolbar toolbar;

    @BindView(R.id.fabEscolherFoto)
    FloatingActionButton fabEscolherFoto;

    @BindView(R.id.editText)
    EditText editText;

    private String mCurrentPhotoPath;

    private File avatar;

    private RotaFeedImpl rotaFeed = new RotaFeedImpl();

    private Long condominioId;
    private ProgressDialog progressDialog;

    @OnClick(R.id.fabEscolherFoto)
    public void fabEscolherFotoAction(View view) {
        createDialogAddFoto();
    }


    @OnClick(R.id.icRemoveImage)
    public void apagarImagem(View view) {
        mCurrentPhotoPath = null;
        avatar = null;
        imgPost.setVisibility(View.GONE);
        imgRemoveImage.setVisibility(View.GONE);
        fabEscolherFoto.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupProgressDialog();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        condominioId = CondominioPreferences.I.getLastSelectedCondominio(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rotaFeed.cancelPostFeedRequisition();
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.aguarde));
        progressDialog.setMessage(getString(R.string.criando_publicacao));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publicacao, menu);
        MenuItem menuItem = menu.findItem(R.id.publicar);
        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    publicar();
                }
            }
        });
        return true;
    }

    private boolean validate() {
        if (avatar == null && editText.getText().toString().equals("")) {
            editText.setError(getString(R.string.campo_obrigatorio));
            return false;
        }
        return true;
    }

    private void publicar() {
        progressDialog.show();
        String text = null;
        if (!editText.getText().toString().equals("")) {
            text = editText.getText().toString();
        }
        rotaFeed.publish(this, UsuarioSingleton.I.getUsuarioLogado(this), condominioId, text, avatar, this);
    }

    private void createDialogAddFoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.selecione_acao)
                .setItems(R.array.foto_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                requestCameraPermission(CAMERA);
                                break;

                            default:
                                requestReadAndWritePermission(GALERIA);
                                break;

                        }
                    }
                });
        builder.show();
    }

    private void requestCameraPermission(int from) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, from);
            } else {
                openCamera(from);
            }
        } else {
            openCamera(from);
        }
    }

    private void openCamera(int from) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = FotoUtils.getOutputMediaFile();
            if (photoFile == null) {
                Toast.makeText(this, getString(R.string.app_nao_conseguiu_criar_diretorio), Toast.LENGTH_LONG).show();
                return;
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            mCurrentPhotoPath = photoFile.getAbsolutePath();
            startActivityForResult(cameraIntent, from);
        }
    }

    private void requestReadAndWritePermission(int from) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, from);
            } else {
                openGalery(from);
            }
        } else {
            openGalery(from);
        }
    }

    private void openGalery(int from) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), from);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == GALERIA) {
                openGalery(requestCode);
            } else if (requestCode == CAMERA) {
                openCamera(requestCode);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                ajdustPicture(this, mCurrentPhotoPath);
            } else if (requestCode == GALERIA) {
                Uri uri = data.getData();
                openCropActivity(uri);
            } else if (requestCode == UCrop.REQUEST_CROP) {
                onPhotoCropped(UCrop.getOutput(data));
            }
        }

    }

    private void ajdustPicture(Context context, String mCurrentPhotoPath) {
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        options.setHideBottomControls(true);
        options.setToolbarColor(context.getResources().getColor(R.color.colorPrimary));
        options.setStatusBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarTitle(context.getString(R.string.cortar));
        options.setMaxBitmapSize(1920);
        options.withMaxResultSize(1920, 1920);
        Uri destination = Uri.fromFile(new File(mCurrentPhotoPath));
        openCropActivity(options, destination);
    }

    private void openCropActivity(UCrop.Options options, Uri destination) {
        UCrop.of(destination, destination)
                .withOptions(options)
                .start(this);
    }

    public void openCropActivity(UCrop.Options options, Uri source, Uri destination) {
        UCrop.of(source, destination).withOptions(options).start(this);
    }

    public void openCropActivity(Uri source) {
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        options.setHideBottomControls(true);
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarTitle(getString(R.string.cortar));
        options.setMaxBitmapSize(1920);
        options.withMaxResultSize(1920, 1920);
        Uri destination = Uri.fromFile(FotoUtils.getOutputMediaFile());

        openCropActivity(options, source, destination);
    }

    public void onPhotoCropped(Uri output) {
        imgPost.setVisibility(View.VISIBLE);
        imgRemoveImage.setVisibility(View.VISIBLE);
        fabEscolherFoto.setVisibility(View.GONE);
        String croppedPath = FotoUtils.getPath(this, output);
        FotoUtils.sendPictureBroadcast(croppedPath, this);
        avatar = new File(croppedPath);
        Picasso.with(this).load(avatar).into(imgPost);

    }

    @Override
    public void onFeedPublised(FeedItem feedItem) {
        progressDialog.dismiss();
        setResult(Activity.RESULT_OK);
        finish();

    }

    @Override
    public void onFeedPublishFail(String error) {
        progressDialog.dismiss();
    }
}
