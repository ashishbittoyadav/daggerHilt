package com.example.daggerhiltpoc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val bs: String,
    val catchPhrase: String,
    val companyName: String?
):Parcelable