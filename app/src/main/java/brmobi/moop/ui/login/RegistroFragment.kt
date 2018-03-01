package brmobi.moop.ui.login


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.ui.main.MoopActivity
import brmobi.moop.utils.FotoUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.fragment_registro.*
import java.io.File
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class RegistroFragment : BaseFragment(), RegisterMvpView {

    private var mCurrentPhotoPath: String? = null
    private var avatar: File? = null

    @Inject
    lateinit var mPresenter: RegisterMvpPresenter<RegisterMvpView>

    private fun createDialogAddFoto() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.selecione_acao)
                .setItems(R.array.foto_options) { dialog, which ->
                    when (which) {
                        0 -> requestCameraPermission(CAMERA)

                        else -> requestReadAndWritePermission(GALLERY)
                    }
                }
        builder.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    override fun setUp(view: View) {
        imgAvatar.setOnClickListener {
            createDialogAddFoto()
        }
        btnCadastrar.setOnClickListener {
            mPresenter.onBtnCadastrarClick(editNome.text.toString(), editEmal.text.toString(), FirebaseAuth.getInstance().currentUser!!.phoneNumber, null, "", FirebaseInstanceId.getInstance().token, "android", avatar)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as LoginActivity).showToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun requestCameraPermission(from: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), from)
            } else {
                openCamera(from)
            }
        } else {
            openCamera(from)
        }
    }

    private fun openCamera(from: Int) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(context.packageManager) != null) {
            val photoFile = FotoUtils.getOutputMediaFile()
            if (photoFile == null) {
                Toast.makeText(context, getString(R.string.app_nao_conseguiu_criar_diretorio), Toast.LENGTH_LONG).show()
                return
            }
            var photoUri: Uri? = null
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                photoUri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".my.package.name.provider", photoFile)
            } else {
                photoUri = Uri.fromFile(photoFile)
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            mCurrentPhotoPath = photoFile.absolutePath
            startActivityForResult(cameraIntent, from)
        }
    }

    private fun requestReadAndWritePermission(from: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), from)
            } else {
                openGalery(from)
            }
        } else {
            openGalery(from)
        }
    }

    private fun openGalery(from: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), from)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == GALLERY) {
                openGalery(requestCode)
            } else if (requestCode == CAMERA) {
                openCamera(requestCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                ajdustPicture(context, mCurrentPhotoPath)
            } else if (requestCode == GALLERY) {
                val uri = data!!.data
                openCropActivity(uri)
            } else if (requestCode == UCrop.REQUEST_CROP) {
                onPhotoCropped(UCrop.getOutput(data!!))
            }
        }

    }

    private fun ajdustPicture(context: Context, mCurrentPhotoPath: String?) {
        val options = UCrop.Options()
        options.setFreeStyleCropEnabled(true)
        options.setHideBottomControls(true)
        options.setToolbarColor(context.resources.getColor(R.color.colorPrimary))
        options.setStatusBarColor(context.resources.getColor(R.color.colorPrimaryDark))
        options.setToolbarTitle(context.getString(R.string.cortar))
        options.setMaxBitmapSize(1920)
        options.withMaxResultSize(1920, 1920)
        val destination = Uri.fromFile(File(mCurrentPhotoPath!!))
        openCropActivity(options, destination)
    }

    private fun openCropActivity(options: UCrop.Options, destination: Uri) {
        UCrop.of(destination, destination)
                .withOptions(options)
                .start(activity, this)
    }

    fun openCropActivity(options: UCrop.Options, source: Uri?, destination: Uri) {
        UCrop.of(source!!, destination).withOptions(options).start(activity, this)
    }

    fun openCropActivity(source: Uri?) {
        val options = UCrop.Options()
        options.setFreeStyleCropEnabled(true)
        options.setHideBottomControls(true)
        options.setToolbarColor(context.resources.getColor(R.color.colorPrimary))
        options.setStatusBarColor(context.resources.getColor(R.color.colorPrimaryDark))
        options.setToolbarTitle(context.getString(R.string.cortar))
        options.setMaxBitmapSize(1920)
        options.withMaxResultSize(1920, 1920)
        val destination = Uri.fromFile(FotoUtils.getOutputMediaFile())

        openCropActivity(options, source, destination)
    }

    fun onPhotoCropped(output: Uri?) {
        val croppedPath = FotoUtils.getPath(context, output)
        FotoUtils.sendPictureBroadcast(croppedPath, context)
        avatar = File(croppedPath!!)
        Picasso.with(context).load(avatar).into(imgAvatar)

    }

    override fun openMoopActivity() {
        startActivity(Intent(activity, MoopActivity::class.java))
        activity.finish()
    }

    override fun onEmptyName(resId: Int) {
        editNome.error = getString(resId)
    }

    override fun onInvalidEmail(resId: Int) {
        editEmal.error = getString(resId)
    }

    companion object {

        const val CAMERA = 1
        const val GALLERY = 2
    }
}// Required empty public constructor
