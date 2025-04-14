package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.model.UserModel
import com.coppel.technicaltest.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: UserModel): Boolean =
        userRepository.insertUser(user)
}