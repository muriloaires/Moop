package brmobi.moop.ui.publication

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
import android.view.Menu
import android.view.View
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import brmobi.moop.ui.profile.EditProfileActivity
import brmobi.moop.ui.tickets.NewTicketFragment
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_new_post.*
import java.io.File
import javax.inject.Inject

class NewPostActivity : BaseActivity(), NewPostMvpView {

    @Inject
    lateinit var mPresenter: NewPostMvpPresenter<NewPostMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        setSupportActionBar(toolbarPublicacao)
        toolbarPublicacao.setNavigationIcon(R.drawable.ic_back)
        toolbarPublicacao.setNavigationOnClickListener { onBackPressed() }
        icRemoveImage.setOnClickListener {
            mPresenter.onRemoveImageClick()
        }
        fabEscolherFoto.setOnClickListener {
            mPresenter.onBtnChooseImageClick()
        }
        icRemoveImage.setOnClickListener {
            mPresenter.removePic()
        }
    }

    override fun hideBtnDeletePhoto() {
        icRemoveImage.visibility = View.GONE
    }

    override fun showBtnSelectPic() {
        fabEscolherFoto.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_publicacao, menu)
        val menuItem = menu.findItem(R.id.publicar)
        menuItem.actionView.setOnClickListener {
            mPresenter.onPublishMenuClick(editText.text.toString())
        }
        return true
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

    override fun hideImgPost() {
        imgPost.visibility = View.GONE
    }

    override fun requestCameraPermision() {
        requestCameraPermission(EditProfileActivity.CAMERA)
    }

    override fun requestWriteReadPermision() {
        requestReadAndWritePermission(EditProfileActivity.GALERIA)
    }

    override fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri?, destination: Uri) {
        UCrop.of(photoUri!!, destination).withOptions(uCropOptions).start(this)
    }

    override fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri) {
        UCrop.of(photoUri, photoUri)
                .withOptions(uCropOptions)
                .start(this)
    }


    override fun openCameraActivity(intent: Intent, requestedBy: Int) {
        startActivityForResult(intent, requestedBy)
    }

    override fun loadPhotoOnImageView(foto: File) {
        imgPost.visibility = View.VISIBLE
        icRemoveImage.visibility = View.VISIBLE
        fabEscolherFoto.visibility = View.GONE
        Picasso.with(this).load(foto).into(imgPost)
    }

    override fun finishWithOkResult() {
        setResult(Activity.RESULT_OK)
        finish()
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
            if (requestCode == EditProfileActivity.GALERIA) {
                openGalery(requestCode)
            } else if (requestCode == EditProfileActivity.CAMERA) {
                openCamera(requestCode)
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                NewTicketFragment.CAMERA -> mPresenter.onCameraActivityReturn()
                NewTicketFragment.GALLERY -> mPresenter.onPhotoFromGallerySelected(data!!.data)
                UCrop.REQUEST_CROP -> mPresenter.onPhotoCropped(data!!)
            }
        }

    }


    companion object {
        const val CAMERA = 1
        const val GALERIA = 2
    }
}
