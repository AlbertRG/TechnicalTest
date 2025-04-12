package com.coppel.technicaltest.data.repository

import com.coppel.technicaltest.data.datasource.FactDataSource
import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.domain.repository.FactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factDataSource: FactDataSource
) : FactRepository {

    override suspend fun insertFacts(facts: List<FactModel>): Boolean {
        return runCatching {
            factDataSource.insertFacts(facts)
        }.isSuccess
    }

    override fun getAllFacts(): Flow<List<FactModel>> {
        return factDataSource.getAllFacts()
            .flowOn(Dispatchers.IO)
    }

}