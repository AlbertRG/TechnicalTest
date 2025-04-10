package com.coppel.technicaltest.di.provider

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    suspend fun setBiometricCheck(value: Boolean)
    fun getBiometricCheck(): Flow<Boolean>

    suspend fun setFirstTime(value: Boolean)
    fun getFirstTime(): Flow<Boolean>

}