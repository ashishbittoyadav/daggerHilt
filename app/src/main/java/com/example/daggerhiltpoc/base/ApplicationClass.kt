package com.example.daggerhiltpoc.base

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: SplitCompatApplication(){

    private val TAG = "ApplicationClass.TAG"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate:")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}