package brmobi.moop.data

import android.content.Context
import android.net.Uri
import br.com.airescovit.clim.di.ApplicationContext
import brmobi.moop.data.db.DbHelper
import brmobi.moop.data.db.model.Condominio
import brmobi.moop.data.media.MediaHelper
import brmobi.moop.data.network.ApiHelper
import brmobi.moop.data.network.model.*
import brmobi.moop.data.prefs.PreferenceHelper
import com.google.firebase.auth.FirebaseAuth
import com.yalantis.ucrop.UCrop
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by murilo aires on 19/02/2018.
 */
@Singleton
class AppDataManager @Inject constructor(@ApplicationContext val context: Context, val mDbHelper: DbHelper, val mPreferencesHelper: PreferenceHelper, val mApiHelper: ApiHelper, val mMediaHelper: MediaHelper) : DataManager {

    override fun setUserAsLoggedOut() {
        FirebaseAuth.getInstance().signOut()
        updateUserInfo(null, null, DataManager.LoginMode.LOGGED_IN_MODE_LOGGED_OUT, null, null, null)
    }

    override fun updateUserInfo(accessToken: String?, userId: Long?, loginMode: DataManager.LoginMode, userName: String?, userEmail: String?, profilePicUrl: String?) {
        setAccessToken(accessToken)
        setCurrentUserId(userId)
        setCurrentLoginMode(loginMode)
        setCurrentUserName(userName)
        setCurrentUserEmail(userEmail)
        setCurrentProfilePic(profilePicUrl)

    }

    /**
     * User Preferences
     */
    override fun getCurrentUserId(): Long {
        return mPreferencesHelper.getCurrentUserId()
    }

    override fun setCurrentUserId(id: Long?) {
        mPreferencesHelper.setCurrentUserId(id)
    }

    override fun getCurrentUserName(): String {
        return mPreferencesHelper.getCurrentUserName()
    }

    override fun setCurrentUserName(name: String?) {
        mPreferencesHelper.setCurrentUserName(name)
    }

    override fun setCurrentUserEmail(email: String?) {
        mPreferencesHelper.setCurrentUserEmail(email)
    }

    override fun getCurrentUserEmail(): String {
        return mPreferencesHelper.getCurrentUserEmail()
    }

    override fun setAccessToken(accessToken: String?) {
        mPreferencesHelper.setAccessToken(accessToken)
    }

    override fun getCurrentAccessToken(): String {
        return mPreferencesHelper.getCurrentAccessToken()
    }

    override fun getCurrentLoginMode(): Int {
        return mPreferencesHelper.getCurrentLoginMode()
    }

    override fun setCurrentLoginMode(loginMode: DataManager.LoginMode) {
        mPreferencesHelper.setCurrentLoginMode(loginMode)
    }

    override fun setCurrentProfilePic(pictureUrl: String?) {
        mPreferencesHelper.setCurrentProfilePic(pictureUrl)
    }

    override fun getCurrentProfilePic(): String? = mPreferencesHelper.getCurrentProfilePic()
    /**
     * END User Preferences
     */


    /**
     * Condominiuns Endpoint
     */
    override fun getUserCondominiuns(apiToken: String): Observable<GenericListResponse<Condominio>> = mApiHelper.getUserCondominiuns(apiToken)

    override fun doDetachFromCondominium(apiToken: String, condominiumId: Long): Observable<ResponseBody> = mApiHelper.doDetachFromCondominium(apiToken, condominiumId)

    override fun getCondominiumFromZipCode(apiToken: String, zipCode: String): Observable<GenericListResponse<Condominio>> {
        return mApiHelper.getCondominiumFromZipCode(apiToken, zipCode)
    }

    override fun getCondominiumBlocs(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Bloco>> {
        return mApiHelper.getCondominiumBlocs(apiToken, condominiumId)
    }

    override fun getBlocUnits(apiToken: String, blocId: Long, condominiumId: Long): Observable<GenericListResponse<Unidade>> {
        return mApiHelper.getBlocUnits(apiToken, blocId, condominiumId)
    }

    override fun doRegisterUnit(apiToken: String, blocoId: Long, isOwner: Boolean, isDweller: Boolean, number: String): Observable<ResponseBody> {
        return mApiHelper.doRegisterUnit(apiToken, blocoId, isOwner, isDweller, number)
    }

    override fun doRegisterCondominium(apiToken: String, zipCode: String, name: String, sreet: String, number: String, phoneNumber: String, isHorizontal: Boolean, blocs: List<String>): Observable<GenericResponse<CadastroCondominio>> {
        return mApiHelper.doRegisterCondominium(apiToken, zipCode, name, sreet, number, phoneNumber, isHorizontal, blocs)
    }

    override fun doRegisterBloc(apiToken: String, condominiumId: Long, name: String, number: String): Observable<ResponseBody> {
        return mApiHelper.doRegisterBloc(apiToken, condominiumId, name, number)
    }

    override fun getCondominiumDetail(apiToken: String, condominiumId: Long): Observable<Condominio> {
        return mApiHelper.getCondominiumDetail(apiToken, condominiumId)
    }

    override fun updateUser(apiToken: String, name: String, avatar: File?): Observable<Usuario> {
        return mApiHelper.updateUser(apiToken, name, avatar)
    }

    override fun generateNewPassword(apiToken: String): Observable<Senha> {
        return mApiHelper.generateNewPassword(apiToken)
    }
    /**
     * END Condominiuns Endpoint
     */


    /**
     * Condominium Preferences
     */
    override fun getLastSelectedCondominium(): Long {
        return mPreferencesHelper.getLastSelectedCondominium()
    }

    override fun saveLastSelectedCondominium(condominiumId: Long) {
        mPreferencesHelper.saveLastSelectedCondominium(condominiumId)
    }

    /**
     * END Condominium Preferences
     */


    override fun clearPreferences() {
        mPreferencesHelper.clearPreferences()
    }

    /**
     * Condominium DB
     */
    override fun loadCondominium(condominiumId: Long): Observable<Condominio> {
        return mDbHelper.loadCondominium(condominiumId)
    }

    override fun insertCondominium(condominium: Condominio): Observable<Long> {
        return mDbHelper.insertCondominium(condominium)
    }

    override fun insertCondominiumList(condominiuns: List<Condominio>): Observable<Unit> {
        return mDbHelper.insertCondominiumList(condominiuns)
    }
    /**
     * END Condominium DB
     */

    /**
     * User Endpoints
     */
    override fun doLogin(phoneNumber: String, deviceToken: String, deviceType: String): Observable<Usuario> {
        return mApiHelper.doLogin(phoneNumber, deviceToken, deviceType)
    }

    override fun doRegister(name: String, email: String, phoneNumber: String, deviceToken: String, deviceType: String, avatarUrl: String?, avatar: File?): Observable<Usuario> {
        return mApiHelper.doRegister(name, email, phoneNumber, deviceToken, deviceType, avatarUrl, avatar)
    }

    /**
     * END User Endpoints
     */

    /**
     * Feed Endpoint
     */

    override fun getFeed(apiToken: String, condiminiumId: Long, limit: Int, offset: Int): Observable<GenericListResponse<FeedItem>> {
        return mApiHelper.getFeed(apiToken, condiminiumId, limit, offset)
    }

    override fun postFeed(apiToken: String, condiminiumId: Long, title: String, text: String?, img: File?): Observable<FeedItem> {
        return mApiHelper.postFeed(apiToken, condiminiumId, title, text, img)
    }

    override fun likeFeedItem(apiToken: String, feedId: Long): Observable<ResponseBody> {
        return mApiHelper.likeFeedItem(apiToken, feedId)
    }

    override fun unlikeFeedItem(apiToken: String, feedId: Long): Observable<ResponseBody> {
        return mApiHelper.unlikeFeedItem(apiToken, feedId)
    }

    override fun deleteFeed(apiToken: String, feedId: Long): Observable<ResponseBody> {
        return mApiHelper.deleteFeed(apiToken, feedId)
    }

    /**
     * END Feed Endpoints
     */


    /**
     * Messages Endpoint
     */
    override fun getUserLastMessages(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Mensagem>> {
        return mApiHelper.getUserLastMessages(apiToken, condominiumId)
    }

    override fun getChatMessages(apiToken: String, userDestinyId: Long, condominiumId: Long): Observable<GenericListResponse<Mensagem>> {
        return mApiHelper.getChatMessages(apiToken, userDestinyId, condominiumId)
    }

    override fun postMessage(apiToken: String, userDestinyId: Long, condominiumId: Long, message: String): Observable<Mensagem> {
        return mApiHelper.postMessage(apiToken, userDestinyId, condominiumId, message)
    }

    override fun deleteMessage(apiToken: String, messageId: Long): Observable<ResponseBody> {
        return mApiHelper.deleteMessage(apiToken, messageId)
    }

    /**
     * Media Helper
     */

    override fun getOutputPhotFile(): File? {
        return mMediaHelper.getOutputPhotFile()
    }

    override fun getUriForFile(photoFile: File): Uri? {
        return mMediaHelper.getUriForFile(photoFile)
    }

    override fun getDefaultUCropOptions(): UCrop.Options {
        return mMediaHelper.getDefaultUCropOptions()
    }

    override fun getUriFromFilePath(path: String): Uri {
        return mMediaHelper.getUriFromFilePath(path)
    }

    override fun getUriFromFile(file: File): Uri {
        return mMediaHelper.getUriFromFile(file)
    }

    override fun getPathFromUri(uri: Uri?): String {
        return mMediaHelper.getPathFromUri(uri)
    }

    override fun sendPictureBroadcast(path: String) {
        mMediaHelper.sendPictureBroadcast(path)
    }

    override fun getUriFromNewFile(): Uri {
        return mMediaHelper.getUriFromNewFile()
    }

    /**
     * Tickets Endpoint
     */
    override fun postTicket(apiToken: String, condominiumId: Long, title: String, message: String, file: File?): Observable<ResponseBody> {
        return mApiHelper.postTicket(apiToken, condominiumId, title, message, file)
    }

    override fun getTickets(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Chamado>> {
        return mApiHelper.getTickets(apiToken, condominiumId)
    }

    /**
     * Dwellers
     */
    override fun getDwellers(apiToken: String, condominiumId: Long, query: String): Observable<List<PerfilHabitacional>> {
        return mApiHelper.getDwellers(apiToken, condominiumId, query)
    }

    override fun approveDweller(apiToken: String, perfilHabitacionalId: Long, isApproved: Boolean): Observable<ResponseBody> {
        return mApiHelper.approveDweller(apiToken, perfilHabitacionalId, isApproved)
    }

    override fun approveDwellerCall(apiToken: String, perfilHabitacionalId: Long, isApproved: Boolean): Call<ResponseBody> {
        return mApiHelper.approveDwellerCall(apiToken, perfilHabitacionalId, isApproved)
    }

    override fun getBlockedDwellers(apiToken: String, condominiumId: Long): Observable<List<PerfilHabitacional>> {
        return mApiHelper.getBlockedDwellers(apiToken, condominiumId)
    }

    /**
     * Notifications Endpoint
     */
    override fun getNotifications(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Notificacao>> {
        return mApiHelper.getNotifications(apiToken, condominiumId)
    }

    /**
     * Comments Endpoint
     */

    override fun postComment(apiToken: String, feedId: Long, text: String): Observable<Comentario> {
        return mApiHelper.postComment(apiToken, feedId, text)
    }

    override fun getComments(apiToken: String, feedId: Long): Observable<GenericListResponse<Comentario>> {
        return mApiHelper.getComments(apiToken, feedId)
    }

    override fun deleteComment(apiToken: String, commentId: Long): Observable<ResponseBody> {
        return mApiHelper.deleteComment(apiToken, commentId)
    }

    /**
     * Reservations Endpoint
     */
    override fun getSharedResources(apiToken: String, condominiumId: Long): Observable<GenericListResponse<BemComum>> {
        return mApiHelper.getSharedResources(apiToken, condominiumId)
    }

    override fun getResourceAvailability(apiToken: String, resourceId: Long, date: String): Observable<GenericListResponse<DisponibilidadeBem>> {
        return mApiHelper.getResourceAvailability(apiToken, resourceId, date)
    }

    override fun reserveAvailability(apiToken: String, availabilityId: Long, dataDesejada: String): Observable<GenericResponse<ReservaBemComum>> {
        return mApiHelper.reserveAvailability(apiToken, availabilityId, dataDesejada)
    }

    override fun getReservations(apiToken: String, condominiumId: Long): Observable<GenericListResponse<ReservaBemComum>> {
        return mApiHelper.getReservations(apiToken, condominiumId)
    }

    override fun getSharedResourcesDays(apiToken: String, condominiumId: Long, resourceId: Long, month: Int, year: Int): Observable<GenericListResponse<DiaBemComum>> {
        return mApiHelper.getSharedResourcesDays(apiToken, condominiumId, resourceId, month, year)
    }

    override fun cancelReservation(apiToken: String, reservationId: Long): Observable<ResponseBody> {
        return mApiHelper.cancelReservation(apiToken, reservationId)
    }
}
