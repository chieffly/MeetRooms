package ru.chieffly.meetroom.view.roomlist

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.view.base.PresenterFragment
import ru.chieffly.meetroom.view.base.RefreshOwner
import ru.chieffly.meetroom.view.base.Refreshable
import ru.chieffly.meetroom.view.details.DetailsActivity
import ru.chieffly.meetroom.view.login.LoginActivity
import ru.chieffly.meetroom.view.roomlist.adapters.RoomlistAdapter


class RoomlistFragment : PresenterFragment(), Refreshable, RoomlistView,
    RoomlistAdapter.OnItemClickListener {
    override fun exitToLoginScreen() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private lateinit var menu: Menu
    private var showFavs = false

    private var recyclerView: RecyclerView? = null
    private var refreshOwner: RefreshOwner? = null
    private var errorView: View? = null
    private var recyclerAdapter: RoomlistAdapter? = null

    @InjectPresenter
    override lateinit var presenter: RoomlistPresenter

    @ProvidePresenter
    internal fun providePresenter(): RoomlistPresenter {
        return RoomlistPresenter()
    }

    companion object {
        fun newInstance(): RoomlistFragment {
            return RoomlistFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RefreshOwner) {
            refreshOwner = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(ru.chieffly.meetroom.R.layout.fragment_roomlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(ru.chieffly.meetroom.R.id.recycler)
        errorView = view.findViewById(ru.chieffly.meetroom.R.id.errorView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerAdapter = RoomlistAdapter(this)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = recyclerAdapter
        presenter.updateRooms()
        onRefreshData()
    }

    override fun onDetach() {
        refreshOwner = null
        super.onDetach()
    }

    override fun onRefreshData() {
        if (showFavs)
            presenter.showFavRoomsFromDB()
        else
            presenter.showRoomsFromDB()
    }

    override fun showRefresh() {
        refreshOwner!!.setRefreshState(true)
    }

    override fun hideRefresh() {
        refreshOwner!!.setRefreshState(false)
    }

    override fun showError() {
        errorView!!.visibility = View.VISIBLE
        recyclerView!!.visibility = View.GONE
    }

    override fun showList(list: List<Meetroom>) {

        errorView!!.visibility = View.GONE
        recyclerView!!.visibility = View.VISIBLE
        recyclerAdapter!!.addData(list, true)
    }

    override fun onItemClick(roomId: Meetroom) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("roomId", roomId)
        startActivity(intent)
    }

    override fun showError(errorCode: Int) {

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
        inflater.inflate(ru.chieffly.meetroom.R.menu.list_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            ru.chieffly.meetroom.R.id.main_menu_logout -> {
                logoutDialog()
                return true
            }
            ru.chieffly.meetroom.R.id.main_menu_favorites -> {
                if (!showFavs) {
                    context?.let {
                        item.icon =
                            ContextCompat.getDrawable(it, ru.chieffly.meetroom.R.drawable.ic_stars)
                    }
                } else
                    context?.let {
                        item.icon = ContextCompat.getDrawable(
                            it,
                            ru.chieffly.meetroom.R.drawable.ic_star_border
                        )
                    }
                showFavs = !showFavs
                onRefreshData()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun logoutDialog() {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(it.getString(ru.chieffly.meetroom.R.string.msg_exit))
            builder.setMessage(it.getString(ru.chieffly.meetroom.R.string.msg_exit_ask))
            builder.setPositiveButton(it.getString(ru.chieffly.meetroom.R.string.exit)) { dialog, which ->

                presenter.logoutRequest()
            }

            builder.setNegativeButton(it.getString(ru.chieffly.meetroom.R.string.cancel)) { dialog, which ->
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}
