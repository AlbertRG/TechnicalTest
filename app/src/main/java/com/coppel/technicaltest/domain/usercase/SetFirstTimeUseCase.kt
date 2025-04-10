package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetFirstTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(value: Boolean) =
        dataStoreRepository.setFirstTime(value)
}