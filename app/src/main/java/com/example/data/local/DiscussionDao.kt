package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiscussionDao {
    @Query("SELECT * FROM discussion_posts ORDER BY upvotes DESC, id DESC")
    fun getAllPosts(): Flow<List<DiscussionPostEntity>>

    @Query("SELECT COUNT(*) FROM discussion_posts")
    suspend fun getPostCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<DiscussionPostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: DiscussionPostEntity)

    @Update
    suspend fun updatePost(post: DiscussionPostEntity)

    @Query("SELECT * FROM discussion_posts WHERE id = :postId LIMIT 1")
    suspend fun getPostById(postId: Int): DiscussionPostEntity?

    @Query("DELETE FROM discussion_posts")
    suspend fun clearAll()
}
