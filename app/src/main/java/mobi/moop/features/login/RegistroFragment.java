package mobi.moop.features.login;


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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.MoopActivity;
import mobi.moop.model.rotas.RotaUsuario;
import mobi.moop.model.rotas.impl.RotaLoginImpl;
import mobi.moop.utils.FotoUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment implements Validator.ValidationListener, RotaUsuario.RegistroHandler {

    private static final int CAMERA = 1;
    private static final int GALERIA = 2;

    @NotEmpty(messageResId = R.string.campo_obrigatorio)
    @BindView(R.id.editNome)
    EditText editNome;

    @Email
    @NotEmpty(messageResId = R.string.campo_obrigatorio)
    @BindView(R.id.editEmal)
    EditText editEmail;

    @Password
    @BindView(R.id.editSenha)
    EditText editSenha;

    @ConfirmPassword(messageResId = R.string.senhas_diferentes)
    @BindView(R.id.editConfirmarSenha)
    EditText editConfirmarSenha;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    private String mCurrentPhotoPath;
    private Validator validator;
    private RotaLoginImpl rotaLogin = new RotaLoginImpl();
    private File avatar;
    private ProgressDialog loginDialog;

    @OnClick(R.id.imgAvatar)
    public void imgAvatarAction(View view) {
        createDialogAddFoto();
    }

    @OnClick(R.id.btnCadastrar)
    public void btnCadastrarAction(View view) {
        validator.validate();
    }


    private void createDialogAddFoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    private void selectPicture() {
    }


    private LoginActivity loginActivity;

    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        ButterKnife.bind(this, view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        createLoginDialog();
        return view;
    }

    private void createLoginDialog() {
        loginDialog = new ProgressDialog(loginActivity);
        loginDialog.setIndeterminate(true);
        loginDialog.setCancelable(false);
        loginDialog.setTitle(getString(R.string.aguarde));
        loginDialog.setMessage(getString(R.string.registrando));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.loginActivity = (LoginActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginActivity.showToolbar();
    }

    @Override
    public void onStop() {
        super.onStop();
        rotaLogin.cancelRegistrarRequisition();
    }

    private void requestCameraPermission(int from) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
        if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = FotoUtils.getOutputMediaFile();
            if (photoFile == null) {
                Toast.makeText(getContext(), getString(R.string.app_nao_conseguiu_criar_diretorio), Toast.LENGTH_LONG).show();
                return;
            }
            Uri photoUri = null;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                photoUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".my.package.name.provider", photoFile);
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
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
                ajdustPicture(getContext(), mCurrentPhotoPath);
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
                .start(getActivity(), this);
    }

    public void openCropActivity(UCrop.Options options, Uri source, Uri destination) {
        UCrop.of(source, destination).withOptions(options).start(getActivity(), this);
    }

    public void openCropActivity(Uri source) {
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        options.setHideBottomControls(true);
        options.setToolbarColor(getContext().getResources().getColor(R.color.colorPrimary));
        options.setStatusBarColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarTitle(getContext().getString(R.string.cortar));
        options.setMaxBitmapSize(1920);
        options.withMaxResultSize(1920, 1920);
        Uri destination = Uri.fromFile(FotoUtils.getOutputMediaFile());

        openCropActivity(options, source, destination);
    }

    public void onPhotoCropped(Uri output) {
        String croppedPath = FotoUtils.getPath(getContext(), output);
        FotoUtils.sendPictureBroadcast(croppedPath, getContext());
        avatar = new File(croppedPath);
        Picasso.with(getContext()).load(avatar).into(imgAvatar);

    }

    @Override
    public void onValidationSucceeded() {
        loginDialog.show();
        rotaLogin.registrar(getContext(), editNome.getText().toString(), editEmail.getText().toString(), editSenha.getText().toString(), null, LoginActivity.LOGIN_MOOP, FirebaseInstanceId.getInstance().getToken(), "android", avatar, this);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onUserRegistred() {
        loginDialog.dismiss();
        startActivity(new Intent(loginActivity, MoopActivity.class));
        getActivity().finish();
    }

    @Override
    public void onRegistrationFail(String error) {
        loginDialog.dismiss();
        Toast.makeText(loginActivity, error, Toast.LENGTH_SHORT).show();
    }
}
