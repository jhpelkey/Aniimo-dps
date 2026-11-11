package com.example.data.repository

import com.example.data.local.AniimoDatabase
import com.example.data.local.CustomBuildEntity
import com.example.data.local.LeaderboardEntryEntity
import com.example.data.model.Aniimo
import com.example.data.model.BossEncounter
import com.example.data.model.CustomBuild
import com.example.data.model.Element
import com.example.data.model.LeaderboardEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import com.example.data.local.CommunityTierVoteEntity
import com.example.data.local.DiscussionPostEntity
import com.example.data.model.DiscussionCategory
import com.example.data.model.DiscussionPost
import com.example.data.model.DiscussionReply
import com.example.ui.components.AniimoOnlineImageResolver

class AniimoRepository(private val database: AniimoDatabase) {

    private val leaderboardDao = database.leaderboardDao()
    private val customBuildDao = database.customBuildDao()
    private val communityTierVoteDao = database.communityTierVoteDao()
    private val topRatedTeamDao = database.topRatedTeamDao()
    private val discussionDao = database.discussionDao()

    // Canonical Game Regions: Breezy Plains (195 forms) and Whisperwake Isles (42 forms, expanding to 54)
    private val whisperwakeSpeciesIds = setOf(
        "dewlop", "dewple", "whirlbite", "chompkin", "octoball", "tentacool", "gachapus",
        "reefish", "wavwal", "bailite", "saws", "jeburst", "malevsera", "sera", "abysser",
        "kraken", "tidehunter", "deepfang", "jabster", "crocobask",
        "coraliz", "hypnowl", "mesmerowl", "coinwisp", "pouchwisp", "bubbeep", "glameep",
        "popapus", "prawnch", "jeglid", "salabask", "mireling", "lilipole", "scrubbit",
        "ekrim", "sugarfish", "sugartini", "royalby", "hypnyto"
    )

    // Master Aniimo Roster from the actual game Aniimo (Pawprint Studio)
    // Includes all 126 verified species across Breezy Plains, Whisperwake Isles, and Season 1 Keepsakes
    val aniimos: List<Aniimo> = (aniimoCatalogPart1 + aniimoCatalogPart2 + aniimoCatalogPart3 + aniimoCatalogPart4).map { aniimo ->
        val isWhisperwake = whisperwakeSpeciesIds.contains(aniimo.id.lowercase())
        aniimo.copy(
            region = if (isWhisperwake) com.example.data.model.GameRegion.WHISPERWAKE_ISLES else com.example.data.model.GameRegion.BREEZY_PLAINS,
            remoteArtworkUrl = AniimoOnlineImageResolver.getDirectOnlineImageUrl(aniimo.id, aniimo.remoteArtworkUrl)
        )
    }

    // Official Roaming Field & Secret Alpha Boss Encounters from the game Aniimo
    val bossEncounters: List<BossEncounter> = AniimoAlphaBossesDatabase.allAlphaBosses

    // Starts completely fresh with zero generated leaderboard runs, discussion posts, or community votes
    private val initialLeaderboardEntries: List<LeaderboardEntryEntity> = emptyList()

    suspend fun seedDatabaseIfEmpty() {
        if (topRatedTeamDao.getCount() == 0) {
            topRatedTeamDao.insertAll(
                initialTopRatedTeams.map {
                    com.example.data.local.TopRatedTeamEntity.fromDomain(
                        it.copy(upvotes = 0, hasUserUpvoted = false)
                    )
                }
            )
        }
    }

    suspend fun clearAllCommunityPostsAndVotes() {
        leaderboardDao.clearAll()
        discussionDao.clearAll()
        communityTierVoteDao.clearAll()
    }

    private fun createInitialCommunityVote(aniimo: Aniimo): CommunityTierVoteEntity {
        return CommunityTierVoteEntity(
            aniimoId = aniimo.id,
            userVote = null,
            votesSPlus = 0,
            votesS = 0,
            votesA = 0,
            votesBPlus = 0,
            votesB = 0,
            votesC = 0
        )
    }

    // Community Tier Votes Flow
    fun getAllCommunityVotes(): Flow<Map<String, CommunityTierVoteEntity>> {
        return communityTierVoteDao.getAllVotes().map { list ->
            list.associateBy { it.aniimoId }
        }
    }

    fun getCommunityVoteForAniimo(aniimoId: String): Flow<CommunityTierVoteEntity?> {
        return communityTierVoteDao.getVoteForAniimo(aniimoId)
    }

    suspend fun submitTierVote(aniimoId: String, newTier: String) {
        val existing = communityTierVoteDao.getVoteSync(aniimoId)
            ?: createInitialCommunityVote(aniimos.find { it.id == aniimoId } ?: return)

        val updated = existing.withUserVote(newTier)
        communityTierVoteDao.insertOrUpdate(updated)
    }

    fun getAllLeaderboardEntries(): Flow<List<LeaderboardEntry>> {
        return leaderboardDao.getAllEntries().map { list -> list.map { it.toDomain() } }
    }

    fun getLeaderboardEntriesByBoss(bossId: String): Flow<List<LeaderboardEntry>> {
        return leaderboardDao.getEntriesByBoss(bossId).map { list -> list.map { it.toDomain() } }
    }

    suspend fun submitRun(entry: LeaderboardEntry): Long {
        val entity = LeaderboardEntryEntity.fromDomain(entry.copy(isUserSubmission = true))
        return leaderboardDao.insertEntry(entity)
    }

    suspend fun toggleUpvote(id: Long, currentHasUpvoted: Boolean) {
        val delta = if (currentHasUpvoted) -1 else 1
        leaderboardDao.toggleUpvote(id, delta, !currentHasUpvoted)
    }

    suspend fun voteLeaderboardLegitimacy(id: Long, voteLegit: Boolean) {
        val existing = leaderboardDao.getEntryById(id) ?: return
        val newLegitVotes: Int
        val newNotLegitVotes: Int
        val newUserVote: String

        if (voteLegit) {
            newLegitVotes = existing.legitVotes + 1
            newNotLegitVotes = if (existing.userLegitVote == "NOT_LEGIT") {
                (existing.notLegitVotes - 1).coerceAtLeast(0)
            } else {
                existing.notLegitVotes
            }
            newUserVote = "LEGIT"
        } else {
            newNotLegitVotes = existing.notLegitVotes + 1
            newLegitVotes = if (existing.userLegitVote == "LEGIT") {
                (existing.legitVotes - 1).coerceAtLeast(0)
            } else {
                existing.legitVotes
            }
            newUserVote = "NOT_LEGIT"
        }

        val updated = existing.copy(
            legitVotes = newLegitVotes,
            notLegitVotes = newNotLegitVotes,
            userLegitVote = newUserVote,
            isVerified = newLegitVotes > newNotLegitVotes
        )
        leaderboardDao.updateEntry(updated)
    }

    // Custom Builds
    fun getAllCustomBuilds(): Flow<List<CustomBuild>> {
        return customBuildDao.getAllBuilds().map { list -> list.map { it.toDomain() } }
    }

    suspend fun saveCustomBuild(build: CustomBuild): Long {
        return customBuildDao.insertBuild(CustomBuildEntity.fromDomain(build))
    }

    suspend fun deleteCustomBuild(id: Long) {
        customBuildDao.deleteById(id)
    }

    // Top Rated Teams Queries and Actions
    fun getAllTopRatedTeams(): Flow<List<com.example.data.model.TopRatedTeam>> {
        return topRatedTeamDao.getAllTeams().map { list -> list.map { it.toDomain() } }
    }

    fun getTopRatedTeamsForAniimo(aniimoId: String): Flow<List<com.example.data.model.TopRatedTeam>> {
        return topRatedTeamDao.getTeamsForAniimo(aniimoId).map { list -> list.map { it.toDomain() } }
    }

    fun getTopRatedTeamsByMode(mode: com.example.data.model.TeamMode): Flow<List<com.example.data.model.TopRatedTeam>> {
        return if (mode == com.example.data.model.TeamMode.ALL) {
            getAllTopRatedTeams()
        } else {
            topRatedTeamDao.getTeamsByMode(mode.name).map { list -> list.map { it.toDomain() } }
        }
    }

    suspend fun toggleTeamUpvote(teamId: String, currentHasUpvoted: Boolean) {
        val delta = if (currentHasUpvoted) -1 else 1
        topRatedTeamDao.toggleUpvote(teamId, delta, !currentHasUpvoted)
    }

    suspend fun saveTopRatedTeam(team: com.example.data.model.TopRatedTeam) {
        topRatedTeamDao.insertTeam(com.example.data.local.TopRatedTeamEntity.fromDomain(team.copy(isUserCreated = true)))
    }

    fun getAniimoById(id: String): Aniimo? {
        return aniimos.find { it.id == id }
    }

    fun getBossById(id: String): BossEncounter? {
        return bossEncounters.find { it.id == id }
    }

    // Discussion Board Queries & Actions
    fun getAllDiscussionPosts(): Flow<List<DiscussionPost>> {
        return discussionDao.getAllPosts().map { list -> list.map { it.toDomainModel() } }
    }

    suspend fun addDiscussionPost(post: DiscussionPost) {
        discussionDao.insertPost(DiscussionPostEntity.fromDomainModel(post))
    }

    suspend fun toggleDiscussionUpvote(postId: Int) {
        val existing = discussionDao.getPostById(postId) ?: return
        val newHasUpvoted = !existing.hasUserUpvoted
        val newUpvotes = (existing.upvotes + if (newHasUpvoted) 1 else -1).coerceAtLeast(0)
        discussionDao.updatePost(
            existing.copy(
                upvotes = newUpvotes,
                hasUserUpvoted = newHasUpvoted
            )
        )
    }

    suspend fun addDiscussionReply(postId: Int, authorName: String, replyContent: String) {
        val existing = discussionDao.getPostById(postId) ?: return
        val domainPost = existing.toDomainModel()
        val newReply = DiscussionReply(
            id = "rep_${System.currentTimeMillis()}",
            authorName = authorName.ifBlank { "Explorer" },
            content = replyContent.trim(),
            timestamp = "Just now"
        )
        val updatedReplies = domainPost.replies + newReply
        discussionDao.updatePost(
            existing.copy(
                repliesSerialized = DiscussionPostEntity.serializeReplies(updatedReplies)
            )
        )
    }

    private val initialDiscussionPosts: List<DiscussionPost> = emptyList()
}
