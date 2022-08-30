package com.example.daggerhiltpoc.api

import com.example.daggerhiltpoc.UserResponse
import com.example.daggerhiltpoc.model.Users
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService):ApiHelper {

    override suspend fun getUsers(): Response<Users> = apiService.getUsers()

}