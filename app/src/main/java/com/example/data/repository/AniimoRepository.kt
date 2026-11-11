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

    // Initial Community Seed Runs featuring real Aniimo characters, authentic held items, video proof links, and community Legit / Not Legit verification votes
    private val initialLeaderboardEntries = listOf(
        LeaderboardEntryEntity(
            id = 1,
            bossId = "alpha_nether_colossus",
            bossName = "Alpha Nether Colossus",
            runnerName = "PawprintMaster",
            mainAniimoId = "fulmintis",
            mainAniimoName = "Fulmintis",
            mainAniimoElement = "LIGHTNING",
            partnerAniimoId = "dazmand",
            partnerAniimoName = "Dazmand",
            partnerAniimoElement = "LIGHT",
            clearTimeMillis = 35240L,
            clearTimeDisplay = "00:35.24",
            videoProofUrl = "https://youtube.com/watch?v=aniimo_fulmintis_world_record",
            videoPlatform = "YouTube",
            weaponEquipped = "Pendant (+15 Contracted)",
            relicSetEquipped = "Partner: Capacitous Battery (+15) • Awakening: ATK > BREAK > REGEN",
            estimatedDpsOutput = 178000,
            submittedDate = "Today 02:14 UTC",
            isVerified = true,
            upvotes = 512,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "World Record clear. Fulmintis held Pendant (+15) for Enhanced Light Blade (+25% crit rate) while Dazmand held Capacitous Battery (+15). Maxed ATK & BREAK Awakening for frame-1 stagger.",
            legitVotes = 248,
            notLegitVotes = 4,
            userLegitVote = null,
            videoDurationSeconds = 38
        ),
        LeaderboardEntryEntity(
            id = 2,
            bossId = "alpha_whisperwake_kraken",
            bossName = "Alpha Whisperwake Kraken",
            runnerName = "GachaLuckGod",
            mainAniimoId = "gachapus",
            mainAniimoName = "Gachapus",
            mainAniimoElement = "WATER",
            partnerAniimoId = "wavwal",
            partnerAniimoName = "Wavwal",
            partnerAniimoElement = "ICE",
            clearTimeMillis = 39810L,
            clearTimeDisplay = "00:39.81",
            videoProofUrl = "https://youtube.com/watch?v=aniimo_gachapus_jackpot_sub40",
            videoPlatform = "YouTube",
            weaponEquipped = "Fission Needles (+15 Contracted)",
            relicSetEquipped = "Partner: Ferocious Fang (+15) • Awakening: ATK > BREAK > REGEN",
            estimatedDpsOutput = 171500,
            submittedDate = "Yesterday 18:30",
            isVerified = true,
            upvotes = 384,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Wavwal ice broke the Kraken in 12s. Popped Super Jackpot with Gachapus holding Fission Needles (+15); basic attacks critted for 420k each.",
            legitVotes = 186,
            notLegitVotes = 3,
            userLegitVote = null,
            videoDurationSeconds = 42
        ),
        LeaderboardEntryEntity(
            id = 3,
            bossId = "alpha_thunder_sovereign",
            bossName = "Alpha Thunder Sovereign",
            runnerName = "TitanClaw",
            mainAniimoId = "grizbo",
            mainAniimoName = "Grizbo",
            mainAniimoElement = "EARTH",
            partnerAniimoId = "bailite",
            partnerAniimoName = "Bailite",
            partnerAniimoElement = "EARTH",
            clearTimeMillis = 38620L,
            clearTimeDisplay = "00:38.62",
            videoProofUrl = "https://twitch.tv/videos/aniimo_grizbo_rage_wr",
            videoPlatform = "Twitch",
            weaponEquipped = "Ferocious Fang (+15 Contracted)",
            relicSetEquipped = "Partner: Bailite Crest (+15) • Awakening: ATK > BREAK > HP",
            estimatedDpsOutput = 168000,
            submittedDate = "2 days ago",
            isVerified = true,
            upvotes = 329,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Reached 100 Rage at 11s. Bailite shredded 35% earth defense, then Grizbo (holding Ferocious Fang +15) consumed 12 Claw Marks (+60% Might) to one-shot phase 2.",
            legitVotes = 162,
            notLegitVotes = 2,
            userLegitVote = null,
            videoDurationSeconds = 41
        ),
        LeaderboardEntryEntity(
            id = 4,
            bossId = "alpha_frost_titan_glacis",
            bossName = "Alpha Frost Titan Glacis",
            runnerName = "BloodReaper",
            mainAniimoId = "malevsera",
            mainAniimoName = "Malevsera",
            mainAniimoElement = "ICE",
            partnerAniimoId = "glacy",
            partnerAniimoName = "Glacy",
            partnerAniimoElement = "ICE",
            clearTimeMillis = 42150L,
            clearTimeDisplay = "00:42.15",
            videoProofUrl = "https://youtube.com/watch?v=aniimo_malevsera_bloodthirst_speedrun",
            videoPlatform = "YouTube",
            weaponEquipped = "Echoing Grimoire (+15 Contracted)",
            relicSetEquipped = "Partner: Capacitous Battery (+15) • Awakening: ATK > BREAK > REGEN",
            estimatedDpsOutput = 174000,
            submittedDate = "3 days ago",
            isVerified = true,
            upvotes = 295,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Ran Malevsera at 18% HP for max Bloodthirst scaling (+48%) with Echoing Grimoire (+15) equipped. 5-stack Frost Judgment hit for 1.8M damage.",
            legitVotes = 144,
            notLegitVotes = 5,
            userLegitVote = null,
            videoDurationSeconds = 45
        ),
        LeaderboardEntryEntity(
            id = 5,
            bossId = "alpha_whisperwake_kraken",
            bossName = "Alpha Whisperwake Kraken",
            runnerName = "ApexShark",
            mainAniimoId = "whirlbite",
            mainAniimoName = "Whirlbite",
            mainAniimoElement = "WATER",
            partnerAniimoId = "dewple",
            partnerAniimoName = "Dewple",
            partnerAniimoElement = "WATER",
            clearTimeMillis = 44520L,
            clearTimeDisplay = "00:44.52",
            videoProofUrl = "https://twitch.tv/videos/aniimo_whirlbite_riptide_sub45",
            videoPlatform = "Twitch",
            weaponEquipped = "Echoing Grimoire (+15 Contracted)",
            relicSetEquipped = "Partner: Tidal Pendant (+15) • Awakening: ATK > BREAK > PDEF",
            estimatedDpsOutput = 160000,
            submittedDate = "4 days ago",
            isVerified = true,
            upvotes = 210,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Dewple shredded Kraken water resistance. Whirlbite held Echoing Grimoire (+15) and looped Riptide Maw 5 times consecutively for maximum 25% damage boost.",
            legitVotes = 119,
            notLegitVotes = 3,
            userLegitVote = null,
            videoDurationSeconds = 47
        ),
        LeaderboardEntryEntity(
            id = 6,
            bossId = "alpha_nether_colossus",
            bossName = "Alpha Nether Colossus",
            runnerName = "FloraEmpress",
            mainAniimoId = "irisalis",
            mainAniimoName = "Irisalis",
            mainAniimoElement = "GRASS",
            partnerAniimoId = "leafy",
            partnerAniimoName = "Leafy",
            partnerAniimoElement = "GRASS",
            clearTimeMillis = 41900L,
            clearTimeDisplay = "00:41.90",
            videoProofUrl = "https://youtube.com/watch?v=aniimo_irisalis_clone_speedrun",
            videoPlatform = "YouTube",
            weaponEquipped = "Echoing Grimoire (+15 Contracted)",
            relicSetEquipped = "Partner: Capacitous Battery (+15) • Awakening: ATK > REGEN > BREAK",
            estimatedDpsOutput = 171000,
            submittedDate = "5 days ago",
            isVerified = true,
            upvotes = 340,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Leafy Canon Nature passive doubled clone duration. Irisalis held Echoing Grimoire (+15) while Verdant Sanctuary put both floral clone phantoms into overdrive.",
            legitVotes = 158,
            notLegitVotes = 4,
            userLegitVote = null,
            videoDurationSeconds = 44
        ),
        LeaderboardEntryEntity(
            id = 7,
            bossId = "alpha_magma_wyrm",
            bossName = "Alpha Magma Wyrm",
            runnerName = "VowKnight",
            mainAniimoId = "thornblade",
            mainAniimoName = "Thornblade",
            mainAniimoElement = "GRASS",
            partnerAniimoId = "bailite",
            partnerAniimoName = "Bailite",
            partnerAniimoElement = "EARTH",
            clearTimeMillis = 48720L,
            clearTimeDisplay = "00:48.72",
            videoProofUrl = "https://youtube.com/watch?v=aniimo_thornblade_sword_dance_clear",
            videoPlatform = "YouTube",
            weaponEquipped = "Fission Needles (+15 Contracted)",
            relicSetEquipped = "Partner: Bailite Crest (+15) • Awakening: ATK > BREAK > REGEN",
            estimatedDpsOutput = 164000,
            submittedDate = "5 days ago",
            isVerified = true,
            upvotes = 188,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Stacked 5 Sword Dance stacks (+40% basic dmg) with Thornblade holding Fission Needles (+15) as soon as Bailite broke the Wyrm's armor.",
            legitVotes = 97,
            notLegitVotes = 2,
            userLegitVote = null,
            videoDurationSeconds = 51
        ),
        LeaderboardEntryEntity(
            id = 8,
            bossId = "alpha_nether_colossus",
            bossName = "Alpha Nether Colossus",
            runnerName = "FakeClaimer99",
            mainAniimoId = "scorchhowl",
            mainAniimoName = "Scorchhowl",
            mainAniimoElement = "FIRE",
            partnerAniimoId = "emberpup",
            partnerAniimoName = "Emberpup",
            partnerAniimoElement = "FIRE",
            clearTimeMillis = 52100L,
            clearTimeDisplay = "00:52.10",
            videoProofUrl = "https://youtube.com/watch?v=fake_1sec_clip_nothing",
            videoPlatform = "YouTube",
            weaponEquipped = "Ferocious Fang (+15)",
            relicSetEquipped = "Partner: Capacitous Battery (+15) • Awakening: ATK > BREAK",
            estimatedDpsOutput = 142000,
            submittedDate = "1 day ago",
            isVerified = false,
            upvotes = 2,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Claimed 52.10s clear time but submitted a 1-second video clip showing only the menu screen. Flagged by community votes as Not Legit.",
            legitVotes = 3,
            notLegitVotes = 64,
            userLegitVote = null,
            videoDurationSeconds = 1
        ),
        LeaderboardEntryEntity(
            id = 9,
            bossId = "alpha_whisperwake_kraken",
            bossName = "Alpha Whisperwake Kraken",
            runnerName = "OneSecClipper",
            mainAniimoId = "fulmintis",
            mainAniimoName = "Fulmintis",
            mainAniimoElement = "LIGHTNING",
            partnerAniimoId = "wisptis",
            partnerAniimoName = "Wisptis",
            partnerAniimoElement = "LIGHTNING",
            clearTimeMillis = 54800L,
            clearTimeDisplay = "00:54.80",
            videoProofUrl = "https://twitch.tv/videos/1sec_black_screen_clip",
            videoPlatform = "Twitch",
            weaponEquipped = "Pendant (+15)",
            relicSetEquipped = "Partner: Capacitous Battery (+15) • Awakening: ATK > REGEN",
            estimatedDpsOutput = 138000,
            submittedDate = "2 days ago",
            isVerified = false,
            upvotes = 1,
            hasUserUpvoted = false,
            isUserSubmission = false,
            runnerNotes = "Submitted a 1-second video showing nothing of the boss encounter. Community voted Not Legit.",
            legitVotes = 2,
            notLegitVotes = 49,
            userLegitVote = null,
            videoDurationSeconds = 1
        )
    )

    suspend fun seedDatabaseIfEmpty() {
        if (leaderboardDao.getCount() == 0) {
            leaderboardDao.insertAll(initialLeaderboardEntries)
        }
        if (communityTierVoteDao.getCount() == 0) {
            val initialVotes = aniimos.map { aniimo ->
                createInitialCommunityVote(aniimo)
            }
            communityTierVoteDao.insertAll(initialVotes)
        }
        if (topRatedTeamDao.getCount() == 0) {
            topRatedTeamDao.insertAll(initialTopRatedTeams.map { com.example.data.local.TopRatedTeamEntity.fromDomain(it) })
        }
        if (discussionDao.getPostCount() == 0) {
            discussionDao.insertAll(initialDiscussionPosts.map { DiscussionPostEntity.fromDomainModel(it) })
        }
    }

    private fun createInitialCommunityVote(aniimo: Aniimo): CommunityTierVoteEntity {
        // Realistic distribution based on authentic community meta perception
        return when (aniimo.metaTier) {
            "S+" -> CommunityTierVoteEntity(
                aniimoId = aniimo.id,
                userVote = null,
                votesSPlus = 184,
                votesS = 48,
                votesA = 12,
                votesBPlus = 3,
                votesB = 1,
                votesC = 0
            )
            "S" -> CommunityTierVoteEntity(
                aniimoId = aniimo.id,
                userVote = null,
                votesSPlus = 42,
                votesS = 146,
                votesA = 32,
                votesBPlus = 8,
                votesB = 2,
                votesC = 0
            )
            "A" -> CommunityTierVoteEntity(
                aniimoId = aniimo.id,
                userVote = null,
                votesSPlus = 14,
                votesS = 36,
                votesA = 118,
                votesBPlus = 24,
                votesB = 6,
                votesC = 1
            )
            "B+" -> CommunityTierVoteEntity(
                aniimoId = aniimo.id,
                userVote = null,
                votesSPlus = 3,
                votesS = 12,
                votesA = 34,
                votesBPlus = 98,
                votesB = 20,
                votesC = 4
            )
            "B" -> CommunityTierVoteEntity(
                aniimoId = aniimo.id,
                userVote = null,
                votesSPlus = 1,
                votesS = 4,
                votesA = 16,
                votesBPlus = 32,
                votesB = 92,
                votesC = 10
            )
            else -> CommunityTierVoteEntity(
                aniimoId = aniimo.id,
                userVote = null,
                votesSPlus = 1,
                votesS = 2,
                votesA = 8,
                votesBPlus = 14,
                votesB = 38,
                votesC = 86
            )
        }
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
            ?: aniimos.find { it.id == aniimoId }?.let { createInitialCommunityVote(it) }
            ?: CommunityTierVoteEntity(aniimoId = aniimoId)

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

    private val initialDiscussionPosts: List<DiscussionPost> = listOf(
        DiscussionPost(
            id = 1,
            aniimoId = "fulmintis",
            aniimoName = "Fulmintis",
            aniimoElement = Element.LIGHTNING,
            authorName = "VoltTheory",
            authorBadge = "Top 10 Speedrunner",
            category = DiscussionCategory.BUILD_IDEA,
            title = "Why Pendant (+15) beats Ferocious Fang on Fulmintis after the Prismana update",
            content = "Since Fulmintis gets +25% Crit Rate and unlocks Enhanced Light Blade from Pendant (+15 Contracted), your basic slash weave inside the 5s Break window hits 100% Crit consistency. Pair with Dazmand holding Capacitous Battery (+15) so you never drop Ultimate uptime.",
            recommendedHeldItem = "Pendant (+15 Contracted)",
            recommendedAwakening = "ATK (20) > BREAK (15) > REGEN (12)",
            upvotes = 142,
            hasUserUpvoted = false,
            timestamp = "2h ago",
            replies = listOf(
                DiscussionReply("r1", "SparkHunter99", "Tested this on Alpha Whisperwake Kraken and shaved 4.2 seconds off my personal best!", "1h ago"),
                DiscussionReply("r2", "AegisTank", "Does this still work if you run Wisptis in Slot 3 instead of Dazmand?", "35m ago")
            )
        ),
        DiscussionPost(
            id = 2,
            aniimoId = "gachapus",
            aniimoName = "Gachapus",
            aniimoElement = Element.WATER,
            authorName = "JackpotKing",
            authorBadge = "Whisperwake Veteran",
            category = DiscussionCategory.TEAM_SYNERGY,
            title = "Gachapus + Fission Needles (+15) 4-Aniimo Water/Ice Freeze-Break Comp",
            content = "Run Gachapus (Fission Needles +15), Wavwal (Bailite Crest +15), Dewple (Tidal Pendant +15), and Glacy (Capacitous Battery +15). Wavwal freezes and shatters boss poise in 11s, then Gachapus Super Jackpot double-procs every basic projectile!",
            recommendedHeldItem = "Fission Needles (+15)",
            recommendedAwakening = "ATK (20) > BREAK (15) > REGEN (10)",
            upvotes = 118,
            hasUserUpvoted = false,
            timestamp = "4h ago",
            replies = listOf(
                DiscussionReply("r3", "TideCaller", "Fission Needles on Gachapus is hands-down the most fun build in Whisperwake Isles.", "2h ago")
            )
        ),
        DiscussionPost(
            id = 3,
            aniimoId = "irisalis",
            aniimoName = "Irisalis",
            aniimoElement = Element.GRASS,
            authorName = "PetalWeaver",
            authorBadge = "Vein Rift Clearer",
            category = DiscussionCategory.AWAKENING,
            title = "Optimal 6-Stat Awakening Order for Irisalis Floral Clone Overdrive",
            content = "With 540 base total attributes and 130 base ATK, Irisalis scales harder with ATK + REGEN than raw BREAK. Put 20 points into ATK first, 15 into REGEN so Verdant Sanctuary clones stay active 100% of the fight, and 12 into BREAK.",
            recommendedHeldItem = "Echoing Grimoire (+15)",
            recommendedAwakening = "ATK (20) > REGEN (15) > BREAK (12)",
            upvotes = 109,
            hasUserUpvoted = false,
            timestamp = "6h ago",
            replies = listOf(
                DiscussionReply("r4", "LeafyMain", "Pair her with Leafy for the Canon Nature passive and your clones hit like trucks.", "5h ago")
            )
        ),
        DiscussionPost(
            id = 4,
            aniimoId = "scorchhowl",
            aniimoName = "Scorchhowl",
            aniimoElement = Element.FIRE,
            authorName = "PyrePack",
            authorBadge = "Founder",
            category = DiscussionCategory.BUILD_IDEA,
            title = "Emberpup -> Flameruff -> Scorchhowl Starter Progression & Held Item Guide",
            content = "Keep Ferocious Fang on Emberpup all the way through Scorchhowl! Once you evolve Flameruff during Daytime at Lv. 20 into Scorchhowl, maxing Ferocious Fang to +15 gives +32% Crit DMG during boss Break windows.",
            recommendedHeldItem = "Ferocious Fang (+15)",
            recommendedAwakening = "ATK (20) > BREAK (15) > HP (8)",
            upvotes = 94,
            hasUserUpvoted = false,
            timestamp = "9h ago",
            replies = listOf(
                DiscussionReply("r5", "NightHowler", "Wait what if I evolved Flameruff at night into Inferlupa? Should I swap to Echoing Grimoire?", "7h ago"),
                DiscussionReply("r6", "PyrePack", "For Inferlupa, Supernova Shard (+15) or Ferocious Fang (+15) both work great!", "6h ago")
            )
        ),
        DiscussionPost(
            id = 5,
            aniimoId = "grizbo",
            aniimoName = "Grizbo",
            aniimoElement = Element.EARTH,
            authorName = "RosetowerBruiser",
            authorBadge = "1v1 Grandmaster",
            category = DiscussionCategory.PVP_EGG_HEIST,
            title = "Bruiser Grizbo is unstoppable in Egg Heist PvP & 1v1 Duels",
            content = "Instead of glass cannon, invest 18 HP / 18 ATK / 12 PDEF / 12 MDEF on Grizbo with Aegis Core (+15) or Ferocious Fang (+15). Nobody can knock the egg out of your hands in Egg Heist and Claw of Madness punishes anyone chasing you.",
            recommendedHeldItem = "Aegis Core (+15)",
            recommendedAwakening = "HP (18) > ATK (18) > PDEF (12) > MDEF (12)",
            upvotes = 87,
            hasUserUpvoted = false,
            timestamp = "12h ago",
            replies = listOf(
                DiscussionReply("r7", "BailiteEnjoyer", "Adding Bailite in Slot 2 for the Earth defense shred makes this build 1v3 capable.", "10h ago")
            )
        ),
        DiscussionPost(
            id = 6,
            aniimoId = "whirlbite",
            aniimoName = "Whirlbite",
            aniimoElement = Element.WATER,
            authorName = "WildwaterApex",
            authorBadge = "Theorycrafter",
            category = DiscussionCategory.BUILD_IDEA,
            title = "Looping Riptide Maw 5x with Echoing Grimoire (+15) on Whirlbite",
            content = "Whirlbite's extendable jaw combo resets faster if you cancel the recovery frame with a short dash. With Echoing Grimoire (+15), each consecutive Riptide Maw stacks +5% Skill DMG up to +25%.",
            recommendedHeldItem = "Echoing Grimoire (+15)",
            recommendedAwakening = "ATK (20) > BREAK (15) > PDEF (8)",
            upvotes = 76,
            hasUserUpvoted = false,
            timestamp = "1d ago",
            replies = emptyList()
        ),
        DiscussionPost(
            id = 7,
            aniimoId = "malevsera",
            aniimoName = "Malevsera",
            aniimoElement = Element.ICE,
            authorName = "UmbralFrost",
            authorBadge = "Alpha Raider",
            category = DiscussionCategory.AWAKENING,
            title = "Low-HP Bloodthirst + Demonic State Sustain Trick on Malevsera",
            content = "Malevsera has 110 base REGEN—the highest among all Ice DPS. If you invest 20 ATK / 15 BREAK / 14 REGEN, you can safely hover around 20% HP for the +48% Bloodthirst multiplier without getting one-shot by Frost Titan Glacis.",
            recommendedHeldItem = "Echoing Grimoire (+15)",
            recommendedAwakening = "ATK (20) > BREAK (15) > REGEN (14)",
            upvotes = 83,
            hasUserUpvoted = false,
            timestamp = "1d ago",
            replies = listOf(
                DiscussionReply("r8", "IceQueen", "Risky playstyle but the 1.8M Frost Judgment crits are unreal.", "18h ago")
            )
        ),
        DiscussionPost(
            id = 8,
            aniimoId = "ignitis",
            aniimoName = "Ignitis",
            aniimoElement = Element.FIRE,
            authorName = "CalderaRunner",
            authorBadge = "Sparkle Collector",
            category = DiscussionCategory.SPARKLE_HUNT,
            title = "Best Breezy Plains Ridge route to chain-hunt Dazzling & Shadow Ignitis",
            content = "Start at the northern caldera vent where 3 Ignitis packs spawn close together. Maintain your encounter chain above 40 without leaving the ridge zone to bring Sparkle odds down to 1/128!",
            recommendedHeldItem = "Supernova Shard (+15)",
            recommendedAwakening = "ATK (20) > BREAK (14) > REGEN (12)",
            upvotes = 91,
            hasUserUpvoted = false,
            timestamp = "2d ago",
            replies = listOf(
                DiscussionReply("r9", "ShinyChaser", "Got my Type VII Amethyst Ignitis at chain 52 using this exact route, thank you!", "1d ago")
            )
        ),
        DiscussionPost(
            id = 9,
            aniimoId = "zephyrion",
            aniimoName = "Zephyrion",
            aniimoElement = Element.WIND,
            authorName = "AetherArchitect",
            authorBadge = "Home Builder",
            category = DiscussionCategory.PRISMANA_SHOWCASE,
            title = "Aesthetic Floating Island Home & Prismana Crystal Shrine Build",
            content = "Showcasing my custom Wind & Sky aesthetic homestead! Built with floating limestone bridges, tiered Prismana energy conduits, and a dedicated training courtyard for Zephyrion and Ignitis. What do you all think?",
            recommendedHeldItem = "Prismana Heartstone (+15)",
            recommendedAwakening = "REGEN (20) > HP (16) > ATK (10)",
            mediaUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800&auto=format&fit=crop&q=80",
            mediaType = "image",
            upvotes = 142,
            hasUserUpvoted = false,
            timestamp = "3h ago",
            replies = listOf(
                DiscussionReply("r10", "BaseDesigner", "The crystal lighting looks incredible at night cycle!", "2h ago")
            )
        ),
        DiscussionPost(
            id = 10,
            aniimoId = "hydroseraph",
            aniimoName = "Hydroseraph",
            aniimoElement = Element.WATER,
            authorName = "WaterfallsMaster",
            authorBadge = "Prismana Artist",
            category = DiscussionCategory.PRISMANA_SHOWCASE,
            title = "Water Garden Home & Cascading Prismana Reservoir Video Tour",
            content = "Full video walkthrough of my 4-tier waterfall base setup and sacred Prismana fountain. Features custom elemental lanterns and an automated Aniimo sanctuary pool.",
            recommendedHeldItem = "Cascade Sigil (+15)",
            recommendedAwakening = "REGEN (20) > HP (18) > PDEF (10)",
            mediaUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            mediaType = "video",
            upvotes = 118,
            hasUserUpvoted = false,
            timestamp = "6h ago",
            replies = listOf(
                DiscussionReply("r11", "SeraphLover", "How did you get the water flow aligned with the stone columns? Clean setup!", "4h ago")
            )
        )
    )
}
