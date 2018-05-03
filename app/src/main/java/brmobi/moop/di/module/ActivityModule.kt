package brmobi.moop.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import br.com.airescovit.clim.di.ActivityContext
import br.com.airescovit.clim.di.PerActivity
import brmobi.moop.ui.splash.SplasMvpView
import brmobi.moop.ui.splash.SplashMvpPresenter
import brmobi.moop.ui.splash.SplashPresenter
import brmobi.moop.ui.comments.CommentsMvpPresenter
import brmobi.moop.ui.comments.CommentsMvpView
import brmobi.moop.ui.comments.CommentsPresenter
import brmobi.moop.ui.condominium.MyCondominiumMvpPresenter
import brmobi.moop.ui.condominium.MyCondominiumMvpView
import brmobi.moop.ui.condominium.MyCondominiumPresenter
import brmobi.moop.ui.condominium.add.*
import brmobi.moop.ui.dwellers.*
import brmobi.moop.ui.feed.FeedMvpPresenter
import brmobi.moop.ui.feed.FeedMvpView
import brmobi.moop.ui.feed.FeedPresenter
import brmobi.moop.ui.login.*
import brmobi.moop.ui.main.MainMvpPresenter
import brmobi.moop.ui.main.MainMvpView
import brmobi.moop.ui.main.MainPresenter
import brmobi.moop.ui.messages.*
import brmobi.moop.ui.notifications.NotificationMvpPresenter
import brmobi.moop.ui.notifications.NotificationMvpView
import brmobi.moop.ui.notifications.NotificationPresenter
import brmobi.moop.ui.profile.EditProfileMvpPresenter
import brmobi.moop.ui.profile.EditProfileMvpView
import brmobi.moop.ui.profile.EditProfilePresenter
import brmobi.moop.ui.publication.NewPostMvpPresenter
import brmobi.moop.ui.publication.NewPostMvpView
import brmobi.moop.ui.publication.NewPostPresenter
import brmobi.moop.ui.reservation.*
import brmobi.moop.ui.tickets.*
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by murilo aires on 19/02/2018.
 */
@Module
class ActivityModule(activity: AppCompatActivity) {

    var mActivity: AppCompatActivity = activity

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return mActivity
    }

    @Provides
    fun provideActivity(): AppCompatActivity {
        return mActivity
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PerActivity
    fun provideMainPresenter(mPresenter: MainPresenter<MainMvpView>): MainMvpPresenter<MainMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideDwellersPresenter(mPresenter: DwellersPresenter<DwellersMvpView>): DwellersMvpPresenter<DwellersMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideCommentsPresenter(mPresenter: CommentsPresenter<CommentsMvpView>): CommentsMvpPresenter<CommentsMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideApproveDwellersPresenter(mPresenter: ApproveDwellersPresenter<ApproveDwellersMvpView>): ApproveDwellersMvpPresenter<ApproveDwellersMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideMyCondominiumPresenter(mPresenter: MyCondominiumPresenter<MyCondominiumMvpView>): MyCondominiumMvpPresenter<MyCondominiumMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideEditProfilePresenter(mPresenter: EditProfilePresenter<EditProfileMvpView>): EditProfileMvpPresenter<EditProfileMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideMessagesPresenter(mPresenter: MessagesPresenter<MessagesMvpView>): MessagesMvpPresenter<MessagesMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideNewPostPresenter(mPresenter: NewPostPresenter<NewPostMvpView>): NewPostMvpPresenter<NewPostMvpView> = mPresenter


    @Provides
    @PerActivity
    fun provideLoginActivityPresenter(mPresenter: LoginActivityPresenter<LoginActivityMpView>): LoginActivityMvpPresenter<LoginActivityMpView> = mPresenter

    @Provides
    @PerActivity
    fun provideSplashPresenter(mPresenter: SplashPresenter<SplasMvpView>): SplashMvpPresenter<SplasMvpView> = mPresenter

    @Provides
    @PerActivity
    fun provideAddCondominiumPresenter(mPresenter: AddCondominiumPresenter<AddCondominiumMvpView>): AddCondominiumMvpPresenter<AddCondominiumMvpView> = mPresenter

    @Provides
    fun provideNotificationPresenter(mPresenter: NotificationPresenter<NotificationMvpView>): NotificationMvpPresenter<NotificationMvpView> = mPresenter

    @Provides
    fun provideAddBlocPresenter(mPresenter: AddBlocPresenter<AddBlocMvpView>): AddBlocMvpPresenter<AddBlocMvpView> = mPresenter

    @Provides
    fun provideMyReservationsPresenter(mPresenter: MyReservationsPresenter<MyReservationsMvpView>): MyReservationsMvpPresenter<MyReservationsMvpView> = mPresenter

    @Provides
    fun provideCondominiunsPresenter(mPresenter: CondominiumPresenter<CondominiumMvpView>): CondominiumMvpPresenter<CondominiumMvpView> = mPresenter

    @Provides
    fun provideLoginPresenter(mPresenter: LoginPresenter<LoginMvpView>): LoginMvpPresenter<LoginMvpView> = mPresenter

    @Provides
    fun provideRegisterPresenter(mPresenter: RegisterPresenter<RegisterMvpView>): RegisterMvpPresenter<RegisterMvpView> = mPresenter

    @Provides
    fun provideCondominiumRegisterPresenter(mPresenter: CondominiumRegisterPresenter<CondominiumRegisterMvpView>): CondominiumRegisterMvpPresenter<CondominiumRegisterMvpView> = mPresenter

    @Provides
    fun provideBlocPresenter(mPresenter: BlocPresenter<BlocMvpView>): BlocMvpPresenter<BlocMvpView> = mPresenter

    @Provides
    fun provideFeedPresenter(mPresenter: FeedPresenter<FeedMvpView>): FeedMvpPresenter<FeedMvpView> = mPresenter

    @Provides
    fun provideLastMessagesPresenter(mPresenter: LastMessagesPresenter<LastMessagesMvpView>): LastMessagesMvpPresenter<LastMessagesMvpView> = mPresenter

    @Provides
    fun provideNewTicketPresenter(mPresenter: NewTicketPresenter<NewTicketMvpView>): NewTicketMvpPresenter<NewTicketMvpView> = mPresenter

    @Provides
    fun provideTickerListPresenter(mPresenter: TicketListPresenter<TicketListMvpView>): TicketListMvpPresenter<TicketListMvpView> = mPresenter

    @Provides
    fun provideSharedResourcesPresenter(mPresenter: SharedResourcesPresenter<SharedResourcesMvpView>): SharedResourcesMvpPresenter<SharedResourcesMvpView> = mPresenter

    @PerActivity
    @Provides
    fun provideDisponibilityPresenter(mPresenter: DisponibilityPresenter<DisponibilityMvpView>): DisponibilityMvpPresenter<DisponibilityMvpView> = mPresenter

    @Provides
    fun provideSharedResourveDescriptionPresenter(mPresenter: SharedResourveDescriptionPresenter<SharedResourceDescriptionMvpView>)
            : SharedResourveDescriptionMvpPresenter<SharedResourceDescriptionMvpView> = mPresenter

    @Provides
    fun provideSharedResourceCalendarPresenter(mPresneter: SharedResourceCalendarPresenter<SharedResourceCalendarMvpView>):
            SharedResourceCalendarMvpPresenter<SharedResourceCalendarMvpView> = mPresneter

    @Provides
    fun provideAvailabilityPresenter(mPresenter: AvailabilityPresenter<AvailabilityMvpView>)
            : AvailabilityMvpPresenter<AvailabilityMvpView> = mPresenter
}