package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.repository.FactRepository
import javax.inject.Inject

class GetFactsUseCase @Inject constructor(
    private val factRepository: FactRepository
) {

    operator fun invoke() =
        factRepository.getAllFacts()

}