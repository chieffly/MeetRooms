package ru.chieffly.meetroom.view.login

import ru.chieffly.meetroom.view.base.BaseView

interface LoginView : BaseView {
    fun showLoginError(errorType: Int)
    fun showPassError(errorType: Int)
    fun clearHelpersPhone ()
    fun openMainScreen()
}