package com.coppel.technicaltest.data.repository

import com.coppel.technicaltest.di.provider.DataStoreManager
import com.coppel.technicaltest.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager
) : DataStoreRepository {

    override fun getBiometricCheck(): Flow<Boolean> {
        return dataStore.getBiometricCheck()
    }

    override suspend fun setBiometricCheck(value: Boolean): Boolean {
        return runCatching {
            dataStore.setBiometricCheck(value)
        }.isSuccess
    }

}