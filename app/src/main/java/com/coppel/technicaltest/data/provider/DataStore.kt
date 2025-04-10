package com.coppel.technicaltest.data.provider

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.coppel.technicaltest.utils.PreferencesKeys.BIOMETRIC_CHECK
import com.coppel.technicaltest.utils.PreferencesKeys.FIRST_TIME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "TECHNICAL_DATASTORE")

class DataStore @Inject constructor(
    @ApplicationContext context: Context
) {

    private val dataStore = context.dataStore

    suspend fun setBiometricCheck(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[BIOMETRIC_CHECK] = value
        }
    }

    fun getBiometricCheck(): Flow<Boolean> = dataStore.data.map {
        it[BIOMETRIC_CHECK] ?: false
    }.flowOn(Dispatchers.IO)

    suspend fun setFirstTime(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[FIRST_TIME] = value
        }
    }

    fun getFirstTime(): Flow<Boolean> = dataStore.data.map {
        it[FIRST_TIME] ?: false
    }.flowOn(Dispatchers.IO)

}