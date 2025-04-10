package com.coppel.technicaltest.di

import com.coppel.technicaltest.data.provider.DataStoreManagerImpl
import com.coppel.technicaltest.di.provider.DataStoreManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun provideDataStoreManager(dataStoreManagerImpl: DataStoreManagerImpl): DataStoreManager

}