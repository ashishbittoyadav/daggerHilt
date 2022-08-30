package com.example.daggerhiltpoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.daggerhiltpoc.MainActivity.Companion.TAG
import com.example.daggerhiltpoc.util.Status
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = "MainActivity.TAG"
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.output_text_view)
        viewModel.res.observe(this){
            runOnUiThread {
                if(it.status == Status.SUCCESS){
                    textView.text = it.data!![0].email
                }else if(it.status == Status.ERROR){
                    textView.text = it.message
                }else{
                    textView.text = "loading..."
                }
            }
        }
    }
}