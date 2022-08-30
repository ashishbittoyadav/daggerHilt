package com.example.daggerhiltpoc

import com.example.daggerhiltpoc.model.Users

data class UserResponse(
    val users: Users?=null,
    val status: String?=""
)