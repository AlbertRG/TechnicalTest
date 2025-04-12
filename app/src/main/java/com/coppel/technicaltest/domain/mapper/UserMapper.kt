package com.coppel.technicaltest.domain.mapper

import com.coppel.technicaltest.data.local.model.UserEntity
import com.coppel.technicaltest.domain.model.UserModel

fun UserEntity.toDomain(): UserModel {
    return UserModel(
        uid = this.uid,
        user = this.user,
        password = this.password
    )
}

fun UserModel.toEntity(): UserEntity {
    return UserEntity(
        uid = this.uid,
        user = this.user,
        password = this.password
    )
}