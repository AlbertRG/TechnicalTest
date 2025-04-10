package com.coppel.technicaltest.data.repository

import com.coppel.technicaltest.data.datasource.FactDataSource
import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.domain.repository.FactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val dataSource: FactDataSource
) : FactRepository {

    override fun insertFacts(facts: List<FactModel>): Flow<Boolean> = flow {
        runCatching {
            dataSource.insertFacts(facts)
        }.onSuccess {
            emit(true)
        }.onFailure {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllFacts(): Flow<List<FactModel>> = flow {
        runCatching {
            dataSource.getAllFacts()
        }.onSuccess { flow ->
            emitAll(flow)
        }
    }.flowOn(Dispatchers.IO)

}