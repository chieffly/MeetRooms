package ru.chieffly.meetroom.view.details

import ru.chieffly.meetroom.model.rooms.Meet
import ru.chieffly.meetroom.view.base.BaseView

interface DetailsView : BaseView {
    fun onRefreshData()
    fun showList(list: List <Meet>)
}