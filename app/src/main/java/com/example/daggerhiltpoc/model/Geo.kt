package com.example.daggerhiltpoc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geo(
    val lat: String,
    val lng: String
):Parcelable{
    override fun toString(): String {
        return "$lat : $lng"
    }
}