package ru.chieffly.meetroom.view.reserve

import ru.chieffly.meetroom.view.base.BaseView

interface ReserveView: BaseView {

    fun showDate(date : String)
    fun showTimeInterval(timeStart : String, timeEnd : String)
    fun setDateHelpButtonText (case: Int)
    fun showDatePickerStart (year: Int, month: Int, day: Int)
    fun showPickerEnd (hour: Int, minute: Int)
    fun showPickerStart (hour: Int, minute: Int)
    }
