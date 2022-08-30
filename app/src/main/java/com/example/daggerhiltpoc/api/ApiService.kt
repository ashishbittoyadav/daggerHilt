package com.example.daggerhiltpoc.api

import com.example.daggerhiltpoc.UserResponse
import com.example.daggerhiltpoc.model.Users
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers():Response<Users>
}