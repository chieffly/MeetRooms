package ru.chieffly.meetroom.view.base

import com.arellomobile.mvp.MvpAppCompatFragment


/**
 * Created by Vladislav Falzan.
 */

abstract class PresenterFragment : MvpAppCompatFragment() {

    protected abstract val presenter : BasePresenter<*>


    override fun onDetach() {
        presenter?.let {it.disposeAll()}
        super.onDetach()
    }
}
