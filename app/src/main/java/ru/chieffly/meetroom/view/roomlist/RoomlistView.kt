package ru.chieffly.meetroom.view.roomlist


import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.view.base.BaseView


interface RoomlistView : BaseView//    void showList(@NonNull List<Project> projects);
//
//    void openProfileFragment(@NonNull String username);
{
    fun showError()
    fun showList(list: List<Meetroom>)
    fun exitToLoginScreen()
}