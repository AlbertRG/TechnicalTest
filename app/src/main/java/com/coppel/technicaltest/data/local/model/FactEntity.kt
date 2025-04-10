package com.coppel.technicaltest.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class FactEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val uid: String,
    @ColumnInfo(name = "date_insert") val dateInsert: String,
    @ColumnInfo(name = "slug") val slug: String,
    @ColumnInfo(name = "columns") val columns: String,
    @ColumnInfo(name = "fact") val fact: String,
    @ColumnInfo(name = "organization") val organization: String,
    @ColumnInfo(name = "resource") val resource: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "operations") val operations: String,
    @ColumnInfo(name = "dataset") val dataset: String,
    @ColumnInfo(name = "created_at")val createdAt: Long
)