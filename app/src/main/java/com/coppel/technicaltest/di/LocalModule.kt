package com.coppel.technicaltest.di

import android.content.Context
import androidx.room.Room
import com.coppel.technicaltest.data.local.database.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        DataBase::class.java,
        "facts_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFactDao(dataBase: DataBase) = dataBase.factDao()

}