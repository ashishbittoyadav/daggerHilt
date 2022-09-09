package com.example.daggerhiltpoc

//import com.ashish.versioning.Versioning

import android.content.Intent
import android.net.Uri
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
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.primaryConstructor


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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        val number = "919720547422"
        installDynamicFeatureModule("dynamicfeature")
        viewModel.getUserFromRepo()
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.resStateFlow.collect{ uiState ->
                    when(uiState){
                        is ResourceUiState.Success -> {
                            val userAdapter = ItemUserAdapter(context =this@MainActivity, lifecycleOwner = this@MainActivity, userList = uiState.users, onItemUserAdapterListener = object : OnItemUserAdapterListener{
                                override fun onItemClicked(usersItem: UsersItem) {
                                    callMethodViaReflection("com.example.dynamicfeature.NonActivityClass","randomPrint")
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

    private fun installDynamicFeatureModule(moduleName:String) {
        var mySessionId = 0
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val request = SplitInstallRequest
            .newBuilder()
            .addModule(moduleName)
            .build()
        val listener =
            SplitInstallStateUpdatedListener { splitInstallSessionState ->
                if (splitInstallSessionState.sessionId() == mySessionId) {
                    when (splitInstallSessionState.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            Log.d(TAG, "Dynamic Module downloaded ")
                            Toast.makeText(
                                this@MainActivity,
                                "Dynamic Module downloaded",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        SplitInstallSessionStatus.CANCELED -> {

                        }
                        SplitInstallSessionStatus.CANCELING -> {

                        }
                        SplitInstallSessionStatus.DOWNLOADED -> {

                        }
                        SplitInstallSessionStatus.DOWNLOADING -> {

                        }
                        SplitInstallSessionStatus.FAILED -> {

                        }
                        SplitInstallSessionStatus.INSTALLING -> {

                        }
                        SplitInstallSessionStatus.PENDING -> {

                        }
                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {

                        }
                        SplitInstallSessionStatus.UNKNOWN -> {

                        }
                    }
                }
            }

        splitInstallManager.registerListener(listener)

        splitInstallManager.startInstall(request)
            .addOnFailureListener { e -> Log.d(TAG, "Exception: $e") }
            .addOnSuccessListener { sessionId -> mySessionId = sessionId }
    }

    private fun callMethodViaReflection(fullyQualifiedMethodName:String,methodName:String){
        val className = Class.forName(fullyQualifiedMethodName).kotlin
        val method = className.members.find {
            (it.name == methodName)
        }
        val obj = className.primaryConstructor?.call()
        method?.call(obj)
        Log.d(TAG, "onItemClicked: ${method?.call(obj)}")
    }
}