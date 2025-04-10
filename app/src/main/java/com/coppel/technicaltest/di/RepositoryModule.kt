package com.coppel.technicaltest.di

import com.coppel.technicaltest.data.repository.DataStoreRepositoryImpl
import com.coppel.technicaltest.data.repository.FactRepositoryImpl
import com.coppel.technicaltest.domain.repository.DataStoreRepository
import com.coppel.technicaltest.domain.repository.FactRepository
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
    abstract fun bindFactRepository(factRepositoryImpl: FactRepositoryImpl): FactRepository

    @Singleton
    @Binds
    abstract fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository

}