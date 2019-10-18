package ru.chieffly.meetroom

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ru.chieffly.meetroom.di.AppComponent
import ru.chieffly.meetroom.di.AppModule
import ru.chieffly.meetroom.di.DaggerAppComponent

class App : Application() {

    init {
        instance = this
    }
//    lateinit var appComponent: AppComponent

    companion object {
        private var instance: App? = null
        lateinit var appComponent: AppComponent

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }


    override fun onCreate() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this@App))
            .build()

    }

}