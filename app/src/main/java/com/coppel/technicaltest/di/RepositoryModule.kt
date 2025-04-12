package com.coppel.technicaltest.di

import com.coppel.technicaltest.data.repository.DataStoreRepositoryImpl
import com.coppel.technicaltest.data.repository.FactRepositoryImpl
import com.coppel.technicaltest.data.repository.NetworkRepositoryImpl
import com.coppel.technicaltest.data.repository.UserRepositoryImpl
import com.coppel.technicaltest.domain.repository.DataStoreRepository
import com.coppel.technicaltest.domain.repository.FactRepository
import com.coppel.technicaltest.domain.repository.NetworkRepository
import com.coppel.technicaltest.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository

    @Singleton
    @Binds
    abstract fun bindFactRepository(factRepositoryImpl: FactRepositoryImpl): FactRepository

    @Singleton
    @Binds
    abstract fun bindNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository

}