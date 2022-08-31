package com.example.daggerhiltpoc.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.daggerhiltpoc.model.UsersItem

@Database(entities = [UsersItem::class], version = 2)
abstract class AppDataBase:RoomDatabase() {
    abstract fun userDao(): UserDao
}