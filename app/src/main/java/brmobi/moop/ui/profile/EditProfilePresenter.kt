package brmobi.moop.ui.profile

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.ui.base.BasePresenter
import com.yalantis.ucrop.UCrop
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by murilo aires on 25/02/2018.
 */
class EditProfilePresenter<V : EditProfileMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), EditProfileMvpPresenter<V> {

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

    override fun onImgAvatarClick() {
        getMvpView()?.createDialogAddPhoto()
    }

    override fun onBtnConfirmClick(text: Editable) {
        if (text.isEmpty() && foto == null) {
            return
        } else {
            getMvpView()?.showLoading(R.string.aguarde, R.string.efetuando_requisicao)
            mCompositeDisposable.add(dataManager.updateUser(dataManager.getCurrentAccessToken(), text.toString(), foto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ usuario ->
                        getMvpView()?.hideLoading()
                        getMvpView()?.showMessage(R.string.perfil_atualizado_sucesso)
                        dataManager.updateUserInfo(dataManager.getCurrentAccessToken(), dataManager.getCurrentUserId()
                                , DataManager.LoginMode.LOGGE_IN_MODE_SERVER, usuario.nome,
                                dataManager.getCurrentUserEmail(), usuario.avatar)
                        getMvpView()?.finishWithOkResult()
                    }, {

                    }))
        }
    }

    override fun onViewReady() {
        getMvpView()?.setUserName(dataManager.getCurrentUserName())
        if (dataManager.getCurrentProfilePic().isNullOrEmpty()) {
            getMvpView()?.showPlaceHolderAvatar()
        } else {
            getMvpView()?.showAvatar(dataManager.getCurrentProfilePic()!!)
        }
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