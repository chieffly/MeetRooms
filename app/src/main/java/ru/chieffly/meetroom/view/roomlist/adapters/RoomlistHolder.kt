package ru.chieffly.meetroom.view.roomlist.adapters

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.meetroom.App
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.db.MeetDao
import ru.chieffly.meetroom.db.MeetroomDao
import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.utils.TimeUtil
import java.util.*
import javax.inject.Inject




class RoomlistHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imgProjector: ImageView = itemView.findViewById(ru.chieffly.meetroom.R.id.imgProjector)
    private val imgBoard: ImageView = itemView.findViewById(ru.chieffly.meetroom.R.id.imgBoard)
    private val imgFavorite: ImageView = itemView.findViewById(ru.chieffly.meetroom.R.id.imgFavorite)

    private val txtRoomName: TextView = itemView.findViewById(ru.chieffly.meetroom.R.id.txtRoomName)
    private val txtChairs: TextView = itemView.findViewById(ru.chieffly.meetroom.R.id.txtSeats)
    private val txtTime: TextView = itemView.findViewById(ru.chieffly.meetroom.R.id.txtTime)
    private val txtDescription: TextView = itemView.findViewById(ru.chieffly.meetroom.R.id.txtDescription)
    private val progressBar: ProgressBar = itemView.findViewById(ru.chieffly.meetroom.R.id.progressBar)
    private val imgTime: ImageView = itemView.findViewById(ru.chieffly.meetroom.R.id.imgTime)

    @Inject
    lateinit var app: App

    @Inject
    lateinit var meetDB: MeetDao
    @Inject
    lateinit var meetroomDB: MeetroomDao

    init {
        App.appComponent.inject(viewModel = this)
    }

    fun showRefresh() {
        progressBar.isVisible = true
        txtTime.text = app.getString(ru.chieffly.meetroom.R.string.str_data_is_update)
        imgTime.isVisible = false
    }

    fun hideRefresh() {
        progressBar.isVisible = false
    }

    fun showRoomIsFree() {

        txtTime.text = app.getString(ru.chieffly.meetroom.R.string.str_room_free)
    }

    fun showTime(timeStart: String, timeEnd: String) {
        imgTime.isVisible = true
        txtTime.text = app.getString(ru.chieffly.meetroom.R.string.str_time_interval, timeStart, timeEnd)

    }

    fun showError(errorCode: Int) {
    }

    fun bind(item: Meetroom, onItemClickListener: RoomlistAdapter.OnItemClickListener) {
        txtRoomName.text = item.name
        txtChairs.text = item.seats.toString()
        txtDescription.text = item.description

        imgBoard.isVisible = item.hasBoard
        imgProjector.isVisible = item.hasProjector

        if (item.isFavorite) {
            imgFavorite.setImageResource(R.drawable.ic_favorite)
            imgFavorite.setColorFilter(ContextCompat.getColor(app, R.color.colorAccent))

        } else {
            imgFavorite.setImageResource(R.drawable.ic_favorite_border)
            imgFavorite.setColorFilter(ContextCompat.getColor(app, R.color.colorGray))
        }


        imgFavorite.setOnClickListener {

            item.isFavorite = !item.isFavorite

            updateDB(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    imgFavorite.setImageResource(R.drawable.ic_favorite)
                    imgFavorite.setColorFilter(ContextCompat.getColor(app, R.color.colorAccent))
                }
        }


        itemView.setOnClickListener { v ->
            onItemClickListener.onItemClick(item)
        }

        showRefresh()
        val now = Date().time
        meetDB.getNextMeets(item.id, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listMeets ->
                if (listMeets.size == 0) {
                    hideRefresh()
                    showRoomIsFree()
                } else {

                    val start = TimeUtil.parseUnixTimeFormated(
                        listMeets[0].timeStart,
                        TimeUtil.dateFormatOnlyHours
                    )
                    val end = TimeUtil.parseUnixTimeFormated(
                        listMeets[0].timeEnd,
                        TimeUtil.dateFormatOnlyHours
                    )
                    hideRefresh()
                    showTime(start, end)
                }
            }
    }

    fun updateDB (item: Meetroom): Completable = Completable.fromAction {
        meetroomDB.update(item)
    }

    companion object {
        private val FIRST_OWNER_INDEX = 0
    }
}
