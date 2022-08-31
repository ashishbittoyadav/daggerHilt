package com.example.daggerhiltpoc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerhiltpoc.model.Users
import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.repository.MainRepository
import com.example.daggerhiltpoc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel() {

    private val _res = MutableLiveData<Resource<Users>>()

    val res : LiveData<Resource<Users>>
        get() = _res

    init {
        getEmployees()
    }

    private fun getEmployees()  = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepository.getUsers().let { userItem ->
            if (userItem.isSuccessful){
//                userItem.body().let { user ->
//                    user?.forEach {
//                        mainRepository.setUser(it)
//                    }
//                }
                _res.postValue(Resource.success(userItem.body()))
            }else{
                _res.postValue(Resource.error(userItem.errorBody().toString(), null))
            }
        }
    }


    suspend fun saveUserInDB(usersItem: UsersItem){
        mainRepository.setUser(usersItem)
    }



}