package com.coppel.technicaltest.data.remote.response

import com.google.gson.annotations.SerializedName

data class FactResponse(
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("results")
    val results: List<FactEntity>
)

data class Pagination(
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total")
    val total: Int
)

data class FactEntity(
    @SerializedName("_id")
    val uid: String,
    @SerializedName("date_insert")
    val dateInsert: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("columns")
    val columns: String,
    @SerializedName("fact")
    val fact: String,
    @SerializedName("organization")
    val organization: String,
    @SerializedName("resource")
    val resource: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("operations")
    val operations: String,
    @SerializedName("dataset")
    val dataset: String,
    @SerializedName("created_at")
    val createdAt: Long
)