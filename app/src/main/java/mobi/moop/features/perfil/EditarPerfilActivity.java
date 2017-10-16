package mobi.moop.features.perfil;

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
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import mobi.moop.model.rotas.RotaUsuario;
import mobi.moop.model.rotas.impl.RotaLoginImpl;
import mobi.moop.model.singleton.UsuarioSingleton;
import mobi.moop.utils.FotoUtils;

public class EditarPerfilActivity extends AppCompatActivity implements RotaUsuario.UpdateHandler {
    private static final int CAMERA = 1;
    private static final int GALERIA = 2;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.editNome)
    EditText editNome;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ProgressDialog loginDialog;

    @OnClick(R.id.btnConfirmar)
    public void btnCadastrarAction(View view) {
        editarPerdil();
    }

    private File avatar;
    private RotaLoginImpl rotaLogin = new RotaLoginImpl();

    @OnClick(R.id.imgAvatar)
    public void imgAvatarAction(View view) {
        createDialogAddFoto();
    }

    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createLoginDialog();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editNome.setText(UsuarioSingleton.I.getUsuarioLogado(this).getNome());
        if (!UsuarioSingleton.I.getUsuarioLogado(this).getAvatar().equals("")) {
            Picasso.with(this).load(UsuarioSingleton.I.getUsuarioLogado(this).getAvatar()).into(imgAvatar);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        rotaLogin.cancelUpdateProfileRequisition();
    }

    private void createLoginDialog() {
        loginDialog = new ProgressDialog(this);
        loginDialog.setIndeterminate(true);
        loginDialog.setCancelable(false);
        loginDialog.setTitle(getString(R.string.aguarde));
        loginDialog.setMessage(getString(R.string.atualizando));
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
            Uri photoUri = null;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".my.package.name.provider", photoFile);
            } else {
                photoUri = Uri.fromFile(photoFile);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
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

    public void openCropActivity(UCrop.Options options, Uri source, Uri destination) {
        UCrop.of(source, destination).withOptions(options).start(this);
    }

    public void onPhotoCropped(Uri output) {
        String croppedPath = FotoUtils.getPath(this, output);
        FotoUtils.sendPictureBroadcast(croppedPath, this);
        avatar = new File(croppedPath);
        Picasso.with(this).load(avatar).into(imgAvatar);
    }

    private void editarPerdil() {
        if (editNome.getText().toString().equals("")) {
            editNome.setError(getString(R.string.campo_obrigatorio));
            return;
        }
        loginDialog.show();
        rotaLogin.atualizar(this, editNome.getText().toString(), avatar, this);
    }

    @Override
    public void onUserUpdated() {
        loginDialog.dismiss();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onUpdateFail(String error) {
        loginDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
