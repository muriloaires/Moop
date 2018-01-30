package brmobi.moop.features.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import brmobi.moop.features.MoopActivity;
import brmobi.moop.model.rotas.RotaUsuario;
import brmobi.moop.model.rotas.impl.RotaLoginImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;

public class LoginFragment extends Fragment implements RotaUsuario.LoginHandler, RotaUsuario.RegistroHandler {
    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final int STATE_CODE_TIMEOUT = 7;
    private static final int STATE_SENDING_CODE = 8;

    private int actualState;

    private LoginActivity loginActivity;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @BindView(R.id.country_picker)
    CountryCodePicker cpp;

    @BindView(R.id.editNumeroTelefone)
    EditText editNumeroTelefone;

    @BindView(R.id.btn_enviar_codigo)
    Button btnEnviarCodigo;

    @BindView(R.id.editCodigoVerificacao)
    EditText editCodigoVerificacao;

    @BindView(R.id.textCountdown)
    TextView textCountdown;

    @BindView(R.id.btnReenviarCodigo)
    TextView btnReenviarCodigo;

    @BindView(R.id.textStatus)
    TextView textStatus;

    @BindView(R.id.img)
    ImageView imageView;

    private CountDownTimer countDownTimer;

    @OnClick(R.id.btnlogout)
    public void logout(View view) {
        updateUI();
        mAuth.signOut();
    }

    @OnClick(R.id.btnReenviarCodigo)
    public void reenviarCodigo(View view) {
        if (validateNumber()) {
            actualState = STATE_SENDING_CODE;
            updateUI();
            resendVerificationCode(editNumeroTelefone.getText().toString(), mResendToken);
        }
    }

    @OnClick(R.id.btn_enviar_codigo)
    public void btnEnviarCodigoAction(View view) {
        if (validateNumber()) {
            actualState = STATE_SENDING_CODE;
            updateUI();
            startPhoneNumberVerification(editNumeroTelefone.getText().toString());
        }
    }

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    private RotaLoginImpl login = new RotaLoginImpl();
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
            mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
        }

        configureCallBack();
        cpp.registerCarrierNumberEditText(editNumeroTelefone);
        editCodigoVerificacao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    actualState = STATE_VERIFY_SUCCESS;
                    updateUI();
                    countDownTimer.cancel();
                    verifyPhoneNumberWithCode(mVerificationId, charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        updateUI();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            actualState = STATE_SIGNIN_SUCCESS;
            updateUI();
            performLogin(user);
        } else {
            if (mVerificationInProgress && validateNumber()) {
                actualState = STATE_SENDING_CODE;
                updateUI();
                startPhoneNumberVerification(editNumeroTelefone.getText().toString());
            } else {
                actualState = STATE_INITIALIZED;
                updateUI();
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }


    private void configureCallBack() {
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                actualState = STATE_VERIFY_SUCCESS;
                countDownTimer.cancel();
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI();
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                actualState = STATE_VERIFY_FAILED;
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                updateUI();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                actualState = STATE_CODE_SENT;
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                updateUI();
                // [END_EXCLUDE]
            }
        };
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                textCountdown.setText("Validade do código " + (millisUntilFinished / 1000) + " segundo(s)");
            }

            public void onFinish() {
                actualState = STATE_CODE_TIMEOUT;
                updateUI();
            }
        };
        countDownTimer.start();
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            actualState = STATE_SIGNIN_SUCCESS;
                            updateUI();
                            FirebaseUser user = task.getResult().getUser();
                            performLogin(user);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            actualState = STATE_SIGNIN_FAILED;
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]

                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            actualState = STATE_SIGNIN_FAILED;
                            updateUI();
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.loginActivity = (LoginActivity) context;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        loginActivity.hideToolbar();
    }

    @Override
    public void onStop() {
        super.onStop();
        login.cancelLoginRequisition();
        login.cancelRegistrarRequisition();
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        phoneNumber = "+" + cpp.getSelectedCountryCode() + phoneNumber;
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                loginActivity,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                loginActivity,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void performLogin(FirebaseUser user) {
        login.login(loginActivity, user.getPhoneNumber(), FirebaseInstanceId.getInstance().getToken(), "android", this);
    }

    public void showViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    private boolean validateNumber() {
        return !TextUtils.isEmpty(editNumeroTelefone.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onLogin() {
        startActivity(new Intent(loginActivity, MoopActivity.class));
        getActivity().finish();
    }

    @Override
    public void onLoginError(String error) {
        Toast.makeText(loginActivity, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotFound() {
        loginActivity.showRegistroFragment();
    }


    @Override
    public void onUserRegistred() {
        startActivity(new Intent(loginActivity, MoopActivity.class));
        getActivity().finish();
    }

    @Override
    public void onRegistrationFail(String error) {
    }

    public void updateUI() {
        switch (actualState) {
            case STATE_INITIALIZED:
                textStatus.setText(getString(R.string.insira_seu_telefone));
                showViews(editNumeroTelefone, cpp, btnEnviarCodigo, textStatus);
                hideViews(editCodigoVerificacao, btnReenviarCodigo, mProgressBar, textCountdown);
                break;
            case STATE_CODE_SENT:
                textStatus.setText(getString(R.string.insira_o_codigo_verificacao));
                showViews(editCodigoVerificacao, textCountdown, textStatus);
                hideViews(editNumeroTelefone, cpp, btnEnviarCodigo, mProgressBar, btnReenviarCodigo);
                startCountdown();
                break;
            case STATE_VERIFY_SUCCESS:
                textStatus.setText(getString(R.string.validando_codigo));
                showViews(mProgressBar, textStatus);
                hideViews(editNumeroTelefone, editCodigoVerificacao, cpp, btnEnviarCodigo, btnReenviarCodigo, textCountdown);
                break;
            case STATE_VERIFY_FAILED:
                textStatus.setText(getString(R.string.status_verification_failed));
                showViews(editNumeroTelefone, cpp, btnEnviarCodigo, textStatus);
                hideViews(editCodigoVerificacao, btnReenviarCodigo, mProgressBar, textCountdown);
                break;
            case STATE_SIGNIN_SUCCESS:
                textStatus.setText(getString(R.string.entrando_no_moop));
                showViews(mProgressBar, textStatus);
                hideViews(editNumeroTelefone, editCodigoVerificacao, cpp, btnEnviarCodigo, btnReenviarCodigo, textCountdown);
                break;
            case STATE_SIGNIN_FAILED:
                textStatus.setText(getString(R.string.status_verification_failed_code));
                showViews(editCodigoVerificacao, textCountdown, textStatus);
                hideViews(editNumeroTelefone, cpp, btnEnviarCodigo, mProgressBar, btnReenviarCodigo);
                break;
            case STATE_CODE_TIMEOUT:

                showViews(btnReenviarCodigo);
                break;
            case STATE_SENDING_CODE:
                textStatus.setText(getString(R.string.recebendo_codigo));
                showViews(mProgressBar, textStatus);
                hideViews(editNumeroTelefone, cpp, btnEnviarCodigo, editCodigoVerificacao, textCountdown, btnReenviarCodigo);
                break;
            default:
        }
    }
}
