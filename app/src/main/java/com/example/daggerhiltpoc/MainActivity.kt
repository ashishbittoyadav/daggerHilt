package com.example.daggerhiltpoc

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerhiltpoc.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
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
        val textView = findViewById<TextView>(R.id.output_text_view)
        viewModel.res.observe(this){ response ->
            CoroutineScope(IO).launch {
                withContext(Dispatchers.Default) {
                    response.data?.forEach {
                        viewModel.saveUserInDB(it)
                    }
                }
            }
            runOnUiThread {
                when (response.status) {
                    Status.SUCCESS -> {
                        textView.text = response.data!![0].email
                    }
                    Status.ERROR -> {
                        textView.text = response.message
                    }
                    else -> {
                        textView.text = getString(R.string.loading_message)
                    }
                }
            }
        }
    }
}