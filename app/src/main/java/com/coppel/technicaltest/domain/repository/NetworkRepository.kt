package com.coppel.technicaltest.domain.repository

import com.coppel.technicaltest.domain.model.FactModel

interface NetworkRepository {

    suspend fun getAllFacts(): List<FactModel>

}