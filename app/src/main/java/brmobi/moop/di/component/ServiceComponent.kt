package brmobi.moop.di.component

import brmobi.moop.di.PerService
import brmobi.moop.di.module.ServiceModule
import brmobi.moop.service.AprovarService
import dagger.Component

/**
 * Created by murilo aires on 24/02/2018.
 */
@PerService
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ServiceModule::class))
interface ServiceComponent {

    fun inject(aprovarService: AprovarService)

}