package com.example.daggerhiltpoc

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerhiltpoc.adapter.ItemUserAdapter
import com.example.daggerhiltpoc.databinding.ActivityMainBinding
import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.util.ResourceUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = "MainActivity.TAG"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var userList: ArrayList<UsersItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        viewModel.getUserFromRepo()

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.resStateFlow.collect{ uiState ->
                    when(uiState){
                        is ResourceUiState.Success -> {
                            val userAdapter = ItemUserAdapter(this@MainActivity,this@MainActivity,uiState.users)
                            binding.activityMainUserItemRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false)
                            binding.activityMainUserItemRecyclerView.adapter = userAdapter
                            uiState.users.forEach {
                                Log.d(TAG, "onCreate: ${it.name}")
                            }
                        }
                        is ResourceUiState.Failed -> {
                            Log.d(TAG, "onCreate: ${uiState.errorMsg}")
                        }
                        else -> {
                            Log.d(TAG, "onCreate: ")
                        }
                    }
                }
            }
        }
    }
}