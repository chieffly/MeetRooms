package ru.chieffly.meetroom.view.roomlist



import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.toolbar.*
import ru.chieffly.meetroom.view.base.SingleFragmentActivity


class RoomlistActivity : SingleFragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar_actionbar)
    }
    override var fragment: Fragment = RoomlistFragment.newInstance()
}
