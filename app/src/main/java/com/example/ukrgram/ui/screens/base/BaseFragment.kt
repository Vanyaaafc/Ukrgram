package com.example.ukrgram.ui.screens.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ukrgram.utilits.APP_ACTIVITY
import java.lang.Exception


open class BaseFragment(private val layuot: Int) : Fragment(layuot) {

    lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mRootView = inflater.inflate(layuot, container, false)
        return mRootView
    }

    override fun onStart() {
        super.onStart()
        try {
            APP_ACTIVITY.mAppDrawer.disableDrawer()
        } catch (e: Exception) {
            return
        }
    }
}
