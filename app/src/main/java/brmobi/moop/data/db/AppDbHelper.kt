package brmobi.moop.data.db

import brmobi.moop.data.db.dao.DaoMaster
import brmobi.moop.data.db.dao.DaoSession
import brmobi.moop.data.db.model.Condominio
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by murilo aires on 19/02/2018.
 */
@Singleton
class AppDbHelper @Inject constructor(dbOpenHelper: DbOpenHelper) : DbHelper {

    private var mDaoSession: DaoSession = DaoMaster(dbOpenHelper.writableDatabase).newSession()

    override fun insertCondominium(condominium: Condominio): Observable<Long> {
        return Observable.fromCallable<Long> {
            mDaoSession.condominioDao.insertOrReplace(condominium)
        }
    }

    override fun insertCondominiumList(condominiuns: List<Condominio>): Observable<Unit> {
        return Observable.fromCallable {
            for (condominium: Condominio in condominiuns) {
                mDaoSession.condominioDao.insertOrReplace(condominium)
            }
        }
    }

    override fun loadCondominium(condominiumId: Long): Observable<Condominio> {
        return Observable.fromCallable<Condominio>({
            mDaoSession.condominioDao.load(condominiumId)
        })
    }
}