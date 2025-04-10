package com.coppel.technicaltest.domain.mapper

import com.coppel.technicaltest.data.local.model.FactEntity
import com.coppel.technicaltest.domain.model.FactModel

fun FactModel.toEntity() = FactEntity(
    uid = uid,
    dateInsert = dateInsert,
    slug = slug,
    columns = columns,
    fact = fact,
    organization = organization,
    resource = resource,
    url = url,
    operations = operations,
    dataset = dataset,
    createdAt = createdAt
)

fun FactEntity.toDomain() = FactModel(
    uid = uid,
    dateInsert = dateInsert,
    slug = slug,
    columns = columns,
    fact = fact,
    organization = organization,
    resource = resource,
    url = url,
    operations = operations,
    dataset = dataset,
    createdAt = createdAt
)