package com.example.daggerhiltpoc.base

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application(){

    private val TAG = "ApplicationClass.TAG"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate:")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        SplitCompat.install(this)
    }
}