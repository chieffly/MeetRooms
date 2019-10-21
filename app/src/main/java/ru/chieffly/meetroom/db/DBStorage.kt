package ru.chieffly.meetroom.db

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.model.rooms.Meet
import ru.chieffly.meetroom.model.rooms.Meetroom
import javax.inject.Inject

class DBStorage {

    @Inject lateinit var meetroomDB : MeetroomDao
    @Inject lateinit var meetDB : MeetDao

    init {
        App.appComponent.inject(viewModel = this)
    }

    fun addRoomsInDB (listOfMems: List<Meetroom>) {
        listOfMems.forEach {
            Single.fromCallable {
                it.isFavorite=false
                meetroomDB.insert(it)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe()
        }
    }

    fun addMeetsInDB (listOfMems: List<Meet>) {
        listOfMems.forEach {
            Single.fromCallable {
                meetDB.insert(it)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    {},{ println("ERROR ${it.localizedMessage}")}
                )
        }
    }

    interface StorageOwner {

        fun obtainStorage(): DBStorage
    }

}
