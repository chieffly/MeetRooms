package ru.chieffly.meetroom.view.details

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.txtSeats
import kotlinx.android.synthetic.main.item_room.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.chieffly.meetroom.R
import ru.chieffly.meetroom.model.rooms.Meet
import ru.chieffly.meetroom.model.rooms.Meetroom
import ru.chieffly.meetroom.view.base.PresenterActivity
import ru.chieffly.meetroom.view.base.RefreshOwner
import ru.chieffly.meetroom.view.reserve.ReserveActivity
import ru.chieffly.meetroom.view.roomlist.adapters.DetailsAdapter


class DetailsActivity : PresenterActivity(), DetailsView, DetailsAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: DetailsAdapter
    private lateinit var currentRoom: Meetroom

    @InjectPresenter
    override lateinit var presenter: DetailsPresenter

    @ProvidePresenter
    fun provideLoginPresenter(): DetailsPresenter {
        return DetailsPresenter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        val item = menu.findItem(R.id.main_add_fav)
        if (currentRoom.isFavorite) {
            item.icon =
                ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite)
        } else
            item.icon =
                ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_border)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        currentRoom = intent.getSerializableExtra("roomId") as Meetroom
        initListeners()
        initViews()
        presenter.getMeets(currentRoom.id)
    }

    private fun initViews() {
        txt_projetor.isVisible = currentRoom.hasProjector
        env_board.isVisible = currentRoom.hasBoard
        txt_description.text = currentRoom.description
        txtSeats.text =
            getString(ru.chieffly.meetroom.R.string.str_seats, currentRoom.seats.toString())

        recyclerView = recycle_meets

        recyclerAdapter = DetailsAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
        toolbar_actionbar.title = currentRoom.name

        setSupportActionBar(toolbar_actionbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        toolbar_actionbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun initListeners() {
        btnReserve.setOnClickListener {
            val intent = Intent(this, ReserveActivity::class.java)
            intent.putExtra("roomId", currentRoom)
            startActivity(intent)
        }
    }

    override fun onRefreshData() {
        presenter.getMeets(currentRoom.id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.main_add_fav -> {
                currentRoom.isFavorite = !currentRoom.isFavorite

                presenter.updateDB(currentRoom)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (currentRoom.isFavorite) {
                            item.icon =
                                ContextCompat.getDrawable(
                                    this, R.drawable.ic_favorite)
                        } else
                            item.icon =
                                ContextCompat.getDrawable(
                                    this, R.drawable.ic_favorite_border)
                    }


                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showError(errorCode: Int) {
    }

    override fun showRefresh() {

    }

    override fun showList(list: List<Meet>) {
        recyclerView!!.visibility = View.VISIBLE
        recyclerAdapter!!.addData(list, true)
    }



    override fun hideRefresh() {

    }

    override fun onItemClick(id: Meet) {
    }
}