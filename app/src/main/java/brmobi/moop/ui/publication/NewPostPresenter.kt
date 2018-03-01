package brmobi.moop.ui.publication

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
 * Created by murilo aires on 25/02/2018.
 */
class NewPostPresenter<V : NewPostMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), NewPostMvpPresenter<V> {
    private var mCurrentPhotoPath: String? = null
    private var foto: File? = null

    override fun onAddPhotoClick() {
        getMvpView()?.createDialogAddPhoto()
    }

    override fun onCameraSelected() {
        getMvpView()?.requestCameraPermision()
    }

    override fun onGallerySelected() {
        getMvpView()?.requestCameraPermision()
    }

    override fun removePic() {
        mCurrentPhotoPath = null
        foto = null
        getMvpView()?.showBtnSelectPic()
        getMvpView()?.hideImgPost()
        getMvpView()?.hideBtnDeletePhoto()
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

    override fun onImgAvatarClick() {
        getMvpView()?.createDialogAddPhoto()
    }

    override fun onRemoveImageClick() {
        foto = null
        mCurrentPhotoPath = null
        getMvpView()?.hideImgPost()
    }

    override fun onBtnChooseImageClick() {
        getMvpView()?.createDialogAddPhoto()
    }

    override fun onPublishMenuClick(text: String) {
        if (text.isEmpty() && foto == null) {
            return
        } else {
            getMvpView()?.showLoading(R.string.aguarde, R.string.criando_publicacao)
            mCompositeDisposable.add(dataManager.postFeed(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium(), "", text, foto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getMvpView()?.hideLoading()
                        getMvpView()?.showMessage(R.string.publicacao_criacada_sucesso)
                        getMvpView()?.finishWithOkResult()
                    }, {
                        getMvpView()?.hideLoading()
                        handleApiError(it as HttpException)
                    }))
        }
    }
}