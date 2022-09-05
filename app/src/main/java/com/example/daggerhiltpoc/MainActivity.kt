package com.example.daggerhiltpoc

import android.location.Address
import android.location.Geocoder
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
import com.example.daggerhiltpoc.adapter.OnItemUserAdapterListener
import com.example.daggerhiltpoc.databinding.ActivityMainBinding
import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.util.ResourceUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


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
//        Log.d(TAG, "onCreate: ${Versioning().version()}")
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.resStateFlow.collect{ uiState ->
                    when(uiState){
                        is ResourceUiState.Success -> {
                            val userAdapter = ItemUserAdapter(context =this@MainActivity, lifecycleOwner = this@MainActivity, userList = uiState.users, onItemUserAdapterListener = object : OnItemUserAdapterListener{
                                override fun onItemClicked(usersItem: UsersItem) {
                                    if (BuildConfig.FLAVOR == "internal")
                                        Toast.makeText(this@MainActivity,"internal version",Toast.LENGTH_SHORT).show()
                                    else
                                        Toast.makeText(this@MainActivity,"external version",Toast.LENGTH_SHORT).show()
                                }
                            })
                            binding.activityMainUserItemRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false)
                            binding.activityMainUserItemRecyclerView.adapter = userAdapter
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