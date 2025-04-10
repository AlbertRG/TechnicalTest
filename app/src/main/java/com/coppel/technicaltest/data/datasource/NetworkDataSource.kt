package com.coppel.technicaltest.data.datasource

import com.coppel.technicaltest.data.remote.api.ApiService
import com.coppel.technicaltest.domain.mapper.toDomain
import com.coppel.technicaltest.domain.model.FactModel
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllFacts(): List<FactModel> {
        val response = apiService.getAllFacts()

        if (response.isSuccessful) {
            return response.body()?.results?.map { it.toDomain() } ?: emptyList()
        } else {
            throw Exception("Error al obtener los datos")
        }
    }
}