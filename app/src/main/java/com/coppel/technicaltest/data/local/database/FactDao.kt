package com.coppel.technicaltest.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.technicaltest.data.local.model.FactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(results: List<FactEntity>)

    @Query("SELECT * FROM facts")
    fun getAll(): Flow<List<FactEntity>>

}