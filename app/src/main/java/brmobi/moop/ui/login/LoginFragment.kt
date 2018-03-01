package brmobi.moop.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.ui.main.MoopActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginMvpView {

    companion object {
        const val TAG = "PhoneAuthActivity"
        const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
        const val STATE_INITIALIZED = 1
        const val STATE_CODE_SENT = 2
        const val STATE_VERIFY_FAILED = 3
        const val STATE_VERIFY_SUCCESS = 4
        const val STATE_SIGNIN_FAILED = 5
        const val STATE_SIGNIN_SUCCESS = 6
        const val STATE_CODE_TIMEOUT = 7
        const val STATE_SENDING_CODE = 8
    }

    private var actualState: Int = 0

    private var loginActivity: LoginActivity? = null
    private var countDownTimer: CountDownTimer? = null
    private var mAuth: FirebaseAuth? = null
    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null


    private var mVerificationInProgress = false
    private var mVerificationId: String? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null

    @Inject
    lateinit var mPresenter: LoginMvpPresenter<LoginMvpView>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        if (savedInstanceState != null) {
            mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS)
        }

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun setUp(view: View) {
        btn_enviar_codigo.setOnClickListener {
            if (validateNumber()) {
                actualState = STATE_SENDING_CODE
                updateUI()
                startPhoneNumberVerification(editNumeroTelefone.text.toString())
            }
        }

        btnReenviarCodigo.setOnClickListener {
            if (validateNumber()) {
                actualState = STATE_SENDING_CODE
                updateUI()
                resendVerificationCode(editNumeroTelefone.text.toString(), mResendToken)
            }
        }

        btnlogout.setOnClickListener {
            updateUI()
            mAuth!!.signOut()
        }

        configureCallBack()
        country_picker.registerCarrierNumberEditText(editNumeroTelefone)
        editCodigoVerificacao.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length == 6) {
                    actualState = STATE_VERIFY_SUCCESS
                    updateUI()
                    countDownTimer!!.cancel()
                    verifyPhoneNumberWithCode(mVerificationId, charSequence.toString())
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }


    override fun openMoopActivity() {
        startActivity(Intent(activity, MoopActivity::class.java))
        activity.finish()
    }

    override fun onStart() {
        super.onStart()

        updateUI()
        val user = mAuth!!.currentUser
        if (user != null) {
            actualState = STATE_SIGNIN_SUCCESS
            updateUI()
            performLogin(user)
        } else {
            if (mVerificationInProgress && validateNumber()) {
                actualState = STATE_SENDING_CODE
                updateUI()
                startPhoneNumberVerification(editNumeroTelefone.text.toString())
            } else {
                actualState = STATE_INITIALIZED
                updateUI()
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress)
    }


    private fun configureCallBack() {
        mAuth = FirebaseAuth.getInstance()
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential)
                actualState = STATE_VERIFY_SUCCESS
                countDownTimer!!.cancel()
                // [START_EXCLUDE silent]
                mVerificationInProgress = false
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI()
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)
                actualState = STATE_VERIFY_FAILED
                // [START_EXCLUDE silent]
                mVerificationInProgress = false
                // [END_EXCLUDE]

                if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                updateUI()
            }

            override fun onCodeSent(verificationId: String?,
                                    token: PhoneAuthProvider.ForceResendingToken?) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId!!)
                actualState = STATE_CODE_SENT
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token

                // [START_EXCLUDE]
                updateUI()
                // [END_EXCLUDE]
            }
        }
    }

    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                textCountdown.text = "Validade do código " + millisUntilFinished / 1000 + " segundo(s)"
            }

            override fun onFinish() {
                actualState = STATE_CODE_TIMEOUT
                updateUI()
            }
        }
        countDownTimer!!.start()
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        actualState = STATE_SIGNIN_SUCCESS
                        updateUI()
                        val user = task.result.user
                        performLogin(user)
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        actualState = STATE_SIGNIN_FAILED
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            // [START_EXCLUDE silent]

                            // [END_EXCLUDE]
                        }
                        // [START_EXCLUDE silent]
                        // Update UI
                        actualState = STATE_SIGNIN_FAILED
                        updateUI()
                        // [END_EXCLUDE]
                    }
                }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.loginActivity = context as LoginActivity?
    }

    override fun onResume() {
        super.onResume()
        loginActivity!!.hideToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        var phoneNumber = phoneNumber
        phoneNumber = "+" + country_picker.selectedCountryCode + phoneNumber
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                loginActivity!!, // Activity (for callback binding)
                mCallbacks!!)        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true
    }

    private fun resendVerificationCode(phoneNumber: String,
                                       token: PhoneAuthProvider.ForceResendingToken?) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                loginActivity!!, // Activity (for callback binding)
                mCallbacks!!, // OnVerificationStateChangedCallbacks
                token)             // ForceResendingToken from callbacks
    }

    private fun performLogin(user: FirebaseUser) {
        mPresenter.onLoginReady(user.phoneNumber!!, FirebaseInstanceId.getInstance().token!!, "android")
    }

    fun showViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    fun hideViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.GONE
        }
    }

    private fun validateNumber(): Boolean {
        return !TextUtils.isEmpty(editNumeroTelefone.text.toString())
    }

    fun updateUI() {
        when (actualState) {
            STATE_INITIALIZED -> {
                textStatus.text = getString(R.string.insira_seu_telefone)
                showViews(editNumeroTelefone, country_picker, btn_enviar_codigo, textStatus)
                hideViews(editCodigoVerificacao, btnReenviarCodigo, progress, textCountdown)
            }
            STATE_CODE_SENT -> {
                textStatus.text = getString(R.string.insira_o_codigo_verificacao)
                showViews(editCodigoVerificacao, textCountdown, textStatus)
                hideViews(editNumeroTelefone, country_picker, btn_enviar_codigo, progress, btnReenviarCodigo)
                startCountdown()
            }
            STATE_VERIFY_SUCCESS -> {
                textStatus.text = getString(R.string.validando_codigo)
                showViews(progress, textStatus)
                hideViews(editNumeroTelefone, editCodigoVerificacao, country_picker, btn_enviar_codigo, btnReenviarCodigo, textCountdown)
            }
            STATE_VERIFY_FAILED -> {
                textStatus.text = getString(R.string.status_verification_failed)
                showViews(editNumeroTelefone, country_picker, btn_enviar_codigo, textStatus)
                hideViews(editCodigoVerificacao, btnReenviarCodigo, progress, textCountdown)
            }
            STATE_SIGNIN_SUCCESS -> {
                textStatus.text = getString(R.string.entrando_no_moop)
                showViews(progress, textStatus)
                hideViews(editNumeroTelefone, editCodigoVerificacao, country_picker, btn_enviar_codigo, btnReenviarCodigo, textCountdown)
            }
            STATE_SIGNIN_FAILED -> {
                textStatus.text = getString(R.string.status_verification_failed_code)
                showViews(editCodigoVerificacao, textCountdown, textStatus)
                hideViews(editNumeroTelefone, country_picker, btn_enviar_codigo, progress, btnReenviarCodigo)
            }
            STATE_CODE_TIMEOUT ->

                showViews(btnReenviarCodigo)
            STATE_SENDING_CODE -> {
                textStatus.text = getString(R.string.recebendo_codigo)
                showViews(progress, textStatus)
                hideViews(editNumeroTelefone, country_picker, btn_enviar_codigo, editCodigoVerificacao, textCountdown, btnReenviarCodigo)
            }
        }


    }

    override fun showRegisterFragment() {
        (activity as LoginActivity).showRegistroFragment()
    }


}
