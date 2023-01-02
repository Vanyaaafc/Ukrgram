package com.example.ukrgram.ui.fragments

import android.widget.EditText
import android.widget.Toast
import com.example.ukrgram.MainActivity
import com.example.ukrgram.R
import com.example.ukrgram.activities.RegisterActivity
import com.example.ukrgram.utilits.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class EnterCodeFragment(val phoneNumber: String, val id: String) :
    BaseFragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        mRootView.findViewById<EditText>(R.id.register_input_code)
            .addTextChangedListener(AppTextWatcher {
                val string =
                    mRootView.findViewById<EditText>(R.id.register_input_code).text.toString()
                if (string.length == 6) {
                    enterCode()
                }
            })
    }

    private fun enterCode() {
        val code: String =
            mRootView.findViewById<EditText>(R.id.register_input_code).text.toString()
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                    .addOnFailureListener { showToast(it.message.toString()) }
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                            .addOnSuccessListener {
                                showToast("Добро пожаловать")
                                (activity as RegisterActivity).replaceActivity(MainActivity())
                            }
                            .addOnFailureListener { showToast(it.message.toString()) }
                    }

            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }
}