package ru.chieffly.meetroom.view.details.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.model.rooms.Meet
import ru.chieffly.meetroom.utils.TimeUtil
import ru.chieffly.meetroom.view.roomlist.adapters.DetailsAdapter


class DetailsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val txtDate: TextView
    private val txtTime: TextView
    private val txtPerson: TextView

    init {
        txtDate = itemView.findViewById(R.id.txtDate)
        txtTime = itemView.findViewById(R.id.txtTime)
        txtPerson = itemView.findViewById(R.id.txtPerson)
    }

    fun bind(item: Meet, onItemClickListener: DetailsAdapter.OnItemClickListener) {

        println("ALIVE TIME ST! "+ item.timeStart + "  " + TimeUtil.parseUnixTimeFormated (item.timeStart, TimeUtil.dateFormatOnlyHours))
        println("ALIVE TIME END! "+TimeUtil.parseUnixTimeFormated (item.timeEnd, TimeUtil.dateFormatOnlyHours))
        println("ALIVE TIME END! "+item.roomId)
        println("ALIVE TIME END! "+item.date)



        txtDate.text = item.date
        val myStr = buildString {
            append(TimeUtil.parseUnixTimeFormated (item.timeStart, TimeUtil.dateFormatOnlyHours))
            append (" - ")
            append(TimeUtil.parseUnixTimeFormated (item.timeEnd, TimeUtil.dateFormatOnlyHours))


        }
        txtTime.text = myStr

//        txtRoomName.text = item.name
//        txtChairs.text = item.seats.toString()
//        txtDescription.text = item.description
//
//        txtTime.text = "Ожидание данных..."
//
//        item.nextMeet?.let {
//
//            txtTime.text = it
//        }
//
//        imgBoard.isVisible = item.hasBoard
//        imgProjector.isVisible = item.hasProjector
//
//        itemView.setOnClickListener { v ->
//            onItemClickListener.onItemClick(
//                item
//            )
//        }
    }

    companion object {

        private val FIRST_OWNER_INDEX = 0
    }
}
