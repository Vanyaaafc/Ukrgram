package com.example.ukrgram.ui.fragments

import android.widget.TextView
import com.example.ukrgram.R
import com.example.ukrgram.utilits.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        mRootView.findViewById<TextView>(R.id.settings_input_bio).setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = mRootView.findViewById<TextView>(R.id.settings_input_bio).text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    USER.bio = newBio
                    fragmentManager?. popBackStack()
                }
            }
    }
}