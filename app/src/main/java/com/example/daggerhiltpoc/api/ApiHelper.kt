package com.example.daggerhiltpoc.api

import com.example.daggerhiltpoc.model.Users
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers():Response<Users>
}