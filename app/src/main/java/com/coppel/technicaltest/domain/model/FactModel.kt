package com.coppel.technicaltest.domain.model

data class FactModel(
    val uid: String,
    val dateInsert: String,
    val slug: String,
    val columns: String,
    val fact: String,
    val organization: String,
    val resource: String,
    val url: String,
    val operations: String,
    val dataset: String,
    val createdAt: Long
)