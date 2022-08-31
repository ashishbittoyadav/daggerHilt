package com.example.daggerhiltpoc.repository

import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.room_db.UserDao
import javax.inject.Inject

class UserItemRepository @Inject constructor(private val userDao: UserDao){

    fun setUser(usersItem: UsersItem){
        userDao.insertUser(usersItem)
    }

    fun getUsers() = userDao.getUsers()

}