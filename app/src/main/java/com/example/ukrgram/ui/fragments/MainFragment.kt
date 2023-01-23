package com.example.ukrgram.ui.fragments

import androidx.fragment.app.Fragment
import com.example.ukrgram.R
import com.example.ukrgram.utilits.APP_ACTIVITY
import com.example.ukrgram.utilits.hideKeyboard


class MainFragment : Fragment(R.layout.fragment_chats) {


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Ukrgram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
    }
}