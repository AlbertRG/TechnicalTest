package com.coppel.technicaltest.data.datasource

import com.coppel.technicaltest.data.remote.api.ApiService
import com.coppel.technicaltest.domain.mapper.toDomain
import com.coppel.technicaltest.domain.model.FactModel
import java.io.IOException
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllFacts(): List<FactModel> {

        val response = apiService.getAllFacts()

        if (response.isSuccessful) {
            return response.body()?.results?.map { fact ->
                fact.toDomain() } ?: emptyList()
        } else {
            throw IOException("Error retrieving data: ${response.code()} ${response.message()}")
        }
    }

}