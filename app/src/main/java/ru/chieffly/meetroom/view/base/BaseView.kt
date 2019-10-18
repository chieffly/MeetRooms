package ru.chieffly.meetroom.view.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Vladislav Falzan.
 */

@StateStrategyType (AddToEndSingleStrategy::class)
interface BaseView : MvpView {

    fun showRefresh()

    fun hideRefresh()

    fun showError(errorCode: Int)
}
