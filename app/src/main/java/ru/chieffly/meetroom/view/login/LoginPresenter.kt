package ru.chieffly.meetroom.view.login

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.model.auth.LoginUserRequestDto
import ru.chieffly.meetroom.net.AuthApi
import ru.chieffly.meetroom.utils.UserStorage
import ru.chieffly.meetroom.view.base.BasePresenter
import javax.inject.Inject

const val ERR_ERROR = 0
const val ERR_WRONGDATA = 1
const val ERR_EMPTY = 2
const val ERR_SHORT = 4
const val PASS_MIN_LENGTH = 6

@InjectViewState
class LoginPresenter () : BasePresenter<LoginView>() {
    @Inject
    lateinit var app: App

    @Inject
    lateinit var authApi: AuthApi
    @Inject
    lateinit var storage: UserStorage

    init {
        App.appComponent.inject(viewModel = this)
    }


    fun makeLogin (login: String, pass: String) {
        viewState.clearHelpersPhone ()
        if (login.length == 0 || pass.length == 0) {
            hasEmptyFields(login, pass)
        } else {
            viewState.showRefresh()
            val acc = LoginUserRequestDto(
                login,
                pass
            )

            mCompositeDisposable.add(authApi.loginRequest(acc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ authInfo ->
                    viewState.hideRefresh()
                    storage.saveToken(authInfo.access_token)
                    storage.saveRefreshToken(authInfo.refresh_token)
                    viewState.openMainScreen()
                }, {
                    viewState.showError(0)
                    viewState.hideRefresh()
                    println(it.localizedMessage)
                },{
                    viewState.showError(0)
                })
            )
        }
    }
    private fun hasEmptyFields(login: String, pass: String): Boolean {
        viewState.showError (ERR_WRONGDATA)
        var fail = false;
        if (login.length == 0) {
            viewState.showLoginError(R.string.err_empty)
            fail = true
        }
        if (pass.length < PASS_MIN_LENGTH) {
            if (pass.length == 0)
                viewState.showPassError (R.string.err_empty)
            else
                viewState.showError (ERR_SHORT)

            fail = true
        }
        return fail
    }

}