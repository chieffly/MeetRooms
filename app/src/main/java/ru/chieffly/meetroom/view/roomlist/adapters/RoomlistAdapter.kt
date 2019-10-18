package ru.chieffly.meetroom.view.roomlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.model.rooms.Meetroom
import java.util.*


class RoomlistAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RoomlistHolder>() {

    private val mmetroomList = ArrayList<Meetroom>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomlistHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_room, parent, false)
        return RoomlistHolder(view)
    }

    override fun onBindViewHolder(holder: RoomlistHolder, position: Int) {
        val project = mmetroomList[position]
        holder.bind(project, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return mmetroomList.size
    }

    fun addData(data: List<Meetroom>, isRefreshed: Boolean) {
        if (isRefreshed) {
            mmetroomList.clear()
        }

        // TODO: обработать кейс с data.size == 0 || data == null

        mmetroomList.addAll(data)


        notifyDataSetChanged()
    }

    interface OnItemClickListener {

        fun onItemClick(id: Meetroom)

    }
}
