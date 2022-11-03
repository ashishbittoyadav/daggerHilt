package com.example.daggerhiltpoc

//import com.ashish.versioning.Versioning

import android.os.Bundle
import android.os.Environment
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
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.example.daggerhiltpoc.adapter.ItemUserAdapter
import com.example.daggerhiltpoc.adapter.OnItemUserAdapterListener
import com.example.daggerhiltpoc.databinding.ActivityMainBinding
import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.util.ResourceUiState
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.full.primaryConstructor


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "MainActivity.TAG"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var userList: ArrayList<UsersItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        viewModel.getUserFromRepo()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resStateFlow.collect { uiState ->
                    when (uiState) {
                        is ResourceUiState.Success -> {
                            val userAdapter = ItemUserAdapter(
                                context = this@MainActivity,
                                lifecycleOwner = this@MainActivity,
                                userList = uiState.users,
                                onItemUserAdapterListener = object : OnItemUserAdapterListener {
                                    override fun onItemClicked(usersItem: UsersItem) {
                                        Toast.makeText(this@MainActivity,usersItem.name,Toast.LENGTH_SHORT).show()
                                    }
                                })
                            binding.activityMainUserItemRecyclerView.layoutManager =
                                LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
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