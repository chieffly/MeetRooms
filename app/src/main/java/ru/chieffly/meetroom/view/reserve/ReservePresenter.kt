package ru.chieffly.meetroom.view.reserve

import com.arellomobile.mvp.InjectViewState
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.model.auth.ReserveRequestDto
import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.utils.TimeUtil
import ru.chieffly.meetroom.view.base.BasePresenter
import java.util.*
import javax.inject.Inject

const val DATEHELPER_TODAY = 0
const val DATEHELPER_TOMORROW = 1
const val ERR_PASS_DAY = 0
const val ERR_TIME_INPUT = 1

@InjectViewState
class ReservePresenter : BasePresenter<ReserveView>() {

    @Inject
    lateinit var app: App

    var locale = Locale("ru", "RU")
    var tz = TimeZone.getTimeZone("GMT+3")
    val dateAndTimeNow = Calendar.getInstance(tz, locale)
    val dateNowAsString =
        TimeUtil.getCalendarTimeFormated(dateAndTimeNow, TimeUtil.dateFormatToDays)
    val dateAndTimeStart = Calendar.getInstance(tz, locale)
    val dateAndTimeEnd = Calendar.getInstance(tz, locale)


    init {
        dateAndTimeEnd.add(Calendar.MINUTE, 10)
        App.appComponent.inject(viewModel = this)
    }

    fun onInitScreen() {
        viewState.showDate(
            TimeUtil.getCalendarTimeFormated(
                dateAndTimeStart,
                TimeUtil.dateFormatToDays
            ) + " (сегодня)"
        )

        viewState.showTimeInterval(
            TimeUtil.getCalendarTimeFormated(dateAndTimeStart, TimeUtil.dateFormatOnlyHours),
            TimeUtil.getCalendarTimeFormated(dateAndTimeEnd, TimeUtil.dateFormatOnlyHours)
        )
    }

    fun onChangeDateClick() {
        viewState.showDatePickerStart(
            dateAndTimeStart.get(Calendar.YEAR),
            dateAndTimeStart.get(Calendar.MONTH),
            dateAndTimeStart.get(Calendar.DAY_OF_MONTH)
        )
    }


    fun onChangeTimeClick() {
        viewState.showPickerStart(
            dateAndTimeStart.get(Calendar.HOUR_OF_DAY),
            dateAndTimeStart.get(Calendar.MINUTE)
        )
    }

    fun onDateHelperClick() {

        val start = TimeUtil.getDate(dateAndTimeStart.time.time)
        val now = TimeUtil.getDate(dateAndTimeNow.time.time)

        val dat1 = TimeUtil.dateFormatToDays.parse(start)
        val dat2 = TimeUtil.dateFormatToDays.parse(now)

        if (dat1.equals(dat2)) {
            viewState.setDateHelpButtonText(DATEHELPER_TODAY)
            dateAndTimeStart.add(Calendar.DAY_OF_YEAR, 1)
            dateAndTimeEnd.add(Calendar.DAY_OF_YEAR, 1)
            viewState.showDate(
                TimeUtil.getCalendarTimeFormated(dateAndTimeStart, TimeUtil.dateFormatToDays)
            )
        } else {
            viewState.setDateHelpButtonText(DATEHELPER_TOMORROW)
            dateAndTimeStart.set(Calendar.DAY_OF_YEAR, dateAndTimeNow.get(Calendar.DAY_OF_YEAR))
            dateAndTimeEnd.set(Calendar.DAY_OF_YEAR, dateAndTimeNow.get(Calendar.DAY_OF_YEAR))
            viewState.showDate(
                dateNowAsString + " (сегодня)"
            )
        }

    }

    fun onDateSetListener(year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val tempCalendar = Calendar.getInstance(tz, locale)
        tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        tempCalendar.set(Calendar.MONTH, monthOfYear)
        tempCalendar.set(Calendar.YEAR, year)
        val dateAsString = TimeUtil.getCalendarTimeFormated(tempCalendar, TimeUtil.dateFormatToDays)
        println(tempCalendar.time)
        if (tempCalendar.before(dateAndTimeNow)) {
            viewState.showError(ERR_PASS_DAY)
        } else {
            dateAndTimeStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateAndTimeStart.set(Calendar.MONTH, monthOfYear)
            dateAndTimeStart.set(Calendar.YEAR, year)
            dateAndTimeEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateAndTimeEnd.set(Calendar.MONTH, monthOfYear)
            dateAndTimeEnd.set(Calendar.YEAR, year)

            viewState.setDateHelpButtonText(DATEHELPER_TODAY)
            viewState.showDate(
                TimeUtil.getCalendarTimeFormated(dateAndTimeStart, TimeUtil.dateFormatToDays)
            )
        }

        if (dateAsString.equals(dateNowAsString)) {
            viewState.showDate(dateNowAsString + " (сегодня)")
            viewState.setDateHelpButtonText(DATEHELPER_TOMORROW)

        }
    }

    fun onTimeStartSetListener(hourOfDay: Int, minute: Int) {
        dateAndTimeStart.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dateAndTimeStart.set(Calendar.MINUTE, minute)
        viewState.showPickerEnd(
            dateAndTimeEnd.get(Calendar.HOUR_OF_DAY), dateAndTimeEnd.get(
                Calendar.MINUTE
            )
        )
    }


    fun onTimeEndSetListener(hourOfDay: Int, minute: Int) {
        dateAndTimeEnd.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dateAndTimeEnd.set(Calendar.MINUTE, minute)

        val data =
            TimeUtil.getCalendarTimeFormated(dateAndTimeStart, TimeUtil.dateFormatOnlyHours)
        val data2 =
            TimeUtil.getCalendarTimeFormated(dateAndTimeEnd, TimeUtil.dateFormatOnlyHours)

        if (dateAndTimeStart.after(dateAndTimeEnd)) {
            viewState.showError(ERR_TIME_INPUT)
        } else viewState.showTimeInterval(data, data2)
    }


    fun onSendReserve(room: Meetroom) {
        val request = ReserveRequestDto(
            dateAndTimeStart = dateAndTimeStart.timeInMillis,
            dateAndTimeEnd = dateAndTimeEnd.timeInMillis,
            roomId = room.id,
            userId = 111
        )

    }
}