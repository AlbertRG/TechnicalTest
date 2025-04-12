package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    operator fun invoke(username: String) =
        userRepository.getUserByUsername(username)

}