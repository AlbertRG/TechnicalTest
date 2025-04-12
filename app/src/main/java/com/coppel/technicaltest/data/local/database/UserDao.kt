package com.coppel.technicaltest.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coppel.technicaltest.data.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE user = :username LIMIT 1")
    fun getUserByUsername(username: String): Flow<UserEntity?>

}