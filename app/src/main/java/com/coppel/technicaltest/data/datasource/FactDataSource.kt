package com.coppel.technicaltest.data.datasource

import android.util.Log
import com.coppel.technicaltest.data.local.database.FactDao
import com.coppel.technicaltest.domain.mapper.toDomain
import com.coppel.technicaltest.domain.mapper.toEntity
import com.coppel.technicaltest.domain.model.FactModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FactDataSource @Inject constructor(
    private val factDao: FactDao
) {

    suspend fun insertFacts(facts: List<FactModel>) {
        try {
            Log.d("FactDataSource", "Inserting facts")
            factDao.insertAll(facts.map { it.toEntity() })
        } catch (e: Exception) {
            Log.e("FactDataSource", "Error inserting facts", e)
        }
    }

    fun getAllFacts(): Flow<List<FactModel>> {
        return factDao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }

}