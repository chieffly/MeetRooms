package ru.chieffly.meetroom.view.login

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.view.base.PresenterActivity
import ru.chieffly.meetroom.view.roomlist.RoomlistActivity


class LoginActivity : PresenterActivity(), LoginView {

    @InjectPresenter
    override lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun provideLoginPresenter(): LoginPresenter {
        return LoginPresenter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()
    }


    override fun showLoginError(errorId: Int) {
        inputLayoutPhone.error = getString(errorId)
    }

    override fun showPassError(errorId: Int) {
        inputLayoutPass.error = getString(errorId)
    }

    private fun initListeners() {
        txtPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        txtPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                clearHelpersPhone()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        txtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                clearHelpersPass()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (txtPass.length() < PASS_MIN_LENGTH) {
                    inputLayoutPass.helperText =
                        getString(R.string.pass_length, "" + PASS_MIN_LENGTH)
                }
            }
        })

        btnLogin.setOnClickListener {
            presenter.makeLogin(txtPhone.text.toString(), txtPass.text.toString())
        }
    }

    override fun clearHelpersPhone() {
        inputLayoutPhone.error = ""
        inputLayoutPhone.helperText = ""
    }

    fun clearHelpersPass() {
        inputLayoutPass.error = ""
        inputLayoutPass.helperText = ""
    }

    override fun openMainScreen() {
        val intent = Intent(applicationContext, RoomlistActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showRefresh() {
        btnLogin.isVisible = false
        progressbarLogin.isVisible = true
    }

    override fun showError(errorType: Int) {
        val snackbar = Snackbar.make(log_layout, errorGetText(errorType), Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(applicationContext, R.color.colorError)
        )
        snackbar.show()
    }

    fun errorGetText(errorType: Int): String {
        val errorText =
            when (errorType) {
                ERR_WRONGDATA -> getString(R.string.err_wrong_data)
                ERR_EMPTY -> getString(R.string.err_empty)
                ERR_SHORT -> getString(R.string.pass_length) + PASS_MIN_LENGTH
                else -> getString(R.string.err_error)
            }
        return errorText
    }

    override fun hideRefresh() {
        btnLogin.isVisible = true
        progressbarLogin.isVisible = false
    }
}