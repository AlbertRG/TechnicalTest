package com.coppel.technicaltest.data.remote.api

import com.coppel.technicaltest.data.remote.response.FactResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("gobmx.facts")
    suspend fun getAllFacts(): Response<FactResponse>

}