package ru.chieffly.meetroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.chieffly.meetroom.model.rooms.Meet
import ru.chieffly.meetroom.model.rooms.Meetroom


@Database(entities = [Meetroom::class, Meet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun meetroomDao(): MeetroomDao
    abstract fun meetDao(): MeetDao

//companion object {
//
//    fun getDatabase(context: Context): AppDatabase = Room.databaseBuilder(
//        context, AppDatabase::class.java, RoomContract.databaseApp)
//        .build()
//}

}