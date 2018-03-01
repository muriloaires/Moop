package brmobi.moop.ui.tickets


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.fragment_criar_chamado.*
import java.io.File
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class NewTicketFragment : BaseFragment(), NewTicketMvpView {
    @Inject
    lateinit var mPresenter: NewTicketMvpPresenter<NewTicketMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_criar_chamado, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        return view
    }

    override fun setUp(view: View) {
        btnAbrirChamado.setOnClickListener {
            mPresenter.onBtnNewTicketClick(edit_titulo_chamado.text.toString(), edit_descricao_chamado.text.toString())
        }
        imgAvatar.setOnClickListener {
            mPresenter.onAddPhotoClick()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun createDialogAddPhoto() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.selecione_acao)
                .setItems(R.array.foto_options) { _, which ->
                    when (which) {
                        0 -> mPresenter.onCameraSelected()

                        else -> mPresenter.onGallerySelected()

                    }
                }
        builder.show()
    }


    override fun onInvalidTitle(resId: Int) {
        edit_titulo_chamado.error = getString(resId)
    }

    override fun onInvalidMessage(resId: Int) {
        edit_descricao_chamado.error = getString(resId)
    }

    override fun requestCameraPermision() {
        requestCameraPermission(CAMERA)
    }

    override fun requestWriteReadPermision() {
        requestReadAndWritePermission(GALLERY)
    }

    fun requestCameraPermission(from: Int) {
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

    override fun openCameraActivity(intent: Intent, requestedBy: Int) {
        startActivityForResult(intent, requestedBy)
    }

    private fun openCamera(from: Int) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(context.packageManager) != null) {
            mPresenter.onCameraIntentCreated(cameraIntent, from)
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
                mPresenter.onCameraActivityReturn()
            } else if (requestCode == GALLERY) {
                mPresenter.onPhotoFromGallerySelected(data!!.data)
            } else if (requestCode == UCrop.REQUEST_CROP) {
                mPresenter.onPhotoCropped(data!!)
            }
        }
    }

    override fun onTicketCreated() {
        (activity as TicketActivity).onBackPressed()
    }

    override fun loadPhotoOnImageView(photo: File) {
        Picasso.with(activity).load(photo).into(imgAvatar)
    }

    override fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri) {
        UCrop.of(photoUri, photoUri)
                .withOptions(uCropOptions)
                .start(activity, this)
    }

    override fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri?, destination: Uri) {
        UCrop.of(photoUri!!, destination).withOptions(uCropOptions).start(activity, this)
    }

    companion object {
        const val CAMERA = 1
        const val GALLERY = 2
    }
}// Required empty public constructor
