package brmobi.moop.data.network

import brmobi.moop.data.db.model.Condominio
import brmobi.moop.data.network.endpoints.*
import brmobi.moop.data.network.model.*
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by murilo aires on 19/02/2018.
 */
@Singleton
class AppApiHelper @Inject constructor(private val retrofit: Retrofit) : ApiHelper {

    /**
     * Condominium
     */
    override fun getUserCondominiuns(apiToken: String): Observable<GenericListResponse<Condominio>> {
        return retrofit.create(CondominiosEndpoint::class.java).getUserCondominiuns(apiToken)
    }

    override fun doDetachFromCondominium(apiToken: String, condominiumId: Long): Observable<ResponseBody> {
        return retrofit.create(CondominiosEndpoint::class.java).detachFromCondominium(apiToken, condominiumId)
    }

    override fun getCondominiumFromZipCode(apiToken: String, zipCode: String): Observable<GenericListResponse<Condominio>> {
        return retrofit.create(CondominiosEndpoint::class.java).getCondominiusFromZipCode(apiToken, zipCode)
    }

    override fun getCondominiumBlocs(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Bloco>> {
        return retrofit.create(CondominiosEndpoint::class.java).getCondominiumBlocs(apiToken, condominiumId)
    }

    override fun getBlocUnits(apiToken: String, blocId: Long, condominiumId: Long): Observable<GenericListResponse<Unidade>> {
        return retrofit.create(CondominiosEndpoint::class.java).getBlocUnits(apiToken, blocId, condominiumId)
    }

    override fun doRegisterUnit(apiToken: String, blocoId: Long, isOwner: Boolean, isDweller: Boolean, number: String): Observable<ResponseBody> {
        return retrofit.create(CondominiosEndpoint::class.java).doRegisterInUnit(apiToken, blocoId, isOwner, isDweller, number)
    }

    override fun doRegisterCondominium(apiToken: String, zipCode: String, name: String, sreet: String, number: String, phoneNumber: String, isHorizontal: Boolean, blocs: List<String>): Observable<GenericResponse<CadastroCondominio>> {
        return retrofit.create(CondominiosEndpoint::class.java).doRegisterCondominium(apiToken, zipCode, name, sreet, number, phoneNumber, isHorizontal, Gson().toJson(blocs))
    }

    override fun doRegisterBloc(apiToken: String, condominiumId: Long, name: String, number: String): Observable<ResponseBody> {
        return retrofit.create(CondominiosEndpoint::class.java).doRegisterBloc(apiToken, condominiumId, name, number)
    }

    override fun getCondominiumDetail(apiToken: String, condominiumId: Long): Observable<Condominio> {
        return retrofit.create(CondominiosEndpoint::class.java).getCondominiumDetail(apiToken, condominiumId)
    }

    /**
     * User
     */
    override fun doLogin(phoneNumber: String, deviceToken: String, deviceType: String): Observable<Usuario> {
        return retrofit.create(UserEndpoints::class.java).login(phoneNumber, deviceToken, deviceType)
    }

    override fun doRegister(name: String, email: String, phoneNumber: String, deviceToken: String, deviceType: String, avatarUrl: String?, avatar: File?): Observable<Usuario> {
        val nomeBody = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        val emailBody = RequestBody.create(MediaType.parse("multipart/form-data"), email)
        val phoneNumberBody = RequestBody.create(MediaType.parse("multipart/form-data"), phoneNumber)
        val deviceTokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), deviceToken)
        val deviceTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), deviceType)
        var avatarUrlBody: RequestBody? = null
        if (avatarUrl != null) {
            avatarUrlBody = RequestBody.create(MediaType.parse("multipart/form-data"), avatarUrl)
        }
        var body: MultipartBody.Part? = null
        if (avatar != null) {
            body = MultipartBody.Part.createFormData("avatar", avatar.getName(), RequestBody.create(MediaType.parse("image/*"), avatar))
        }
        return retrofit.create(UserEndpoints::class.java).register(nomeBody, emailBody, phoneNumberBody, deviceTokenBody, deviceTypeBody, avatarUrlBody, body)
    }

    override fun updateUser(apiToken: String, name: String, avatar: File?): Observable<Usuario> {
        val nomeBody = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        var body: MultipartBody.Part? = null

        if (avatar != null) {
            body = MultipartBody.Part.createFormData("avatar", avatar.getName(), RequestBody.create(MediaType.parse("image/*"), avatar))
        }
        return retrofit.create(UserEndpoints::class.java).update(apiToken, nomeBody, body)
    }

    override fun generateNewPassword(apiToken: String): Observable<Senha> {
        return retrofit.create(UserEndpoints::class.java).gerarNovaSenha(apiToken)
    }

    /**
     * Feed
     */
    override fun getFeed(apiToken: String, condiminiumId: Long, limit: Int, offset: Int): Observable<GenericListResponse<FeedItem>> {
        return retrofit.create(FeedEndpoints::class.java).getFeed(apiToken, condiminiumId, 10, offset)
    }

    override fun postFeed(apiToken: String, condiminiumId: Long, title: String, text: String?, img: File?): Observable<FeedItem> {
        val titleBody = RequestBody.create(MediaType.parse("multipart/form-data"), "d")
        var textBody: RequestBody? = null
        if (!text.isNullOrEmpty()) {
            textBody = RequestBody.create(MediaType.parse("multipart/form-data"), text!!)
        }
        var body: MultipartBody.Part? = null
        if (img != null) {
            body = MultipartBody.Part.createFormData("imagem", img.getName(), RequestBody.create(MediaType.parse("image/*"), img))
        }
        return retrofit.create(FeedEndpoints::class.java).postFeed(apiToken, condiminiumId, titleBody, textBody, body)
    }

    override fun likeFeedItem(apiToken: String, feedId: Long): Observable<ResponseBody> {
        return retrofit.create(FeedEndpoints::class.java).likeFeedItem(apiToken, feedId)
    }

    override fun unlikeFeedItem(apiToken: String, feedId: Long): Observable<ResponseBody> {
        return retrofit.create(FeedEndpoints::class.java).unlikeFeedItem(apiToken, feedId)
    }

    override fun deleteFeed(apiToken: String, feedId: Long): Observable<ResponseBody> {
        return retrofit.create(FeedEndpoints::class.java).deleteFeed(apiToken, feedId)
    }

    /**
     * Messages
     */
    override fun getUserLastMessages(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Mensagem>> {
        return retrofit.create(MessagesEndpoint::class.java).getUserLastMessages(apiToken, condominiumId)
    }

    override fun getChatMessages(apiToken: String, userDestinyId: Long, condominiumId: Long): Observable<GenericListResponse<Mensagem>> {
        return retrofit.create(MessagesEndpoint::class.java).getMessages(apiToken, userDestinyId, condominiumId)
    }

    override fun postMessage(apiToken: String, userDestinyId: Long, condominiumId: Long, message: String): Observable<Mensagem> {
        return retrofit.create(MessagesEndpoint::class.java).postMessage(apiToken, userDestinyId, condominiumId, message)
    }

    override fun deleteMessage(apiToken: String, messageId: Long): Observable<ResponseBody> {
        return retrofit.create(MessagesEndpoint::class.java).deleteMessage(apiToken, messageId)
    }

    /**
     * Tickets
     */
    override fun postTicket(apiToken: String, condominiumId: Long, title: String, message: String, file: File?): Observable<ResponseBody> {
        val titleBody = RequestBody.create(MediaType.parse("multipart/form-data"), title)
        val messageBody = RequestBody.create(MediaType.parse("multipart/form-data"), message)
        var body: MultipartBody.Part? = null
        if (file != null) {
            body = MultipartBody.Part.createFormData("imagem", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
        }
        return retrofit.create(TicketsEndpoints::class.java).postTicket(apiToken, condominiumId, titleBody, messageBody, body)
    }

    override fun getTickets(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Chamado>> {
        return retrofit.create(TicketsEndpoints::class.java).getTickets(apiToken, condominiumId)
    }

    /**
     * Dwellers Endpoint
     */
    override fun getDwellers(apiToken: String, condominiumId: Long, query: String): Observable<List<PerfilHabitacional>> {
        return retrofit.create(DwellersEndpoint::class.java).getDwellers(apiToken, condominiumId, query)
    }

    override fun approveDweller(apiToken: String, perfilHabitacionalId: Long, isApproved: Boolean): Observable<ResponseBody> {
        return retrofit.create(DwellersEndpoint::class.java).approveDweller(apiToken, perfilHabitacionalId, isApproved)
    }

    override fun getBlockedDwellers(apiToken: String, condominiumId: Long): Observable<List<PerfilHabitacional>> {
        return retrofit.create(DwellersEndpoint::class.java).getBlockedDwellers(apiToken, condominiumId)
    }

    override fun approveDwellerCall(apiToken: String, perfilHabitacionalId: Long, isApproved: Boolean): Call<ResponseBody> {
        return retrofit.create(DwellersEndpoint::class.java).approveDwellerCall(apiToken, perfilHabitacionalId, isApproved)
    }

    /**
     * Notifications
     */
    override fun getNotifications(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Notificacao>> {
        return retrofit.create(NotificationsEndpoints::class.java).getNotifications(apiToken, condominiumId)
    }

    /**
     * Reservations
     */
    override fun getSharedResources(apiToken: String, condominiumId: Long): Observable<GenericListResponse<BemComum>> {
        return retrofit.create(ReservationsEndpoints::class.java).getSharedResources(apiToken, condominiumId)
    }

    override fun getResourceAvailability(apiToken: String, resourceId: Long, date: String): Observable<GenericListResponse<DisponibilidadeBem>> {
        return retrofit.create(ReservationsEndpoints::class.java).getResourceAvailability(apiToken, resourceId, date)
    }

    override fun reserveAvailability(apiToken: String, availabilityId: Long, dataDesejada: String): Observable<GenericResponse<ReservaBemComum>> {
        return retrofit.create(ReservationsEndpoints::class.java).reserveAvailability(apiToken, availabilityId, dataDesejada)
    }

    override fun getReservations(apiToken: String, condominiumId: Long): Observable<GenericListResponse<ReservaBemComum>> {
        return retrofit.create(ReservationsEndpoints::class.java).getReservations(apiToken, condominiumId)
    }

    override fun getSharedResourcesDays(apiToken: String, condominiumId: Long, resourceId: Long, month: Int, year: Int): Observable<GenericListResponse<DiaBemComum>> {
        return retrofit.create(ReservationsEndpoints::class.java).getSharedResourcesDays(apiToken, condominiumId, resourceId, month, year)
    }

    override fun cancelReservation(apiToken: String, reservationId: Long): Observable<ResponseBody> {
        return retrofit.create(ReservationsEndpoints::class.java).cancelReservation(apiToken, reservationId)
    }

    /**
     * Comments
     */
    override fun postComment(apiToken: String, feedId: Long, text: String): Observable<Comentario> {
        return retrofit.create(CommentsEndpoints::class.java).postComment(apiToken, feedId, text)
    }

    override fun getComments(apiToken: String, feedId: Long): Observable<GenericListResponse<Comentario>> {
        return retrofit.create(CommentsEndpoints::class.java).getComments(apiToken, feedId)
    }

    override fun deleteComment(apiToken: String, commentId: Long): Observable<ResponseBody> {
        return retrofit.create(CommentsEndpoints::class.java).deleteComment(apiToken, commentId)
    }
}