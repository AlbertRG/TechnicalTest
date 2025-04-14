package com.coppel.technicaltest.data.repository

import com.coppel.technicaltest.data.datasource.UserDataSource
import com.coppel.technicaltest.domain.model.UserModel
import com.coppel.technicaltest.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun insertUser(user: UserModel): Boolean {
        return runCatching {
            userDataSource.insertUser(user)
        }.isSuccess
    }

    override fun getUserByUsername(username: String): Flow<UserModel> {
        return userDataSource.getUserByUsername(username)
            .flowOn(Dispatchers.IO)
    }

}