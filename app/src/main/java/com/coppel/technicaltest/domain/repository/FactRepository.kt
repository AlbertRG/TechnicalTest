package com.coppel.technicaltest.domain.repository

import com.coppel.technicaltest.domain.model.FactModel
import kotlinx.coroutines.flow.Flow

interface FactRepository {

    suspend fun insertFacts(facts: List<FactModel>): Boolean
    fun getAllFacts(): Flow<List<FactModel>>

}