package com.coppel.technicaltest.domain.usercase

import com.coppel.technicaltest.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetBiometricCheckUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(value: Boolean): Boolean =
        dataStoreRepository.setBiometricCheck(value)

}