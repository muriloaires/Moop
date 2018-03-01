package brmobi.moop.data.network

import brmobi.moop.data.db.model.Condominio
import brmobi.moop.data.network.model.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.File

/**
 * Created by murilo aires on 19/02/2018.
 */
interface ApiHelper {

    /**
     * Condominium
     */
    fun getUserCondominiuns(apiToken: String): Observable<GenericListResponse<Condominio>>

    fun doDetachFromCondominium(apiToken: String, condominiumId: Long): Observable<ResponseBody>

    fun getCondominiumFromZipCode(apiToken: String, zipCode: String): Observable<GenericListResponse<Condominio>>

    fun getCondominiumBlocs(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Bloco>>

    fun getBlocUnits(apiToken: String, blocId: Long, condominiumId: Long): Observable<GenericListResponse<Unidade>>

    fun doRegisterUnit(apiToken: String, blocoId: Long, isOwner: Boolean, isDweller: Boolean, number: String): Observable<ResponseBody>

    fun doRegisterCondominium(apiToken: String, zipCode: String, name: String, sreet: String, number: String, phoneNumber: String,
                              isHorizontal: Boolean, blocs: List<String>): Observable<GenericResponse<CadastroCondominio>>

    fun doRegisterBloc(apiToken: String, condominiumId: Long, name: String, number: String): Observable<ResponseBody>

    fun getCondominiumDetail(apiToken: String, condominiumId: Long): Observable<Condominio>


    /**
     * User
     */
    fun doLogin(phoneNumber: String, deviceToken: String, deviceType: String): Observable<Usuario>

    fun doRegister(name: String, email: String, phoneNumber: String, deviceToken: String, deviceType: String, avatarUrl: String?, avatar: File?): Observable<Usuario>

    fun updateUser(apiToken: String, name: String, avatar: File?): Observable<Usuario>

    fun generateNewPassword(apiToken: String): Observable<Senha>


    /**
     * Feed
     */
    fun getFeed(apiToken: String, condiminiumId: Long, limit: Int, offset: Int): Observable<GenericListResponse<FeedItem>>

    fun postFeed(apiToken: String, condiminiumId: Long, title: String, text: String?, img: File?): Observable<FeedItem>

    fun likeFeedItem(apiToken: String, feedId: Long): Observable<ResponseBody>

    fun unlikeFeedItem(apiToken: String, feedId: Long): Observable<ResponseBody>

    fun deleteFeed(apiToken: String, feedId: Long): Observable<ResponseBody>

    /**
     * Messages
     */
    fun getUserLastMessages(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Mensagem>>

    fun getChatMessages(apiToken: String, userDestinyId: Long, condominiumId: Long): Observable<GenericListResponse<Mensagem>>

    fun postMessage(apiToken: String, userDestinyId: Long, condominiumId: Long, message: String): Observable<Mensagem>

    fun deleteMessage(apiToken: String, messageId: Long): Observable<ResponseBody>


    /**
     * Tickets
     */
    fun postTicket(apiToken: String, condominiumId: Long, title: String, message: String, file: File?): Observable<ResponseBody>

    fun getTickets(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Chamado>>

    /**
     * Dwellers
     */
    fun getDwellers(apiToken: String, condominiumId: Long, query: String): Observable<List<PerfilHabitacional>>

    fun approveDweller(apiToken: String, perfilHabitacionalId: Long, isApproved: Boolean): Observable<ResponseBody>

    fun approveDwellerCall(apiToken: String, perfilHabitacionalId: Long, isApproved: Boolean): Call<ResponseBody>

    fun getBlockedDwellers(apiToken: String, condominiumId: Long): Observable<List<PerfilHabitacional>>

    /**
     * Notifications
     */
    fun getNotifications(apiToken: String, condominiumId: Long): Observable<GenericListResponse<Notificacao>>

    /**
     *Reservation
     */
    fun getSharedResources(apiToken: String, condominiumId: Long): Observable<GenericListResponse<BemComum>>

    fun getResourceAvailability(apiToken: String, resourceId: Long, date: String): Observable<GenericListResponse<DisponibilidadeBem>>

    fun reserveAvailability(apiToken: String, availabilityId: Long, dataDesejada:String): Observable<GenericResponse<ReservaBemComum>>

    fun getReservations(apiToken: String, condominiumId: Long): Observable<GenericListResponse<ReservaBemComum>>

    fun getSharedResourcesDays(apiToken: String, condominiumId: Long, resourceId: Long, month: Int, year: Int): Observable<GenericListResponse<DiaBemComum>>

    fun cancelReservation(apiToken: String, reservationId: Long): Observable<ResponseBody>

    /**
     * Comments
     */
    fun postComment(apiToken: String, feedId: Long, text: String): Observable<Comentario>

    fun getComments(apiToken: String, feedId: Long): Observable<GenericListResponse<Comentario>>

    fun deleteComment(apiToken: String, commentId: Long): Observable<ResponseBody>


}