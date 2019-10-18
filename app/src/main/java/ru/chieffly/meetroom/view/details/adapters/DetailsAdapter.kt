package ru.chieffly.meetroom.view.roomlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.model.rooms.Meet
import ru.chieffly.meetroom.view.details.adapters.DetailsHolder
import java.util.*


class DetailsAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<DetailsHolder>() {

    private val mmetroomList = ArrayList<Meet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_meet, parent, false)
        return DetailsHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsHolder, position: Int) {
        val project = mmetroomList[position]
        holder.bind(project, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return mmetroomList.size
    }

    fun addData(data: List<Meet>, isRefreshed: Boolean) {
        if (isRefreshed) {
            mmetroomList.clear()
        }

        // TODO: обработать кейс с data.size == 0 || data == null

        mmetroomList.addAll(data)


        notifyDataSetChanged()
    }

    interface OnItemClickListener {

        fun onItemClick(id: Meet)

    }
}
