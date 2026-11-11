package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomBuildDao {
    @Query("SELECT * FROM custom_builds ORDER BY createdAt DESC")
    fun getAllBuilds(): Flow<List<CustomBuildEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuild(build: CustomBuildEntity): Long

    @Delete
    suspend fun deleteBuild(build: CustomBuildEntity)

    @Query("DELETE FROM custom_builds WHERE id = :id")
    suspend fun deleteById(id: Long)
}
