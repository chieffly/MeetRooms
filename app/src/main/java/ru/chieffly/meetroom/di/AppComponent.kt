package ru.chieffly.meetroom.di


import dagger.Component
import ru.chieffly.meetroom.db.DBStorage
import ru.chieffly.meetroom.view.login.LoginPresenter
import ru.chieffly.meetroom.view.reserve.ReservePresenter
import ru.chieffly.meetroom.view.details.DetailsPresenter
import ru.chieffly.meetroom.view.roomlist.RoomlistPresenter
import ru.chieffly.meetroom.view.roomlist.adapters.RoomlistHolder
import ru.chieffly.meetroom.view.splash.SplashActivity
import javax.inject.Singleton

@Component(modules = [AppModule::class, RemoteModule::class])
@Singleton
interface AppComponent {


    fun inject(viewModel: SplashActivity)
    fun inject(viewModel: DBStorage)
    fun inject(viewModel: LoginPresenter)
    fun inject(viewModel: RoomlistPresenter)
    fun inject(viewModel: DetailsPresenter)
    fun inject(viewModel: ReservePresenter)
    fun inject(viewModel: RoomlistHolder)
}