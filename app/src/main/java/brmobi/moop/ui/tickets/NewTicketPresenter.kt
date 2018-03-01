package brmobi.moop.ui.tickets

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.ui.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.yalantis.ucrop.UCrop
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by murilo aires on 21/02/2018.
 */
class NewTicketPresenter<V : NewTicketMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, mCompositeDisposable), NewTicketMvpPresenter<V> {

    private var mCurrentPhotoPath: String? = null
    private var foto: File? = null

    override fun onBtnNewTicketClick(title: String, message: String) {
        when {
            title.isEmpty() -> getMvpView()?.onInvalidTitle(R.string.campo_obrigatorio)
            message.isEmpty() -> getMvpView()?.onInvalidMessage(R.string.campo_obrigatorio)
            else -> {
                getMvpView()?.showLoading(R.string.aguarde, R.string.registrando)
                mCompositeDisposable.add(dataManager.postTicket(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium(), title, message, foto)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            getMvpView()?.hideLoading()
                            getMvpView()?.onTicketCreated()
                        }, {
                            getMvpView()?.hideLoading()
                            handleApiError(it as HttpException)
                        }))
            }
        }
    }

    override fun onAddPhotoClick() {
        getMvpView()?.createDialogAddPhoto()
    }

    override fun onCameraSelected() {
        getMvpView()?.requestCameraPermision()
    }

    override fun onGallerySelected() {
        getMvpView()?.requestWriteReadPermision()
    }

    override fun onCameraIntentCreated(cameraIntent: Intent, requestedBy: Int) {
        val photoFile = dataManager.getOutputPhotFile()
        if (photoFile == null) {
            getMvpView()?.showMessage(R.string.app_nao_conseguiu_criar_diretorio)
            return
        }
        val photoUri: Uri? = dataManager.getUriForFile(photoFile)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        mCurrentPhotoPath = photoFile.absolutePath
        getMvpView()?.openCameraActivity(cameraIntent, requestedBy)
    }

    override fun onCameraActivityReturn() {
        getMvpView()?.openCropActivity(dataManager.getDefaultUCropOptions(), dataManager.getUriFromFilePath(mCurrentPhotoPath!!))
    }

    override fun onPhotoCropped(data: Intent) {
        val croppedPath = dataManager.getPathFromUri(UCrop.getOutput(data))
        dataManager.sendPictureBroadcast(croppedPath)
        foto = File(croppedPath)
        getMvpView()?.loadPhotoOnImageView(foto!!)
    }

    override fun onPhotoFromGallerySelected(data: Uri?) {
        getMvpView()?.openCropActivity(dataManager.getDefaultUCropOptions(), data, dataManager.getUriFromNewFile())
    }
}