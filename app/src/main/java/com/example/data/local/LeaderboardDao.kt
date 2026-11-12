package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaderboardDao {
    @Query("SELECT * FROM leaderboard_entries ORDER BY clearTimeMillis ASC")
    fun getAllEntries(): Flow<List<LeaderboardEntryEntity>>

    @Query("SELECT * FROM leaderboard_entries WHERE bossId = :bossId ORDER BY clearTimeMillis ASC")
    fun getEntriesByBoss(bossId: String): Flow<List<LeaderboardEntryEntity>>

    @Query("SELECT * FROM leaderboard_entries WHERE id = :id LIMIT 1")
    suspend fun getEntryById(id: Long): LeaderboardEntryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: LeaderboardEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<LeaderboardEntryEntity>)

    @Update
    suspend fun updateEntry(entry: LeaderboardEntryEntity)

    @Query("UPDATE leaderboard_entries SET upvotes = upvotes + :delta, hasUserUpvoted = :hasUpvoted WHERE id = :id")
    suspend fun toggleUpvote(id: Long, delta: Int, hasUpvoted: Boolean)

    @Query("SELECT COUNT(*) FROM leaderboard_entries")
    suspend fun getCount(): Int

    @Query("DELETE FROM leaderboard_entries WHERE id = :id")
    suspend fun deleteEntryById(id: Long)

    @Query("DELETE FROM leaderboard_entries")
    suspend fun clearAll()
}

