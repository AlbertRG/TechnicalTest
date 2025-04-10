package com.coppel.technicaltest.domain.repository

import com.coppel.technicaltest.domain.model.FactModel
import kotlinx.coroutines.flow.Flow

interface FactRepository {

    fun insertFacts(facts: List<FactModel>): Flow<Boolean>
    fun getAllFacts(): Flow<List<FactModel>>

}