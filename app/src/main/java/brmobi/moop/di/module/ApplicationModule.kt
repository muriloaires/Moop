package brmobi.moop.di.module

import android.app.Application
import android.content.Context
import br.com.airescovit.clim.di.ApplicationContext
import br.com.airescovit.clim.di.DatabaseInfo
import br.com.airescovit.clim.di.PreferenceInfo
import brmobi.moop.data.AppDataManager
import brmobi.moop.data.DataManager
import brmobi.moop.data.db.AppDbHelper
import brmobi.moop.data.db.DbHelper
import brmobi.moop.data.media.AppMediaHelper
import brmobi.moop.data.media.MediaHelper
import brmobi.moop.data.network.ApiHelper
import brmobi.moop.data.network.AppApiHelper
import brmobi.moop.data.prefs.AppPreferenceHelper
import brmobi.moop.data.prefs.PreferenceHelper
import brmobi.moop.utils.AppConstants
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by murilo aires on 19/02/2018.
 */
@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }


    @Provides
    @DatabaseInfo
    fun providesDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @PreferenceInfo
    fun providePrerenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL + AppConstants.API_VERSION)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat(AppConstants.DATE_FORMAT).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper {
        return appPreferenceHelper
    }

    @Provides
    @Singleton
    fun provideMediaHelper(appMediaHelper: AppMediaHelper): MediaHelper = appMediaHelper

    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

}