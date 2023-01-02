package com.example.ukrgram.ui.fragments

import android.widget.EditText
import com.example.ukrgram.R
import com.example.ukrgram.utilits.*


class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {


    override fun onResume() {
        super.onResume()
        initFullnameList()
    }

    private fun initFullnameList() {
        val fullnameList = USER.fullname.split(" ")
        if (fullnameList.size > 1) {
            mRootView.findViewById<EditText>(R.id.settings_input_name).setText(fullnameList[0])
            mRootView.findViewById<EditText>(R.id.settings_input_surname).setText(fullnameList[1])
        } else {
            mRootView.findViewById<EditText>(R.id.settings_input_name).setText(fullnameList[0])
        }
    }


    override fun change() {
        val name = mRootView.findViewById<EditText>(R.id.settings_input_name).text.toString()
        val surname = mRootView.findViewById<EditText>(R.id.settings_input_surname).text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.fullname = fullname
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                        fragmentManager?.popBackStack()
                        hideKeyboard()
                    }
                }
        }
    }
}