package com.example.daggerhiltpoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.daggerhiltpoc.MainActivity.Companion.TAG
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

    @Inject
    @Named("hiltString")
    lateinit var daggerString: String

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: $daggerString")
        viewModel
    }
}

@HiltViewModel
class TestViewModel @Inject constructor(
    @Named("hiltString") string: String,
    private val testViewModelRepository: TestViewModelRepository
):ViewModel(){

    init {
        Log.d(TAG, "hilt string:-> $string ----- ${testViewModelRepository.testString()}")
    }

}

class TestViewModelRepository(){
    fun testString() = "this string is for testing the repository inject via hilt..."
}