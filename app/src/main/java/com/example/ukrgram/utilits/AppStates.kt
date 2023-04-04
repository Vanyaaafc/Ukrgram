package com.example.ukrgram.utilits

import com.example.ukrgram.R
import com.example.ukrgram.database.*
import com.example.ukrgram.ui.screens.base.BaseFragment
import kotlinx.android.synthetic.main.contact_item.*

enum class AppStates(val state: String) {
    ONLINE(APP_ACTIVITY.getString(R.string.online_state_string)),
    OFFLINE(APP_ACTIVITY.getString(R.string.offline_state_string)),
    TYPING("печатает");


    companion object {
        fun updateState(appStates: AppStates) {
            if (AUTH.currentUser != null) {
                REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATE)
                    .setValue(appStates.state)
                    .addOnSuccessListener { USER.state = appStates.state }
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
        }
    }
}