package com.example.daggerhiltpoc.repository

import com.example.daggerhiltpoc.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}