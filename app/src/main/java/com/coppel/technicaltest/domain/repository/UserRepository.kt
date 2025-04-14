package com.coppel.technicaltest.domain.repository

import com.coppel.technicaltest.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: UserModel): Boolean
    fun getUserByUsername(username: String): Flow<UserModel>

}