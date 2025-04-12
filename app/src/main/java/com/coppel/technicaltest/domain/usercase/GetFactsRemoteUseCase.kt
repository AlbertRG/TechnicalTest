package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.repository.NetworkRepository
import javax.inject.Inject

class GetFactsRemoteUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
){

    suspend operator fun invoke() =
        networkRepository.getAllFacts()

}