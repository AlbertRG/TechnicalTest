package com.coppel.technicaltest.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getBiometricCheck(): Flow<Boolean>
    suspend fun setBiometricCheck(value: Boolean): Boolean

}