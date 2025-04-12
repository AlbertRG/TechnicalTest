package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.domain.repository.FactRepository
import javax.inject.Inject

class InsertFactsLocalUseCase @Inject constructor(
    private val factRepository: FactRepository
) {
    suspend operator fun invoke(facts: List<FactModel>): Boolean =
        factRepository.insertFacts(facts)
}