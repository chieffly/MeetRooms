package ru.chieffly.meetroom.view.roomlist

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.db.DBStorage
import ru.chieffly.meetroom.db.MeetroomDao
import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.net.AuthApi
import ru.chieffly.meetroom.net.MeetroomApi
import ru.chieffly.meetroom.utils.TestMeetGenerator
import ru.chieffly.meetroom.utils.UserStorage
import ru.chieffly.meetroom.view.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class RoomlistPresenter : BasePresenter<RoomlistView>() {

    @Inject
    lateinit var roomApi: MeetroomApi

    @Inject
    lateinit var authApi: AuthApi

    @Inject
    lateinit var db: DBStorage

    @Inject
    lateinit var meetroomDB: MeetroomDao

    @Inject
    lateinit var storage: UserStorage

    init {
        App.appComponent.inject(viewModel = this)
    }


    fun updateRooms () {
        mCompositeDisposable.add(roomApi.getRooms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    listRooms ->
                db.addRoomsInDB(listRooms)
            } , { viewState.showError(0)
                viewState.hideRefresh()
                println("SHOW ERROR $it" )

            })
        )
    }

    fun updateMeets() {

        mCompositeDisposable.add(roomApi.getMeets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    listMeets ->
                viewState.hideRefresh()
                db.addMeetsInDB(listMeets)
            } , { viewState.showError(0)
                viewState.hideRefresh()
                println("SHOW ERROR $it" )
            }))
    }



    fun getProjects() {
        updateRooms()
        updateMeets()
    }

    fun addRooms (listRooms: List<Meetroom>) {
        listRooms.forEach {
                meetroomDB.insert(it)
        }
    }

    fun showRoomsFromDB ()  {
        meetroomDB.getall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                    listMem ->
                viewState.hideRefresh()
                viewState.showList(listMem)
            }
    }

    fun showFavRoomsFromDB ()  {
        meetroomDB.getFavs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                    listMem ->
                viewState.hideRefresh()
                viewState.showList(listMem)
            }
    }

    fun logoutRequest() {

        authApi.logoutRequest("")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ authInfo ->
                storage.clear()
                viewState.exitToLoginScreen()
            },{
                println("PROBLEM $it")
            })

    }
}
