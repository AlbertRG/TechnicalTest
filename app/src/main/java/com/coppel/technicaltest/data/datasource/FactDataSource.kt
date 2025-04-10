package com.coppel.technicaltest.data.datasource

import com.coppel.technicaltest.data.local.database.FactDao
import com.coppel.technicaltest.domain.mapper.toDomain
import com.coppel.technicaltest.domain.mapper.toEntity
import com.coppel.technicaltest.domain.model.FactModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FactDataSource @Inject constructor(
    private val dao: FactDao
) {

    suspend fun insertFacts(facts: List<FactModel>) {
        dao.insertAll(facts.map { it.toEntity() })
    }

    fun getAllFacts(): Flow<List<FactModel>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

}