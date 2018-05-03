package brmobi.moop.di.component

import android.app.Application
import android.content.Context
import br.com.airescovit.clim.di.ApplicationContext
import brmobi.moop.MoopApplication
import brmobi.moop.data.DataManager
import brmobi.moop.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by murilo aires on 19/02/2018.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(app: MoopApplication)

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun getDataManager(): DataManager
}