package com.example.ukrgram.ui.screens.settings

import com.example.ukrgram.R
import com.example.ukrgram.database.*
import com.example.ukrgram.ui.screens.base.BaseChangeFragment
import com.example.ukrgram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }


    override fun change() {
        mNewUsername = settings_input_username.text.toString().lowercase(Locale.getDefault())
        if (mNewUsername.isEmpty()) {
            showToast(getString(R.string.field_empty))
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) {
                        showToast(getString(R.string.user_already_taken))
                    } else {
                        changeUsername()
                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername(mNewUsername)
                    hideKeyboard()
                }
            }
    }
}