package com.coppel.technicaltest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coppel.technicaltest.data.local.model.FactEntity
import com.coppel.technicaltest.data.local.model.UserEntity

@Database(
    entities = [FactEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun factDao(): FactDao
    abstract fun userDao(): UserDao
}