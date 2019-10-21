package ru.chieffly.meetroom.view.details

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.db.MeetDao
import ru.chieffly.meetroom.db.MeetroomDao
import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.net.AuthApi
import ru.chieffly.meetroom.view.base.BasePresenter
import java.util.*
import javax.inject.Inject

@InjectViewState
class DetailsPresenter : BasePresenter<DetailsView>() {
    @Inject
    lateinit var app: App

    @Inject
    lateinit var authApi: AuthApi
    @Inject
    lateinit var meetDB: MeetDao
    @Inject
    lateinit var meetroomDB: MeetroomDao

    init {
        App.appComponent.inject(viewModel = this)
    }

    fun getMeets(roomId: Long) {

        meetDB.getNextMeets(room_id = roomId, timeNow = System.currentTimeMillis())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                    listMem ->
                viewState.showList(list = listMem)
            }
    }

    fun updateDB (item: Meetroom): Completable = Completable.fromAction {
        meetroomDB.update(meetroom = item)
    }
}