package com.example.daggerhiltpoc.model

data class Geo(
    val lat: String,
    val lng: String
){
    override fun toString(): String {
        return "$lat : $lng"
    }
}