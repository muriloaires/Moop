package brmobi.moop.data.db

import android.content.Context
import br.com.airescovit.clim.di.ApplicationContext
import br.com.airescovit.clim.di.DatabaseInfo
import brmobi.moop.data.db.dao.DaoMaster
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by murilo aires on 19/02/2018.
 */
@Singleton
class DbOpenHelper @Inject constructor(@ApplicationContext context: Context, @DatabaseInfo name: String?) : DaoMaster.OpenHelper(context, name) {
}