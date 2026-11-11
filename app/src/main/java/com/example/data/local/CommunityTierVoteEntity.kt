package com.example.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Entity representing community player votes for an Aniimo's meta tier ranking.
 * Tiers: S+, S, A, B+, B, C.
 * The overall Aniimo tier is calculated dynamically from weighted user and community votes.
 */
@Entity(tableName = "community_tier_votes")
data class CommunityTierVoteEntity(
    @PrimaryKey
    val aniimoId: String,
    val userVote: String? = null, // "S+", "S", "A", "B+", "B", "C" or null
    val votesSPlus: Int = 0,
    val votesS: Int = 0,
    val votesA: Int = 0,
    val votesBPlus: Int = 0,
    val votesB: Int = 0,
    val votesC: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
) {
    val totalVotes: Int
        get() = votesSPlus + votesS + votesA + votesBPlus + votesB + votesC

    /**
     * Weighted community score out of 5.0:
     * S+ = 5.0, S = 4.0, A = 3.0, B+ = 2.0, B = 1.0, C = 0.0
     */
    val communityScore: Float
        get() {
            val total = totalVotes
            if (total == 0) return 3.0f // Neutral A-tier baseline
            val weightedSum = (votesSPlus * 5.0f) +
                    (votesS * 4.0f) +
                    (votesA * 3.0f) +
                    (votesBPlus * 2.0f) +
                    (votesB * 1.0f) +
                    (votesC * 0.0f)
            return weightedSum / total
        }

    /**
     * Dynamic Community Tier based on community votes:
     * >= 4.3 -> S+
     * >= 3.4 -> S
     * >= 2.4 -> A
     * >= 1.4 -> B+
     * >= 0.7 -> B
     * < 0.7  -> C
     */
    val communityTier: String
        get() {
            val score = communityScore
            return when {
                score >= 4.25f -> "S+"
                score >= 3.35f -> "S"
                score >= 2.35f -> "A"
                score >= 1.35f -> "B+"
                score >= 0.65f -> "B"
                else -> "C"
            }
        }

    fun getVoteCountForTier(tier: String): Int {
        return when (tier.uppercase()) {
            "S+" -> votesSPlus
            "S" -> votesS
            "A" -> votesA
            "B+" -> votesBPlus
            "B" -> votesB
            "C" -> votesC
            else -> 0
        }
    }

    fun getVotePercentageForTier(tier: String): Int {
        val total = totalVotes
        if (total == 0) return 0
        val count = getVoteCountForTier(tier)
        return ((count.toFloat() / total) * 100).toInt()
    }

    /**
     * Create a copy with a new vote cast by the current user.
     * Decrements the old userVote if any, and increments the new voted tier.
     */
    fun withUserVote(newTier: String): CommunityTierVoteEntity {
        var sPlus = votesSPlus
        var s = votesS
        var a = votesA
        var bPlus = votesBPlus
        var b = votesB
        var c = votesC

        // Remove previous user vote if present
        if (userVote != null) {
            when (userVote.uppercase()) {
                "S+" -> if (sPlus > 0) sPlus--
                "S" -> if (s > 0) s--
                "A" -> if (a > 0) a--
                "B+" -> if (bPlus > 0) bPlus--
                "B" -> if (b > 0) b--
                "C" -> if (c > 0) c--
            }
        }

        // Add new user vote
        when (newTier.uppercase()) {
            "S+" -> sPlus++
            "S" -> s++
            "A" -> a++
            "B+" -> bPlus++
            "B" -> b++
            "C" -> c++
        }

        return copy(
            userVote = newTier.uppercase(),
            votesSPlus = sPlus,
            votesS = s,
            votesA = a,
            votesBPlus = bPlus,
            votesB = b,
            votesC = c,
            updatedAt = System.currentTimeMillis()
        )
    }
}

@Dao
interface CommunityTierVoteDao {
    @Query("SELECT * FROM community_tier_votes")
    fun getAllVotes(): Flow<List<CommunityTierVoteEntity>>

    @Query("SELECT * FROM community_tier_votes WHERE aniimoId = :aniimoId")
    fun getVoteForAniimo(aniimoId: String): Flow<CommunityTierVoteEntity?>

    @Query("SELECT * FROM community_tier_votes WHERE aniimoId = :aniimoId")
    suspend fun getVoteSync(aniimoId: String): CommunityTierVoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(vote: CommunityTierVoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(votes: List<CommunityTierVoteEntity>)

    @Query("SELECT COUNT(*) FROM community_tier_votes")
    suspend fun getCount(): Int
}
