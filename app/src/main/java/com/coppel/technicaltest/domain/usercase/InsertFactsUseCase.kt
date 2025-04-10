package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.domain.repository.FactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFactsUseCase @Inject constructor(
    private val factRepository: FactRepository
) {
    operator fun invoke(facts: List<FactModel>): Flow<Boolean> =
        factRepository.insertFacts(facts)
}