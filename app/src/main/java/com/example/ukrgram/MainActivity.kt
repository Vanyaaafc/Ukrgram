package com.example.ukrgram

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.ukrgram.database.AUTH
import com.example.ukrgram.database.initFirebase
import com.example.ukrgram.database.initUser
import com.example.ukrgram.databinding.ActivityMainBinding
import com.example.ukrgram.livedata.MyLiveData
import com.example.ukrgram.ui.objects.AppDrawer
import com.example.ukrgram.ui.screens.main_list.MainListFragment
import com.example.ukrgram.ui.screens.register.EnterPhoneNumberFragment
import com.example.ukrgram.utilits.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    lateinit var mToolbar: Toolbar
    private val myLiveData = MyLiveData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser {
            CoroutineScope(Dispatchers.IO).launch {
                initContacts()
            }
            initFields()
            initFunc()

            myLiveData.observe(this, Observer {

            })
        }
    }

    override fun onStart() {
        super.onStart()
        myLiveData.observe(this, Observer {

        })
    }

    override fun onStop() {
        super.onStop()
        myLiveData.removeObserver(Observer {

        })
    }


    private fun initFunc() {
        setSupportActionBar(mToolbar)
        if (AUTH.currentUser != null) {
            mAppDrawer.create()
            replaceFragment(MainListFragment(), false)
        } else {
            replaceFragment(EnterPhoneNumberFragment(), false)

        }
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolBar
        mAppDrawer = AppDrawer()
        AUTH = FirebaseAuth.getInstance()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initContacts()
        }
    }
}