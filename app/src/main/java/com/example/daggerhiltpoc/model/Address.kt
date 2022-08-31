package com.example.daggerhiltpoc.model

import androidx.room.Embedded

data class Address(
    val city: String,
    @Embedded
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)