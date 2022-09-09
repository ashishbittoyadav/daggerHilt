package com.example.dynamicfeature

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class DynamicFeature : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity.TAG", "onCreate: ${dynamicLibFun()}")
    }

    private fun dynamicLibFun() = "dynamic library installed..."
}