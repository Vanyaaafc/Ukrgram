package com.example.ukrgram.ui.screens

import android.widget.TextView
import com.example.ukrgram.R
import com.example.ukrgram.database.USER
import com.example.ukrgram.database.setBioToDatabase
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        mRootView.findViewById<TextView>(R.id.settings_input_bio).text = USER.bio
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        setBioToDatabase(newBio)
    }


}