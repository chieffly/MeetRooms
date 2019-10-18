package ru.chieffly.meetroom.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import ru.chieffly.meetroom.R

abstract class SingleFragmentActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    RefreshOwner {

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    protected abstract val fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_container)
        mSwipeRefreshLayout = findViewById(R.id.refresher)
        mSwipeRefreshLayout!!.setOnRefreshListener(this)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorAction);
        if (savedInstanceState == null) {
            changeFragment(fragment)
        }

    }

    fun changeFragment(fragment: Fragment) {
        val addToBackStack = supportFragmentManager.findFragmentById(R.id.fragmentContainer) != null

        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }

        transaction.commit()
    }

    override fun onRefresh() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is Refreshable) {
            (fragment as Refreshable).onRefreshData()
        } else {
            setRefreshState(false)
        }
    }

    override fun setRefreshState(refreshing: Boolean) {
        mSwipeRefreshLayout!!.post { mSwipeRefreshLayout!!.isRefreshing = refreshing }
    }
}
