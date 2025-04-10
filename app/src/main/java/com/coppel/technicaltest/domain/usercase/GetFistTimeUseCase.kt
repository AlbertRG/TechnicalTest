package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> =
        dataStoreRepository.getFirstTime()
}