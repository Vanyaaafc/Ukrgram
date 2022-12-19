package com.example.ukrgram.ui.fragments

import android.view.View
import android.widget.EditText
import com.example.ukrgram.MainActivity
import com.example.ukrgram.R
import com.example.ukrgram.activities.RegisterActivity
import com.example.ukrgram.utilits.AUTH
import com.example.ukrgram.utilits.replaceActivity
import com.example.ukrgram.utilits.replaceFragment
import com.example.ukrgram.utilits.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class EnterPhoneNumberFragment : BaseFragment(R.layout.fragment_enter_phone_number) {

    private lateinit var mPhoneNumber: String
    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onStart() {
        super.onStart()
        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Добро пожаловать")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else {
                        showToast(it.exception?.message.toString())
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }

        }
        mRootView.findViewById<View>(R.id.register_btn_next).setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (mRootView.findViewById<EditText>(R.id.register_input_phone_number).text.isEmpty()) {
            showToast(getString(R.string.register_toast_enter_phone))
        } else {
//            replaceFragment(EnterCodeFragment())
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber =
            mRootView.findViewById<EditText>(R.id.register_input_phone_number).text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            mCallBack
        )
    }
}