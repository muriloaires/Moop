package brmobi.moop.di.module

import android.app.Service
import dagger.Module

/**
 * Created by murilo aires on 24/02/2018.
 */
@Module
class ServiceModule(service: Service) {
    private var mService = service
}