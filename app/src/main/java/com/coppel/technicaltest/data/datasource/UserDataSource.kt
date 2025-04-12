package com.coppel.technicaltest.data.datasource

import com.coppel.technicaltest.data.local.database.UserDao
import com.coppel.technicaltest.domain.mapper.toDomain
import com.coppel.technicaltest.domain.mapper.toEntity
import com.coppel.technicaltest.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun insertUser(user: UserModel) {
        userDao.insertUser(user.toEntity())
    }

    fun getUserByUsername(username: String): Flow<UserModel> {
        return userDao.getUserByUsername(username).map { userEntity ->
            userEntity?.toDomain() ?: throw Exception("User not found")
        }
    }

}