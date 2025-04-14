package com.coppel.technicaltest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    val latitude: Double,
    val longitude: Double
)
