package ru.chieffly.meetroom.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.db.AppDatabase
import ru.chieffly.meetroom.db.DBStorage
import ru.chieffly.meetroom.db.MeetDao
import ru.chieffly.meetroom.db.MeetroomDao
import ru.chieffly.meetroom.utils.UserStorage
import javax.inject.Singleton

@Module
class AppModule(private val myApp: App) {
    private  var app: App = myApp

    @Provides @Singleton
    fun provideApp(): App = app

    @Provides @Singleton
    fun provideContext(): Context = app

    @Provides @Singleton
    fun provideStorage(context: Context): UserStorage =
        UserStorage(context)

    @Provides @Singleton
    fun providesMeetroomDao(db: AppDatabase) : MeetroomDao = db.meetroomDao()

    @Provides @Singleton
    fun providesMeetDao(db: AppDatabase) : MeetDao = db.meetDao()


    @Provides @Singleton
    fun provideDBStorage(db : AppDatabase): DBStorage = DBStorage()

//    @Provides @Singleton
//    fun provideSharedPreferences(): SharedPreferences = app.getSharedPreferences(app.getString(R.string.app_name), 0)


    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context,
                AppDatabase::class.java,
                "base").build()
}