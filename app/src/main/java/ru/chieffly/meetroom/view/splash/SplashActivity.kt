package ru.chieffly.meetroom.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.utils.UserStorage
import ru.chieffly.meetroom.view.login.LoginActivity
import ru.chieffly.meetroom.view.roomlist.RoomlistActivity
import javax.inject.Inject

private const val DELAY: Long = 300

class SplashActivity : AppCompatActivity() {
    private var handler: Handler = Handler()

    @Inject
    lateinit var storage: UserStorage

    init {
        App.appComponent.inject(viewModel = this)
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            if (storage.getToken() !="null") {
                openMainScreen()
            } else  {
            openLoginScreen()
           }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(runnable, DELAY)
    }

    public override fun onDestroy() {

        handler.let {
            it.removeCallbacks(runnable)
        }
        super.onDestroy()
    }



    fun openLoginScreen() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun openMainScreen() {
        val intent = Intent(applicationContext, RoomlistActivity::class.java)
        startActivity(intent)
        finish()
    }
}