package com.coppel.technicaltest.data.repository

import com.coppel.technicaltest.data.datasource.NetworkDataSource
import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : NetworkRepository {

    override suspend fun getAllFacts(): List<FactModel> {
        return networkDataSource.getAllFacts()
    }

}