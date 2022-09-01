package com.example.daggerhiltpoc.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.daggerhiltpoc.model.UsersItem

@Dao
interface UserDao{

    @Query("Select * from UsersItem")
    fun getUsers():List<UsersItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(usersItem: UsersItem)

}