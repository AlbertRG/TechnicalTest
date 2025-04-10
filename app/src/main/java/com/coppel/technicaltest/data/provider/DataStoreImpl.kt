package com.coppel.technicaltest.data.provider

import com.coppel.technicaltest.di.provider.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore
) : DataStoreManager {

    override suspend fun setBiometricCheck(value: Boolean) {
        dataStore.setBiometricCheck(value)
    }

    override fun getBiometricCheck(): Flow<Boolean> =
        dataStore.getBiometricCheck()

    override suspend fun setFirstTime(value: Boolean) {
        dataStore.setFirstTime(value)
    }

    override fun getFirstTime(): Flow<Boolean> =
        dataStore.getFirstTime()

}