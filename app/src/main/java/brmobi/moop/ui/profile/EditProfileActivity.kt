package brmobi.moop.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import brmobi.moop.ui.tickets.NewTicketFragment
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_editar_perfil.*
import java.io.File
import javax.inject.Inject

class EditProfileActivity : BaseActivity(), EditProfileMvpView {

    @Inject
    lateinit var mPresenter: EditProfileMvpPresenter<EditProfileMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        imgAvatar.setOnClickListener {
            mPresenter.onImgAvatarClick()
        }
        btnConfirmar.setOnClickListener {
            mPresenter.onBtnConfirmClick(editNome.text)
        }
        mPresenter.onViewReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri) {
        UCrop.of(photoUri, photoUri)
                .withOptions(uCropOptions)
                .start(this)
    }

    override fun requestCameraPermision() {
        requestCameraPermission(CAMERA)
    }

    override fun requestWriteReadPermision() {
        requestReadAndWritePermission(GALERIA)
    }

    override fun setUserName(currentUserName: String) {
        editNome.setText(currentUserName)
    }

    override fun showPlaceHolderAvatar() {
        Picasso.with(this).load(R.drawable.placeholder_avatar).into(imgAvatar)
    }

    override fun showAvatar(currentProfilePic: String) {
        Picasso.with(this).load(currentProfilePic).into(imgAvatar)
    }

    override fun openCameraActivity(intent: Intent, requestedBy: Int) {
        startActivityForResult(intent, requestedBy)
    }

    override fun loadPhotoOnImageView(foto: File) {
        Picasso.with(this).load(foto).into(imgAvatar)
    }

    override fun createDialogAddPhoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.selecione_acao)
                .setItems(R.array.foto_options) { dialog, which ->
                    when (which) {
                        0 -> requestCameraPermission(CAMERA)
                        else -> requestReadAndWritePermission(GALERIA)
                    }
                }
        builder.show()
    }

    private fun requestCameraPermission(from: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
        if (cameraIntent.resolveActivity(packageManager) != null) {
            mPresenter.onCameraIntentCreated(cameraIntent, from)
        }
    }

    override fun finishWithOkResult() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun requestReadAndWritePermission(from: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
            if (requestCode == GALERIA) {
                openGalery(requestCode)
            } else if (requestCode == CAMERA) {
                openCamera(requestCode)
            }
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == NewTicketFragment.CAMERA) {
                mPresenter.onCameraActivityReturn()
            } else if (requestCode == NewTicketFragment.GALLERY) {
                mPresenter.onPhotoFromGallerySelected(data!!.data)
            } else if (requestCode == UCrop.REQUEST_CROP) {
                mPresenter.onPhotoCropped(data!!)
            }
        }
    }

    override fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri?, destination: Uri) {
        UCrop.of(photoUri!!, destination).withOptions(uCropOptions).start(this)
    }

    companion object {
        const val CAMERA = 1
        const val GALERIA = 2
    }
}
