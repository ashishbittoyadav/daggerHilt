package com.example.daggerhiltpoc

import `in`.sunfox.healthcare.commons.android.spandan_sdk.SpandanSDK
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = "MainActivity.TAG"
    }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getUserFromRepo()

        viewModel.res.observe(this){ response ->
            CoroutineScope(IO).launch {
                withContext(Dispatchers.Default) {
                    response.data?.forEach {
                        Log.d(TAG, "onCreate: ${it.name}")
                    }
                }
            }
        }
    }
}