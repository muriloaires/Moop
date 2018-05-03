package brmobi.moop.data.db

import brmobi.moop.data.db.model.Condominio
import io.reactivex.Observable

/**
 * Created by murilo aires on 19/02/2018.
 */
interface DbHelper {
    fun insertCondominium(condominium: Condominio): Observable<Long>
    fun insertCondominiumList(condominiuns: List<Condominio>): Observable<Unit>
    fun loadCondominium(condominiumId: Long) : Observable<Condominio>

}