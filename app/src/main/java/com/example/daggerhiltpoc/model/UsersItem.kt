package com.example.daggerhiltpoc.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Entity
@TypeConverters(UsersItemConvertor::class)
@Parcelize
data class UsersItem(
    @Embedded
    val address: Address,
    @Embedded
    val company: Company?,
    val email: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
):Parcelable

class UsersItemConvertor {
    @TypeConverter
    fun userItemToString(usersItem: UsersItem): String = GsonBuilder().serializeSpecialFloatingPointValues().create().toJson(usersItem)

    @TypeConverter
    fun stringToUserItem(reportUser: String): UsersItem =
        GsonBuilder().serializeSpecialFloatingPointValues().create().fromJson(reportUser, object : TypeToken<UsersItem>() {}.type)
}