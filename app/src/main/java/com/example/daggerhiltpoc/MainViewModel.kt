package com.example.daggerhiltpoc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerhiltpoc.model.Users
import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.repository.MainRepository
import com.example.daggerhiltpoc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel() {

    private val TAG = "MainViewModel.TAG"

    private val _res = MutableLiveData<Resource<Users>>()

    val res : LiveData<Resource<Users>>
        get() = _res

    private fun getUsersFromServer()  = viewModelScope.launch {
        Log.d(TAG, "getUsersFromServer: ")
        _res.postValue(Resource.loading(null))
        mainRepository.getUsers().let { userItem ->
            if (userItem.isSuccessful){
                userItem.body()?.forEach {
                    this.launch(IO) {
                        saveUserInDB(it)
                    }
                }
                _res.postValue(Resource.success(userItem.body()))
            }else{
                _res.postValue(Resource.error(userItem.errorBody().toString(), null))
            }
        }
    }


    private suspend fun saveUserInDB(usersItem: UsersItem){
        mainRepository.setUser(usersItem)
    }


    fun getUserFromRepo(){
        CoroutineScope(Main).launch {
            _res.postValue(Resource.loading(null))
            this.launch(Dispatchers.IO) {
                mainRepository.getUsersFromDB().let { userList ->
                    Log.d(TAG, "getUserFromRepo: ${userList.size}")
                    val users = Users()
                    users.addAll(userList)
                    if(userList.isNotEmpty())
                        this.launch(Main) {
                            _res.postValue(Resource.success(users))
                        }
                    else
                        getUsersFromServer()
                }
            }
        }
    }
}