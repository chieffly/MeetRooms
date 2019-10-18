package ru.chieffly.meetroom.view.reserve

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_reserve.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.utils.NotificationUtils
import ru.chieffly.meetroom.view.base.PresenterActivity
import ru.chieffly.meetroom.view.roomlist.RoomlistActivity


class ReserveActivity : PresenterActivity(), ReserveView {

    private lateinit var currentRoom: Meetroom

    @InjectPresenter
    override lateinit var presenter: ReservePresenter

    @ProvidePresenter
    fun provideLoginPresenter(): ReservePresenter {
        return ReservePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        currentRoom = intent.getSerializableExtra("roomId") as Meetroom
        presenter.onInitScreen()
        initListeners()
        initViews()

        toolbar_actionbar.title = getString(R.string.str_to_book)

        setSupportActionBar(toolbar_actionbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        toolbar_actionbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initListeners() {
        btnChangeDate.setOnClickListener {
            presenter.onChangeDateClick()
        }

        btnSetTime.setOnClickListener {
            presenter.onChangeTimeClick()
        }

        butDateHelper.setOnClickListener {
            presenter.onDateHelperClick()
        }

        btnReserve.setOnClickListener {
            presenter.onSendReserve(currentRoom)
            NotificationUtils().setNotification(currentRoom,  this@ReserveActivity)
            val intent = Intent(this, RoomlistActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun initViews() {
        txtRoomName.text = currentRoom.name
        txtSeats.text = currentRoom.seats.toString()
        imgProjector.isVisible = currentRoom.hasProjector
        imgBoard.isVisible = currentRoom.hasBoard
    }

    override fun showDate(date: String) {
        textDate.text = date

    }

    override fun showTimeInterval(timeStart: String, timeEnd: String) {
        txtTimeInterval.text = getString(R.string.str_time_interval, timeStart, timeEnd)

    }

    override fun showRefresh() {
    }

    override fun hideRefresh() {
    }

    override fun showError(errorCode: Int) {
        val errorText = when (errorCode) {
            ERR_TIME_INPUT -> getString(R.string.err_time_interval)
            ERR_PASS_DAY -> getString(R.string.err_pass_day)
            else -> getString(R.string.err_empty)
        }

        val snackbar = Snackbar.make(
            layout,
            errorText,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(applicationContext, R.color.colorError)
        )
        snackbar.show()
    }


    override fun setDateHelpButtonText(case: Int) {
        when (case) {
            DATEHELPER_TODAY -> butDateHelper.text = getString(R.string.str_maybe_today)
            DATEHELPER_TOMORROW -> butDateHelper.text = getString(R.string.str_maybe_tomorrow)
            else -> butDateHelper.text = ""

        }
    }

    override fun showPickerStart(hour: Int, minute: Int) {
        val tpd =
            TimePickerDialog(
                this@ReserveActivity, tListenerStart,
                hour,
                minute, true
            )
        tpd.setTitle(getString(R.string.str_start_time))
        tpd.show()
    }

    override fun showPickerEnd(hour: Int, minute: Int) {
        val tpd =
            TimePickerDialog(
                this@ReserveActivity, tListenerEnd,
                hour,
                minute, true
            )
        tpd.setTitle(getString(R.string.str_end_time))
        tpd.show()
    }

    override fun showDatePickerStart(year: Int, month: Int, day: Int) {
        val dpd =
            DatePickerDialog(
                this@ReserveActivity, dListener,
                year,
                month,
                day
            )
        dpd.setTitle(getString(R.string.str_start_time))
        dpd.show()
    }


    var dListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        presenter.onDateSetListener(year, monthOfYear, dayOfMonth)
    }

    var tListenerStart: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            presenter.onTimeStartSetListener(hourOfDay, minute)

        }

    var tListenerEnd: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            presenter.onTimeEndSetListener(hourOfDay, minute)

        }
}