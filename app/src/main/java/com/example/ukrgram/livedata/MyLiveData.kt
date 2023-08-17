package com.example.ukrgram.livedata

import androidx.lifecycle.LiveData
import com.example.ukrgram.utilits.AppStates

class MyLiveData : LiveData<String>() {

    override fun onActive() {
        super.onActive()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onInactive() {
        super.onInactive()
        AppStates.updateState(AppStates.OFFLINE)
    }
}