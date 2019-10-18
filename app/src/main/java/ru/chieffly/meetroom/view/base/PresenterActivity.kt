package ru.chieffly.meetroom.view.base

import com.arellomobile.mvp.MvpAppCompatActivity

abstract class PresenterActivity : MvpAppCompatActivity() {

    protected abstract val presenter : BasePresenter<*>

    override fun onDestroy() {
        presenter?.let {it.disposeAll()}
        super.onDestroy()
    }
}
