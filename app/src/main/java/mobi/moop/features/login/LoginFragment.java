package mobi.moop.features.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.MoopActivity;
import mobi.moop.model.rotas.RotaUsuario;
import mobi.moop.model.rotas.impl.RotaLoginImpl;

public class LoginFragment extends Fragment implements Validator.ValidationListener, RotaUsuario.LoginHandler, GoogleApiClient.OnConnectionFailedListener, RotaUsuario.RegistroHandler {
    private static final int RC_SIGN_IN = 0;
    private LoginActivity loginActivity;
    private Validator validator;
    private JSONObject facebookJson;
    private GoogleSignInAccount acct;
    private ProgressDialog loginDialog;

    @OnClick(R.id.textCriarConta)
    public void textCriarContaAction(View view) {
        loginActivity.showRegistroFragment();
    }

    @OnClick(R.id.btnLogarGoogle)
    public void btnLogarGoogleAction(View view) {
        loginGoogle();
    }

    @OnClick(R.id.btnLogarFacebook)
    public void btnLogarFacebookAction(View view) {
        loginFacebook();
    }

    @Email(messageResId = R.string.email_invalido)
    @BindView(R.id.editEmail)
    EditText editEmail;

    @Password(messageResId = R.string.campo_obrigatorio)
    @BindView(R.id.editSenha)
    EditText editSenha;

    @OnClick(R.id.btnEntrar)
    public void btnEntrarAction(View view) {
        validator.validate();
    }

    private RotaLoginImpl login = new RotaLoginImpl();
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        configureGoogleApiClient();
        configureFacebookCallBack();
        createLoginDialog();
        validator = new Validator(this);
        validator.setValidationListener(this);
        return view;
    }

    private void createLoginDialog() {
        loginDialog = new ProgressDialog(loginActivity);
        loginDialog.setIndeterminate(true);
        loginDialog.setCancelable(false);
        loginDialog.setTitle(getString(R.string.aguarde));
        loginDialog.setMessage(getString(R.string.efetuando_login));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.loginActivity = (LoginActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginActivity.hideToolbar();
    }

    @Override
    public void onValidationSucceeded() {
        performLogin(editEmail.getText().toString(), editSenha.getText().toString(), LoginActivity.LOGIN_MOOP);

    }

    private void performLogin(String email, String password, String loginType) {
        loginDialog.show();
        login.login(loginActivity, email, password, FirebaseInstanceId.getInstance().getToken(), "android", loginType, this);
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

    private void configureFacebookCallBack() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            facebookJson = object;
                                            performLogin(object.getString("email"), null, LoginActivity.LOGIN_FACEBOOK);
                                        } catch (JSONException e) {

                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name,email,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }
                });

    }

    private void configureGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            acct = result.getSignInAccount();
            performLogin(acct.getEmail(), null, LoginActivity.LOGIN_GOOGLE);
        } else {
        }
    }

    @Override
    public void onLogin() {
        loginDialog.dismiss();
        startActivity(new Intent(loginActivity, MoopActivity.class));
        getActivity().finish();
    }

    @Override
    public void onLoginError(String error) {
        loginDialog.dismiss();
        Toast.makeText(loginActivity, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotFound(String logadoCom) {
        switch (logadoCom) {
            case LoginActivity.LOGIN_FACEBOOK:
                try {
                    login.registrar(loginActivity, facebookJson.getString("name"), facebookJson.getString("email"), "", new JSONObject(facebookJson.getString("picture")).getJSONObject("data").getString("url"), logadoCom, FirebaseInstanceId.getInstance().getToken(), "android", null, this);
                } catch (JSONException e) {
                    Log.e("JSONError", e.getMessage());
                }
                break;
            case LoginActivity.LOGIN_GOOGLE:
                login.registrar(loginActivity, acct.getDisplayName(), acct.getEmail(), "", acct.getPhotoUrl() == null ? null : acct.getPhotoUrl().toString(), logadoCom, FirebaseInstanceId.getInstance().getToken(), "android", null, this);
                break;
            default:
                loginDialog.dismiss();
                Toast.makeText(loginActivity, getString(R.string.dados_invalidos), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void loginGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
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
    }
}
