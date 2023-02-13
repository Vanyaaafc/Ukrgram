package com.example.ukrgram.ui.fragments

import android.widget.TextView
import com.example.ukrgram.R
import com.example.ukrgram.database.USER
import com.example.ukrgram.database.setBioToDatabase
import com.example.ukrgram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        mRootView.findViewById<TextView>(R.id.settings_input_bio).setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        setBioToDatabase(newBio)
    }


}