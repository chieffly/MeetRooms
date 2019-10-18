package ru.chieffly.meetroom.view.base


import com.arellomobile.mvp.MvpPresenter

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Vladislav Falzan.
 */

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    protected val mCompositeDisposable = CompositeDisposable()

    fun disposeAll() {
        mCompositeDisposable.dispose()
    }
}
