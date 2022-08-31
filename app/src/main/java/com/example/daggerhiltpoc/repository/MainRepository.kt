package com.example.daggerhiltpoc.repository

import com.example.daggerhiltpoc.api.ApiHelper
import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.room_db.UserDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper,private val userDao: UserDao) {

    suspend fun getUsers() = apiHelper.getUsers()

    suspend fun setUser(usersItem: UsersItem) {
        userDao.insertUser(usersItem)
    }

}