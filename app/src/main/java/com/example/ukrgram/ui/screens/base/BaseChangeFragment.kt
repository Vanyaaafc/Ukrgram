package com.example.ukrgram.ui.screens.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.ukrgram.R
import com.example.ukrgram.utilits.APP_ACTIVITY
import com.example.ukrgram.utilits.hideKeyboard

open class BaseChangeFragment(val layout: Int) : Fragment(layout) {

    lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mRootView = inflater.inflate(layout, container, false)
        return mRootView
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        APP_ACTIVITY.mAppDrawer.disableDrawer()
        hideKeyboard()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        APP_ACTIVITY.menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }
}
