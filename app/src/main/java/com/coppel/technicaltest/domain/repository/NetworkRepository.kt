package com.coppel.technicaltest.domain.repository

interface NetworkRepository {

    suspend fun getAllFacts(): List<Unit>

}