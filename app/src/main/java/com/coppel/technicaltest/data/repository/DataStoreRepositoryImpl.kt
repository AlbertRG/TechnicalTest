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

    override suspend fun setBiometricCheck(value: Boolean) {
        dataStore.setBiometricCheck(value)
    }

    override fun getFirstTime(): Flow<Boolean> {
        return dataStore.getFirstTime()
    }

    override suspend fun setFirstTime(value: Boolean) {
        dataStore.setFirstTime(value)
    }

}