package brmobi.moop.di.component

import br.com.airescovit.clim.di.PerActivity
import brmobi.moop.di.module.ActivityModule
import brmobi.moop.ui.reservation.AvailabilityFragment
import brmobi.moop.ui.reservation.DisponibilityActivity
import brmobi.moop.ui.reservation.SharedResourceCalendarFragment
import brmobi.moop.ui.reservation.SharedResourceDescriptionFragment
import brmobi.moop.ui.splash.SplashScreen
import brmobi.moop.ui.comments.CommentsActivity
import brmobi.moop.ui.condominium.MyCondominiumActivity
import brmobi.moop.ui.condominium.add.*
import brmobi.moop.ui.dwellers.ApproveDwellersActivity
import brmobi.moop.ui.dwellers.DwellersListFragment
import brmobi.moop.ui.feed.FeedFragment
import brmobi.moop.ui.login.LoginActivity
import brmobi.moop.ui.login.LoginFragment
import brmobi.moop.ui.login.RegistroFragment
import brmobi.moop.ui.main.MoopActivity
import brmobi.moop.ui.messages.MensagensFragment
import brmobi.moop.ui.messages.MessagesActivity
import brmobi.moop.ui.notifications.NotificationFragment
import brmobi.moop.ui.profile.EditProfileActivity
import brmobi.moop.ui.publication.NewPostActivity
import brmobi.moop.ui.reservation.MyReservationsFragment
import brmobi.moop.ui.reservation.SharedResourcesFragment
import brmobi.moop.ui.tickets.NewTicketFragment
import brmobi.moop.ui.tickets.TicketsListFragment
import dagger.Component

/**
 * Created by murilo aires on 19/02/2018.
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(moopActivity: MoopActivity)

    fun inject(dwellersListFragment: DwellersListFragment)

    fun inject(approveDwellersActivity: ApproveDwellersActivity)

    fun inject(disponibilityActivity: DisponibilityActivity)

    fun inject(commentsActivity: CommentsActivity)

    fun inject(addCondominioActivity: AddCondominioActivity)

    fun inject(myCondominiumActivity: MyCondominiumActivity)

    fun inject(loginActivity: LoginActivity)

    fun inject(editProfileActivity: EditProfileActivity)

    fun inject(messagesActivity: MessagesActivity)

    fun inject(newPostActivity: NewPostActivity)

    fun inject(splashScreen: SplashScreen)

    fun inject(notificationFragment: NotificationFragment)

    fun inject(condominiunsFragment: CondominiunsFragment)

    fun inject(condominiumRegisterFragment: CondominiumRegisterFragment)

    fun inject(blocsFragment: BlocsFragment)

    fun inject(addBlocFragment: AddBlocFragment)

    fun inject(sharedResourceDescriptionFragment: SharedResourceDescriptionFragment)

    fun inject(sharedResourceCalendarFragment: SharedResourceCalendarFragment)

    fun inject(availabilityFragment: AvailabilityFragment)

    fun inject(loginFragment: LoginFragment)

    fun inject(myReservationFragment: MyReservationsFragment)

    fun inject(sharedResourcesFragment: SharedResourcesFragment)

    fun inject(registerFragment: RegistroFragment)

    fun inject(feedFragment: FeedFragment)

    fun inject(messagesFragment: MensagensFragment)

    fun inject(newTicketFragment: NewTicketFragment)

    fun inject(ticketsListFragment: TicketsListFragment)


}