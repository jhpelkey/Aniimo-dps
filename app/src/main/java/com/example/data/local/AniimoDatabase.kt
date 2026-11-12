package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        LeaderboardEntryEntity::class,
        CustomBuildEntity::class,
        CommunityTierVoteEntity::class,
        TopRatedTeamEntity::class,
        DiscussionPostEntity::class
    ],
    version = 13,
    exportSchema = false
)
abstract class AniimoDatabase : RoomDatabase() {
    abstract fun leaderboardDao(): LeaderboardDao
    abstract fun customBuildDao(): CustomBuildDao
    abstract fun communityTierVoteDao(): CommunityTierVoteDao
    abstract fun topRatedTeamDao(): TopRatedTeamDao
    abstract fun discussionDao(): DiscussionDao

    companion object {
        @Volatile
        private var INSTANCE: AniimoDatabase? = null

        fun getDatabase(context: Context): AniimoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AniimoDatabase::class.java,
                    "aniimo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
