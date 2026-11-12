package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AniimoDatabase
import com.example.data.model.Aniimo
import com.example.data.model.BossEncounter
import com.example.data.model.CustomBuild
import com.example.data.model.Element
import com.example.data.model.LeaderboardEntry
import com.example.data.repository.AniimoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.concurrent.ConcurrentHashMap

enum class LeaderboardSort(val label: String) {
    FASTEST_TIME("Fastest Clear"),
    MOST_UPVOTED("Most Upvoted"),
    RECENT("Most Recent")
}

data class UiMessage(val text: String, val isError: Boolean = false)

@OptIn(ExperimentalCoroutinesApi::class)
class AniimoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AniimoRepository
    private val firestoreRepository: com.example.data.repository.AniimoFirestoreRepository
    private val patchNotesService: com.example.data.service.AniimoPatchNotesService

    companion object {
        const val ADMIN_EMAIL = "jhpelkey@gmail.com"
    }

    private val cloudEntryDocIds = ConcurrentHashMap<Long, String>()
    private val cloudPostDocIds = ConcurrentHashMap<Int, String>()
    private val _locallyDeletedTeamIds = MutableStateFlow<Set<String>>(emptySet())
    private val _locallyDeletedEntryIds = MutableStateFlow<Set<Long>>(emptySet())
    private val _locallyDeletedPostIds = MutableStateFlow<Set<Int>>(emptySet())
    private val _localModerationBans = MutableStateFlow<List<com.example.data.model.FirestoreModerationBan>>(emptyList())
    private val _adminOverrideEnabled = MutableStateFlow(false)

    private val authUidFlow: Flow<String?> = callbackFlow {
        val auth = try { Firebase.auth } catch (_: Exception) { null }
        if (auth == null) {
            trySend(null)
            close()
            return@callbackFlow
        }
        trySend(auth.currentUser?.uid)
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.uid)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }.distinctUntilChanged().catch { emit(null) }

    private val authEmailFlow: Flow<String?> = callbackFlow {
        val auth = try { Firebase.auth } catch (_: Exception) { null }
        if (auth == null) {
            trySend(null)
            close()
            return@callbackFlow
        }
        trySend(auth.currentUser?.email)
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.email)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }.distinctUntilChanged().catch { emit(null) }

    val currentUserEmail: StateFlow<String?> = authEmailFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), try { Firebase.auth.currentUser?.email } catch (_: Exception) { null })

    val isAdminUser: StateFlow<Boolean> = combine(
        authEmailFlow,
        _adminOverrideEnabled
    ) { email, overrideEnabled ->
        overrideEnabled || (!email.isNullOrBlank() && email.equals(ADMIN_EMAIL, ignoreCase = true))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        try { Firebase.auth.currentUser?.email?.equals(ADMIN_EMAIL, ignoreCase = true) == true } catch (_: Exception) { false }
    )

    val activeModerationBans: StateFlow<List<com.example.data.model.FirestoreModerationBan>>
    private val _localAppConfig = MutableStateFlow<com.example.data.model.FirestoreAppConfig?>(null)
    val liveAppConfig: StateFlow<com.example.data.model.FirestoreAppConfig>
    private var lastAppliedResetEpoch: Int = 0

    // Live Patch Balances & Releases State
    val cloudPatchNotes: StateFlow<List<com.example.data.model.FirestoreLivePatchNote>>
    private val _activeSkillChanges = MutableStateFlow<List<com.example.data.model.SkillBalanceChange>>(emptyList())
    val activeSkillChanges: StateFlow<List<com.example.data.model.SkillBalanceChange>> = _activeSkillChanges.asStateFlow()

    private val _activeStatChanges = MutableStateFlow<List<com.example.data.model.StatBalanceChange>>(emptyList())
    val activeStatChanges: StateFlow<List<com.example.data.model.StatBalanceChange>> = _activeStatChanges.asStateFlow()

    private val _isParsingWithAi = MutableStateFlow(false)
    val isParsingWithAi: StateFlow<Boolean> = _isParsingWithAi.asStateFlow()

    init {
        val db = AniimoDatabase.getDatabase(application)
        repository = AniimoRepository(db)
        firestoreRepository = com.example.data.repository.AniimoFirestoreRepository(application)
        patchNotesService = com.example.data.service.AniimoPatchNotesService()

        cloudPatchNotes = authUidFlow
            .flatMapLatest { firestoreRepository.observePatchNotes() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        liveAppConfig = combine(
            authUidFlow.flatMapLatest { firestoreRepository.observeAppConfig() },
            _localAppConfig
        ) { cloudConfig, localConfig ->
            localConfig ?: cloudConfig ?: com.example.data.model.FirestoreAppConfig()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            com.example.data.model.FirestoreAppConfig()
        )

        activeModerationBans = combine(
            authUidFlow.flatMapLatest { firestoreRepository.observeModerationBans() },
            _localModerationBans
        ) { cloudBans, localBans ->
            val combined = (cloudBans + localBans)
                .filter { it.isCurrentlyActive }
                .distinctBy { it.targetIdentifier.lowercase() }
            combined
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
        }

        // Reactively apply Over-The-Air remote reset epoch when pushed by Admin
        viewModelScope.launch {
            liveAppConfig.collect { config ->
                if (config.remoteResetEpoch > lastAppliedResetEpoch && lastAppliedResetEpoch != 0) {
                    lastAppliedResetEpoch = config.remoteResetEpoch
                    repository.clearAllCommunityPostsAndVotes()
                } else if (lastAppliedResetEpoch == 0) {
                    lastAppliedResetEpoch = config.remoteResetEpoch
                }
            }
        }

        // Reactively apply balance updates as new patches are synced in Firestore
        viewModelScope.launch {
            cloudPatchNotes.collect { patches ->
                val allSkills = mutableListOf<com.example.data.model.SkillBalanceChange>()
                val allStats = mutableListOf<com.example.data.model.StatBalanceChange>()
                patches.forEach { patch ->
                    allSkills.addAll(patchNotesService.decodeSkillChanges(patch.skillChangesJson))
                    allStats.addAll(patchNotesService.decodeStatChanges(patch.statChangesJson))
                }
                _activeSkillChanges.value = allSkills
                _activeStatChanges.value = allStats
            }
        }
    }

    fun syncOfficialPatchNotesUrl(url: String) {
        viewModelScope.launch {
            _isParsingWithAi.value = true
            try {
                val fetchResult = patchNotesService.fetchRawPatchNotesFromUrl(url)
                if (fetchResult.isSuccess) {
                    val rawText = fetchResult.getOrThrow()
                    val userUid = com.google.firebase.Firebase.auth.currentUser?.uid ?: "user_sync"
                    val parseResult = patchNotesService.parsePatchNotesWithAi(rawText, url, userUid)
                    if (parseResult.isSuccess) {
                        val patch = parseResult.getOrThrow()
                        firestoreRepository.publishPatchNote(patch)
                        _userMessage.value = UiMessage("Synced & applied ${patch.title} successfully!")
                    } else {
                        _userMessage.value = UiMessage(parseResult.exceptionOrNull()?.localizedMessage ?: "Failed to parse patch notes with AI", isError = true)
                    }
                } else {
                    _userMessage.value = UiMessage(fetchResult.exceptionOrNull()?.localizedMessage ?: "Failed to load patch notes URL", isError = true)
                }
            } finally {
                _isParsingWithAi.value = false
            }
        }
    }

    fun parseRawPatchNotesText(rawText: String) {
        viewModelScope.launch {
            _isParsingWithAi.value = true
            try {
                val userUid = com.google.firebase.Firebase.auth.currentUser?.uid ?: "user_sync"
                val parseResult = patchNotesService.parsePatchNotesWithAi(rawText, "https://aniimo.game/patch", userUid)
                if (parseResult.isSuccess) {
                    val patch = parseResult.getOrThrow()
                    firestoreRepository.publishPatchNote(patch)
                    _userMessage.value = UiMessage("Auto-configured ${patch.title} and updated multipliers!")
                } else {
                    _userMessage.value = UiMessage(parseResult.exceptionOrNull()?.localizedMessage ?: "Failed to parse patch notes", isError = true)
                }
            } finally {
                _isParsingWithAi.value = false
            }
        }
    }

    val aniimos: List<Aniimo> get() = repository.aniimos
    val bosses: List<BossEncounter> get() = repository.bossEncounters

    // Roster Screen State
    enum class StageFilter(val label: String) {
        ALL("All Forms"),
        PRE_EVOS("Pre-Evos"),
        FINAL_EVOS("Final (Nova)"),
        STANDALONE("Standalone")
    }

    // Community Tier Votes State
    val communityVotes: StateFlow<Map<String, com.example.data.local.CommunityTierVoteEntity>> =
        repository.getAllCommunityVotes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    enum class AniimoSortOption(val label: String, val shortLabel: String) {
        NUMBER("Aniimo #", "Number"),
        TIER("Community Tier", "Tier"),
        TYPE("Element Type", "Type"),
        DPS("Peak DPS", "DPS")
    }

    private val _selectedStageFilter = MutableStateFlow(StageFilter.ALL)
    val selectedStageFilter: StateFlow<StageFilter> = _selectedStageFilter.asStateFlow()

    private val _selectedRegionFilter = MutableStateFlow(com.example.data.model.GameRegion.ALL)
    val selectedRegionFilter: StateFlow<com.example.data.model.GameRegion> = _selectedRegionFilter.asStateFlow()

    private val _selectedSortOption = MutableStateFlow(AniimoSortOption.NUMBER)
    val selectedSortOption: StateFlow<AniimoSortOption> = _selectedSortOption.asStateFlow()

    private val _selectedElementFilter = MutableStateFlow<Element?>(null)
    val selectedElementFilter: StateFlow<Element?> = _selectedElementFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedAniimo = MutableStateFlow<Aniimo>(repository.aniimos.first())
    val selectedAniimo: StateFlow<Aniimo> = _selectedAniimo.asStateFlow()

    // Top Rated Teams State
    private val _selectedTeamModeFilter = MutableStateFlow(com.example.data.model.TeamMode.ALL)
    val selectedTeamModeFilter: StateFlow<com.example.data.model.TeamMode> = _selectedTeamModeFilter.asStateFlow()

    private val _teamSearchQuery = MutableStateFlow("")
    val teamSearchQuery: StateFlow<String> = _teamSearchQuery.asStateFlow()

    private val _selectedTeamAniimoFilter = MutableStateFlow<String?>(null)
    val selectedTeamAniimoFilter: StateFlow<String?> = _selectedTeamAniimoFilter.asStateFlow()

    private val cloudTeamsFlow: Flow<List<com.example.data.model.FirestoreTeam>> =
        authUidFlow.flatMapLatest { firestoreRepository.observePublicTeams() }

    val allTopRatedTeams: StateFlow<List<com.example.data.model.TopRatedTeam>> = combine(
        repository.getAllTopRatedTeams(),
        cloudTeamsFlow,
        _locallyDeletedTeamIds,
        activeModerationBans
    ) { localTeams, cloudTeams, deletedIds, bans ->
        val bannedNames = bans.map { it.targetAuthorName.lowercase() }.toSet()
        val bannedIds = bans.map { it.targetIdentifier.lowercase() }.toSet()
        val mappedCloud = cloudTeams.mapNotNull { fsTeam ->
            if (deletedIds.contains(fsTeam.id)) return@mapNotNull null
            if (bannedIds.contains(fsTeam.userId.lowercase()) || bannedNames.contains(fsTeam.authorName.lowercase())) {
                return@mapNotNull null
            }
            val resolvedIds = fsTeam.memberIds.ifEmpty { listOf("fulmintis", "dazmand", "bailite", "stellarys") }
            val resolvedAniimos = (0..3).map { idx ->
                val targetId = resolvedIds.getOrNull(idx) ?: ""
                repository.aniimos.find { it.id.equals(targetId, ignoreCase = true) }
                    ?: repository.aniimos.getOrElse(idx) { repository.aniimos.first() }
            }
            val primary = resolvedAniimos.first()
            val mode = com.example.data.model.TeamMode.entries.find {
                it.displayName.equals(fsTeam.tag, ignoreCase = true) || it.id.equals(fsTeam.tag, ignoreCase = true)
            } ?: com.example.data.model.TeamMode.DPS
            val defaultRoles = listOf(
                "Slot 1 • Main Burst Carry",
                "Slot 2 • Partner Synergy",
                "Slot 3 • Armor Break / Support",
                "Slot 4 • Flex / Sustain"
            )
            val members = resolvedAniimos.mapIndexed { index, aniimo ->
                com.example.data.model.TeamMemberBuild(
                    aniimoId = aniimo.id,
                    aniimoName = aniimo.name,
                    role = defaultRoles.getOrElse(index) { "Squad Member" },
                    element = aniimo.element,
                    heldItem = aniimo.bestItems.firstOrNull()?.name ?: "Pendant (+15 Contracted)",
                    heldItemEffect = aniimo.bestItems.firstOrNull()?.coreEffect ?: "Signature Held Item",
                    awakeningOrder = "ATK > BREAK > REGEN"
                )
            }
            com.example.data.model.TopRatedTeam(
                id = fsTeam.id.ifBlank { "cloud_team_${fsTeam.name.hashCode()}" },
                title = fsTeam.name.ifBlank { "${primary.name} Public Squad" },
                mode = mode,
                primaryAniimoId = primary.id,
                primaryAniimoName = primary.name,
                members = members,
                capabilities = listOf(
                    com.example.data.model.CapabilityPerk("ATK", "1st Priority", "Max Attack Awakening", 10),
                    com.example.data.model.CapabilityPerk("BREAK", "2nd Priority", "Boss Stagger Shred", 6),
                    com.example.data.model.CapabilityPerk("REGEN", "3rd Priority", "Energy Rotation Uptime", 4)
                ),
                pointInvestmentOrder = emptyList(),
                strategyGuide = fsTeam.description.ifBlank { "Shared public 4-Aniimo team build synced via Cloud." },
                estimatedDpsOrWinRate = "Cloud Public Build",
                author = fsTeam.authorName.ifBlank { "Community Builder" },
                upvotes = fsTeam.upvotes.coerceAtLeast(1),
                hasUserUpvoted = false,
                isUserCreated = true
            )
        }
        val merged = localTeams
            .filter { !deletedIds.contains(it.id) && !bannedNames.contains(it.author.lowercase()) }
            .toMutableList()
        for (cloudTeam in mappedCloud) {
            val duplicate = merged.any {
                it.id == cloudTeam.id ||
                    (it.title.equals(cloudTeam.title, ignoreCase = true) && it.author.equals(cloudTeam.author, ignoreCase = true))
            }
            if (!duplicate) {
                merged.add(0, cloudTeam)
            }
        }
        merged
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredTopRatedTeams: StateFlow<List<com.example.data.model.TopRatedTeam>> = combine(
        allTopRatedTeams,
        _selectedTeamModeFilter,
        _teamSearchQuery,
        _selectedTeamAniimoFilter
    ) { teams, mode, query, aniimoFilter ->
        teams.filter { team ->
            val matchesMode = mode == com.example.data.model.TeamMode.ALL || team.mode == mode
            val matchesAniimo = aniimoFilter == null || team.primaryAniimoId.equals(aniimoFilter, ignoreCase = true) || team.members.any { it.aniimoId.equals(aniimoFilter, ignoreCase = true) }
            val matchesQuery = query.isBlank() ||
                    team.title.contains(query, ignoreCase = true) ||
                    team.primaryAniimoName.contains(query, ignoreCase = true) ||
                    team.members.any {
                        it.aniimoName.contains(query, ignoreCase = true) ||
                        it.heldItem.contains(query, ignoreCase = true) ||
                        it.awakeningOrder.contains(query, ignoreCase = true)
                    } ||
                    team.capabilities.any {
                        it.name.contains(query, ignoreCase = true) ||
                        it.effect.contains(query, ignoreCase = true)
                    } ||
                    team.mode.displayName.contains(query, ignoreCase = true) ||
                    team.strategyGuide.contains(query, ignoreCase = true)
            matchesMode && matchesAniimo && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    data class RosterFilterParams(
        val element: Element?,
        val stage: StageFilter,
        val region: com.example.data.model.GameRegion,
        val sort: AniimoSortOption,
        val query: String
    )

    private val rosterFilterParams = combine(
        _selectedElementFilter,
        _selectedStageFilter,
        _selectedRegionFilter,
        _selectedSortOption,
        _searchQuery
    ) { element, stage, region, sort, query ->
        RosterFilterParams(element, stage, region, sort, query)
    }

    // Filtered & Sorted Aniimo List (Dynamic Community Tier & Game Region integration)
    val filteredAniimos: StateFlow<List<Aniimo>> = combine(
        rosterFilterParams,
        communityVotes
    ) { params, votesMap ->
        val filtered = repository.aniimos.filter { aniimo ->
            val matchesElement = params.element == null || aniimo.element == params.element
            val matchesRegion = params.region == com.example.data.model.GameRegion.ALL || aniimo.region == params.region
            val matchesStage = when (params.stage) {
                StageFilter.ALL -> true
                StageFilter.PRE_EVOS -> aniimo.stage.isPreEvolution
                StageFilter.FINAL_EVOS -> aniimo.stage == com.example.data.model.EvolutionStage.NOVA
                StageFilter.STANDALONE -> aniimo.stage == com.example.data.model.EvolutionStage.STANDALONE
            }
            val matchesQuery = params.query.isBlank() ||
                    aniimo.name.contains(params.query, ignoreCase = true) ||
                    aniimo.title.contains(params.query, ignoreCase = true) ||
                    aniimo.role.contains(params.query, ignoreCase = true) ||
                    aniimo.aniilogNumber.contains(params.query, ignoreCase = true) ||
                    aniimo.stage.stageName.contains(params.query, ignoreCase = true) ||
                    (aniimo.stage.isPreEvolution && (params.query.equals("pre", ignoreCase = true) || params.query.equals("baby", ignoreCase = true) || params.query.equals("pre-evo", ignoreCase = true))) ||
                    aniimo.skills.any { it.name.contains(params.query, ignoreCase = true) }
            matchesElement && matchesRegion && matchesStage && matchesQuery
        }

        when (params.sort) {
            AniimoSortOption.NUMBER -> {
                filtered.sortedBy { aniimo ->
                    Regex("""\d+""").find(aniimo.aniilogNumber)?.value?.toIntOrNull() ?: Int.MAX_VALUE
                }
            }
            AniimoSortOption.TIER -> {
                filtered.sortedWith(
                    compareBy<Aniimo> { aniimo ->
                        val voteEntity = votesMap[aniimo.id]
                        val effectiveTier = if (voteEntity != null && voteEntity.totalVotes > 0) voteEntity.communityTier else aniimo.metaTier
                        when (effectiveTier) {
                            "S+" -> 1
                            "S" -> 2
                            "A" -> 3
                            "B+" -> 4
                            "B" -> 5
                            else -> 6
                        }
                    }
                    .thenByDescending {
                        val v = votesMap[it.id]
                        if (v != null && v.totalVotes > 0) v.communityScore else 0f
                    }
                    .thenByDescending { it.maxOptimizedDps }
                )
            }
            AniimoSortOption.TYPE -> {
                filtered.sortedWith(
                    compareBy<Aniimo> { it.element.displayName }
                        .thenBy { Regex("""\d+""").find(it.aniilogNumber)?.value?.toIntOrNull() ?: Int.MAX_VALUE }
                )
            }
            AniimoSortOption.DPS -> {
                filtered.sortedByDescending { it.maxOptimizedDps }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.aniimos)

    fun selectStageFilter(filter: StageFilter) {
        _selectedStageFilter.value = filter
    }

    fun selectRegionFilter(region: com.example.data.model.GameRegion) {
        _selectedRegionFilter.value = region
    }

    fun selectSortOption(sortOption: AniimoSortOption) {
        _selectedSortOption.value = sortOption
    }

    fun selectTeamModeFilter(mode: com.example.data.model.TeamMode) {
        _selectedTeamModeFilter.value = mode
    }

    fun setTeamSearchQuery(query: String) {
        _teamSearchQuery.value = query
    }

    fun selectTeamAniimoFilter(aniimoId: String?) {
        _selectedTeamAniimoFilter.value = aniimoId
    }

    fun toggleTeamUpvote(teamId: String, currentHasUpvoted: Boolean) {
        viewModelScope.launch {
            repository.toggleTeamUpvote(teamId, currentHasUpvoted)
            val msg = if (currentHasUpvoted) "Removed upvote" else "Upvoted team composition!"
            _userMessage.value = UiMessage(msg)
        }
    }

    fun submitCustomTeam(team: com.example.data.model.TopRatedTeam) {
        findActiveBanForUser(team.author)?.let { ban ->
            _userMessage.value = UiMessage(formatBanMessage(ban), isError = true)
            return
        }
        viewModelScope.launch {
            repository.saveTopRatedTeam(team)
            try {
                val uid = Firebase.auth.currentUser?.uid
                if (!uid.isNullOrBlank()) {
                    firestoreRepository.saveTeam(
                        com.example.data.model.FirestoreTeam(
                            id = team.id,
                            userId = uid,
                            authorName = team.author.ifBlank { Firebase.auth.currentUser?.displayName ?: "Community Builder" },
                            name = team.title,
                            tag = team.mode.displayName,
                            description = team.strategyGuide,
                            memberIds = team.members.map { it.aniimoId },
                            isPublic = true,
                            upvotes = team.upvotes
                        )
                    )
                }
            } catch (_: Exception) {
                // Local Room persistence succeeded even if offline
            }
            _userMessage.value = UiMessage("Public team '${team.title}' published unrestricted to Community & Cloud!")
        }
    }

    fun getTopTeamsForAniimo(aniimoId: String): List<com.example.data.model.TopRatedTeam> {
        return allTopRatedTeams.value.filter {
            it.primaryAniimoId.equals(aniimoId, ignoreCase = true) ||
            it.members.any { m -> m.aniimoId.equals(aniimoId, ignoreCase = true) }
        }
    }

    fun voteForAniimoTier(aniimoId: String, tier: String) {
        viewModelScope.launch {
            repository.submitTierVote(aniimoId, tier)
            _userMessage.value = UiMessage("Recorded your $tier vote for community tier ranking!")
        }
    }

    fun getCommunityVoteInfo(aniimoId: String): com.example.data.local.CommunityTierVoteEntity? {
        return communityVotes.value[aniimoId]
    }

    fun getEvolutionLine(aniimo: Aniimo): List<Aniimo> {
        val family = mutableSetOf<Aniimo>()
        family.add(aniimo)

        // Traverse upwards (find parent / pre-evolution)
        var currUp: Aniimo? = aniimo
        while (currUp != null) {
            val parent = if (currUp.evolvesFromId != null) {
                repository.aniimos.find { it.id == currUp?.evolvesFromId }
            } else {
                repository.aniimos.find { it.evolvesToIds.contains(currUp?.id) }
            }
            if (parent != null && family.add(parent)) {
                currUp = parent
            } else {
                currUp = null
            }
        }

        // Traverse downwards (find evolutions)
        fun traverseDown(current: Aniimo) {
            val children = repository.aniimos.filter {
                current.evolvesToIds.contains(it.id) || it.evolvesFromId == current.id
            }
            for (child in children) {
                if (family.add(child)) {
                    traverseDown(child)
                }
            }
        }
        traverseDown(aniimo)

        return family.sortedBy {
            when (it.stage) {
                com.example.data.model.EvolutionStage.LUMIN -> 1
                com.example.data.model.EvolutionStage.GAMMA -> 2
                com.example.data.model.EvolutionStage.NOVA -> 3
                com.example.data.model.EvolutionStage.STANDALONE -> 4
            }
        }
    }

    // Leaderboard Screen State
    private val _selectedBossFilter = MutableStateFlow<String?>(null)
    val selectedBossFilter: StateFlow<String?> = _selectedBossFilter.asStateFlow()

    private val _leaderboardSort = MutableStateFlow(LeaderboardSort.FASTEST_TIME)
    val leaderboardSort: StateFlow<LeaderboardSort> = _leaderboardSort.asStateFlow()

    private val _showOnlyLegitEntries = MutableStateFlow(false)
    val showOnlyLegitEntries: StateFlow<Boolean> = _showOnlyLegitEntries.asStateFlow()

    private val cloudLeaderboardFlow: Flow<List<com.example.data.model.FirestoreLeaderboardEntry>> =
        authUidFlow.flatMapLatest { firestoreRepository.observeLeaderboardEntries() }

    private val rawEntries: Flow<List<LeaderboardEntry>> = combine(
        repository.getAllLeaderboardEntries(),
        cloudLeaderboardFlow,
        _locallyDeletedEntryIds,
        activeModerationBans
    ) { localEntries, cloudEntries, deletedIds, bans ->
        val bannedNames = bans.map { it.targetAuthorName.lowercase() }.toSet()
        val bannedIds = bans.map { it.targetIdentifier.lowercase() }.toSet()
        val mappedCloud = cloudEntries.mapNotNull { fsEntry ->
            val syntheticId = ((fsEntry.id.hashCode().toLong() and 0x7FFFFFFFL) + 100_000L)
            if (deletedIds.contains(syntheticId)) return@mapNotNull null
            if (bannedIds.contains(fsEntry.submitterUid.lowercase()) || bannedNames.contains(fsEntry.runnerName.lowercase())) {
                return@mapNotNull null
            }
            if (fsEntry.id.isNotBlank()) {
                cloudEntryDocIds[syntheticId] = fsEntry.id
            }
            val boss = repository.bossEncounters.find {
                it.name.equals(fsEntry.dungeonName, ignoreCase = true) ||
                    it.id.equals(fsEntry.dungeonName, ignoreCase = true)
            } ?: repository.bossEncounters.first()

            val ids = fsEntry.aniimoIds
            val main = repository.aniimos.find { it.id.equals(ids.getOrNull(0), ignoreCase = true) }
                ?: repository.aniimos.first()
            val partner = repository.aniimos.find { it.id.equals(ids.getOrNull(1), ignoreCase = true) }
                ?: repository.aniimos.getOrElse(1) { main }
            val slot3 = repository.aniimos.find { it.id.equals(ids.getOrNull(2), ignoreCase = true) }
                ?: repository.aniimos.getOrElse(2) { main }
            val slot4 = repository.aniimos.find { it.id.equals(ids.getOrNull(3), ignoreCase = true) }
                ?: repository.aniimos.getOrElse(3) { partner }

            val clearMillis = (fsEntry.clearTimeSeconds.coerceAtLeast(1) * 1000L)
            val urlLower = fsEntry.videoUrl.lowercase()
            val platform = when {
                urlLower.contains("youtube.com") || urlLower.contains("youtu.be") -> "YouTube"
                urlLower.contains("twitch.tv") -> "Twitch"
                urlLower.contains("bilibili.com") -> "Bilibili"
                else -> "Video Proof"
            }

            LeaderboardEntry(
                id = syntheticId,
                bossId = boss.id,
                bossName = boss.name,
                runnerName = fsEntry.runnerName.ifBlank { "Speedrunner" },
                mainAniimoId = main.id,
                mainAniimoName = main.name,
                mainAniimoElement = main.element,
                partnerAniimoId = partner.id,
                partnerAniimoName = partner.name,
                partnerAniimoElement = partner.element,
                slot3AniimoId = slot3.id,
                slot3AniimoName = slot3.name,
                slot3AniimoElement = slot3.element,
                slot4AniimoId = slot4.id,
                slot4AniimoName = slot4.name,
                slot4AniimoElement = slot4.element,
                clearTimeMillis = clearMillis,
                clearTimeDisplay = fsEntry.clearTimeFormatted.ifBlank {
                    val mins = fsEntry.clearTimeSeconds / 60
                    val secs = fsEntry.clearTimeSeconds % 60
                    String.format("%02d:%02d.00", mins, secs)
                },
                videoProofUrl = fsEntry.videoUrl,
                videoPlatform = platform,
                weaponEquipped = main.bestItems.firstOrNull()?.name ?: "Pendant (+15 Contracted)",
                relicSetEquipped = "Partner: ${partner.bestItems.firstOrNull()?.name ?: "Capacitous Battery (+15)"} • ATK > BREAK > REGEN",
                estimatedDpsOutput = main.maxOptimizedDps,
                submittedDate = "Cloud Live",
                isVerified = fsEntry.isVerified || fsEntry.legitVotes > fsEntry.notLegitVotes,
                upvotes = fsEntry.legitVotes.coerceAtLeast(1),
                hasUserUpvoted = false,
                isUserSubmission = fsEntry.submitterUid == (try { Firebase.auth.currentUser?.uid } catch (_: Exception) { null }),
                runnerNotes = fsEntry.notes.ifBlank { "4-Aniimo squad speedrun synced from Cloud Leaderboard." },
                legitVotes = fsEntry.legitVotes,
                notLegitVotes = fsEntry.notLegitVotes,
                userLegitVote = null,
                videoDurationSeconds = (fsEntry.clearTimeSeconds + 10).coerceAtLeast(15)
            )
        }

        val merged = localEntries
            .filter { !deletedIds.contains(it.id) && !bannedNames.contains(it.runnerName.lowercase()) }
            .map { local ->
                val matchingCloud = mappedCloud.find { cloud ->
                    cloud.runnerName.equals(local.runnerName, ignoreCase = true) &&
                        cloud.clearTimeDisplay == local.clearTimeDisplay &&
                        cloud.bossId == local.bossId
                }
                if (matchingCloud != null) {
                    cloudEntryDocIds[local.id]?.let { } ?: run {
                        cloudEntryDocIds[matchingCloud.id]?.let { docId -> cloudEntryDocIds[local.id] = docId }
                    }
                    local.copy(
                        legitVotes = maxOf(local.legitVotes, matchingCloud.legitVotes),
                        notLegitVotes = maxOf(local.notLegitVotes, matchingCloud.notLegitVotes),
                        isVerified = maxOf(local.legitVotes, matchingCloud.legitVotes) > maxOf(local.notLegitVotes, matchingCloud.notLegitVotes)
                    )
                } else {
                    local
                }
            }.toMutableList()

        for (cloud in mappedCloud) {
            val exists = merged.any { local ->
                local.runnerName.equals(cloud.runnerName, ignoreCase = true) &&
                    local.clearTimeDisplay == cloud.clearTimeDisplay &&
                    local.bossId == cloud.bossId
            }
            if (!exists) {
                merged.add(cloud)
            }
        }
        merged
    }

    val leaderboardEntries: StateFlow<List<LeaderboardEntry>> = combine(
        rawEntries,
        _selectedBossFilter,
        _leaderboardSort,
        _showOnlyLegitEntries
    ) { entries, bossId, sort, onlyLegit ->
        val byBoss = if (bossId == null) entries else entries.filter { it.bossId == bossId }
        val filtered = if (onlyLegit) {
            byBoss.filter { it.isLegitCommunityVerified }
        } else {
            byBoss
        }
        when (sort) {
            LeaderboardSort.FASTEST_TIME -> filtered.sortedBy { it.clearTimeMillis }
            LeaderboardSort.MOST_UPVOTED -> filtered.sortedByDescending { it.upvotes }
            LeaderboardSort.RECENT -> filtered.sortedByDescending { it.id }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Proof Video Dialog state
    private val _activeProofEntry = MutableStateFlow<LeaderboardEntry?>(null)
    val activeProofEntry: StateFlow<LeaderboardEntry?> = _activeProofEntry.asStateFlow()

    // Run Submission Dialog state & optional prefill from active build / Aniimo
    data class SubmitRunPrefill(
        val mainAniimoId: String? = null,
        val partnerAniimoId: String? = null,
        val slot3AniimoId: String? = null,
        val slot4AniimoId: String? = null,
        val weaponName: String = "",
        val relicSetName: String = ""
    )

    private val _isSubmitRunDialogOpen = MutableStateFlow(false)
    val isSubmitRunDialogOpen: StateFlow<Boolean> = _isSubmitRunDialogOpen.asStateFlow()

    private val _submitRunPrefill = MutableStateFlow(SubmitRunPrefill())
    val submitRunPrefill: StateFlow<SubmitRunPrefill> = _submitRunPrefill.asStateFlow()

    // Saved Custom Builds
    val customBuilds: StateFlow<List<CustomBuild>> = repository.getAllCustomBuilds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // DPS Simulator State: 4-Aniimo Team + 1 Held Item per Aniimo + 6-Stat Awakening (HP, ATK, BREAK, PDEF, MDEF, REGEN)
    private val _simMainAniimo = MutableStateFlow<Aniimo>(repository.aniimos.first { it.id == "fulmintis" })
    val simMainAniimo: StateFlow<Aniimo> = _simMainAniimo.asStateFlow()

    private val _simPartnerAniimo = MutableStateFlow<Aniimo>(
        repository.aniimos.first { it.id == "dazmand" }
    )
    val simPartnerAniimo: StateFlow<Aniimo> = _simPartnerAniimo.asStateFlow()

    private val _simSlot3Aniimo = MutableStateFlow<Aniimo>(
        repository.aniimos.first { it.id == "bailite" }
    )
    val simSlot3Aniimo: StateFlow<Aniimo> = _simSlot3Aniimo.asStateFlow()

    private val _simSlot4Aniimo = MutableStateFlow<Aniimo>(
        repository.aniimos.first { it.id == "stellarys" }
    )
    val simSlot4Aniimo: StateFlow<Aniimo> = _simSlot4Aniimo.asStateFlow()

    // 1 Held Item equipped per Aniimo
    private val _simMainHeldItem = MutableStateFlow(
        _simMainAniimo.value.bestItems.firstOrNull()?.name ?: "Pendant (+15 Contracted)"
    )
    val simMainHeldItem: StateFlow<String> = _simMainHeldItem.asStateFlow()

    private val _simPartnerHeldItem = MutableStateFlow(
        _simPartnerAniimo.value.bestItems.firstOrNull()?.name ?: "Capacitous Battery (+15)"
    )
    val simPartnerHeldItem: StateFlow<String> = _simPartnerHeldItem.asStateFlow()

    private val _simSlot3HeldItem = MutableStateFlow(
        _simSlot3Aniimo.value.bestItems.firstOrNull()?.name ?: "Bailite Crest (+15)"
    )
    val simSlot3HeldItem: StateFlow<String> = _simSlot3HeldItem.asStateFlow()

    private val _simSlot4HeldItem = MutableStateFlow(
        _simSlot4Aniimo.value.bestItems.firstOrNull()?.name ?: "Supernova Shard (+15)"
    )
    val simSlot4HeldItem: StateFlow<String> = _simSlot4HeldItem.asStateFlow()

    // Held Item Enhancement Level (+0 Base to +15 Contracted)
    private val _simItemEnhanceLevel = MutableStateFlow(15)
    val simItemEnhanceLevel: StateFlow<Int> = _simItemEnhanceLevel.asStateFlow()

    // Awakening (Capability) 6-Stat Point Allocation (0..20 points per stat)
    private val _simAwakeningAtk = MutableStateFlow(20)
    val simAwakeningAtk: StateFlow<Int> = _simAwakeningAtk.asStateFlow()

    private val _simAwakeningBreak = MutableStateFlow(15)
    val simAwakeningBreak: StateFlow<Int> = _simAwakeningBreak.asStateFlow()

    private val _simAwakeningRegen = MutableStateFlow(12)
    val simAwakeningRegen: StateFlow<Int> = _simAwakeningRegen.asStateFlow()

    private val _simAwakeningHp = MutableStateFlow(6)
    val simAwakeningHp: StateFlow<Int> = _simAwakeningHp.asStateFlow()

    private val _simAwakeningPdef = MutableStateFlow(4)
    val simAwakeningPdef: StateFlow<Int> = _simAwakeningPdef.asStateFlow()

    private val _simAwakeningMdef = MutableStateFlow(4)
    val simAwakeningMdef: StateFlow<Int> = _simAwakeningMdef.asStateFlow()

    // Legacy compatibility aliases mapped to ATK, BREAK, REGEN & Item Enhance Level
    val simSkill1Level: StateFlow<Int> get() = _simAwakeningAtk
    val simSkill2Level: StateFlow<Int> get() = _simAwakeningBreak
    val simUltLevel: StateFlow<Int> get() = _simAwakeningRegen
    val simWeaponBonus: StateFlow<Int> get() = _simItemEnhanceLevel
    val simRelicBonus: StateFlow<Int> get() = _simAwakeningHp

    // Snackbar/Toast messages
    private val _userMessage = MutableStateFlow<UiMessage?>(null)
    val userMessage: StateFlow<UiMessage?> = _userMessage.asStateFlow()

    // Computed DPS & Awakening Telemetry
    data class CalculatedDpsResult(
        val totalDps: Int,
        val burstWindowDps: Int,
        val synergyBonusPercent: Int,
        val resonanceTitle: String,
        val tierRating: String,
        val itemBonusPercent: Int = 45,
        val breakEfficiencyPercent: Int = 145,
        val energyRegenBonusPercent: Int = 136,
        val effectiveHpScore: Int = 28500,
        val awakeningSummary: String = "ATK (20) > BREAK (15) > REGEN (12)",
        val totalAwakeningPoints: Int = 61
    )

    val calculatedDpsResult: StateFlow<CalculatedDpsResult> = combine(
        _simMainAniimo,
        _simPartnerAniimo,
        _simSlot3Aniimo,
        _simSlot4Aniimo,
        _simMainHeldItem,
        _simPartnerHeldItem,
        _simItemEnhanceLevel,
        _simAwakeningAtk,
        _simAwakeningBreak,
        _simAwakeningRegen,
        _simAwakeningHp,
        _simAwakeningPdef,
        _simAwakeningMdef
    ) { args ->
        val main = args[0] as Aniimo
        val partner = args[1] as Aniimo
        val slot3 = args[2] as Aniimo
        val slot4 = args[3] as Aniimo
        val mainItem = args[4] as String
        val partnerItem = args[5] as String
        val itemLevel = args[6] as Int
        val atkPts = args[7] as Int
        val breakPts = args[8] as Int
        val regenPts = args[9] as Int
        val hpPts = args[10] as Int
        val pdefPts = args[11] as Int
        val mdefPts = args[12] as Int

        // Check if partner or squad members are listed synergy partners
        val directSynergy = main.bestPartners.find {
            it.partnerAniimoId == partner.id || it.partnerAniimoId == slot3.id || it.partnerAniimoId == slot4.id
        }
        val basePartnerBoost = directSynergy?.dpsBoostPercent ?: 18
        val squadElementalDiversityBonus = setOf(main.element, partner.element, slot3.element, slot4.element).size * 3
        val totalSynergyBoost = basePartnerBoost + squadElementalDiversityBonus
        val resonanceTitle = directSynergy?.resonanceTitle ?: "${main.element.displayName} & ${partner.element.displayName} 4-Unit Resonance"

        // 1 Held Item per Aniimo scaling: +0 gives +15% base item bonus, +15 Contracted unlocks +45% (or +52% if BiS signature item)
        val isBiSMainItem = main.bestItems.any { mainItem.contains(it.name, ignoreCase = true) }
        val isContractMaxed = itemLevel >= 15
        val mainItemBonusPct = 15 + (itemLevel * 2) + (if (isBiSMainItem) 8 else 0) + (if (isContractMaxed) 7 else 0)
        val partnerItemSupportPct = if (partnerItem.contains("Battery", ignoreCase = true) ||
            partnerItem.contains("Crest", ignoreCase = true) ||
            partnerItem.contains("Pendant", ignoreCase = true) ||
            partnerItem.contains("Shard", ignoreCase = true)
        ) 12 else 8
        val totalItemBonusPct = mainItemBonusPct + partnerItemSupportPct

        // Awakening (Capability) 6-Stat Scaling:
        // ATK: +2.8% direct skill & basic damage per point
        // REGEN: +1.6% sustained rotation uptime per point
        // BREAK: +2.2% stagger frequency (sustained) and +3.8% burst window multiplier per point
        val awakeningSustainedScaling = 1.0f + (atkPts * 0.028f) + (regenPts * 0.016f) + (breakPts * 0.012f)
        val itemScaling = 1.0f + (totalItemBonusPct / 100.0f)
        val synergyScaling = 1.0f + (totalSynergyBoost / 100.0f)
        val critFactor = 1.0f + (main.critRate / 100f * (main.critDmg - 100f) / 100f)

        val totalDps = (main.baseDps * awakeningSustainedScaling * itemScaling * synergyScaling * critFactor * 0.72f).toInt()
        val burstMultiplier = 2.15f + (breakPts * 0.028f) + (if (isContractMaxed) 0.20f else 0f)
        val burstWindowDps = (totalDps * burstMultiplier).toInt()

        val breakEfficiency = 100 + (breakPts * 4) + (if (slot3.role.contains("Break", ignoreCase = true) || slot3.role.contains("Tank", ignoreCase = true)) 15 else 8)
        val energyRegenBonus = 100 + (regenPts * 3) + (if (partnerItem.contains("Battery", ignoreCase = true)) 18 else 6)
        val effectiveHp = 18000 + (hpPts * 1250) + ((pdefPts + mdefPts) * 680)

        val statPairs = listOf(
            "ATK" to atkPts,
            "BREAK" to breakPts,
            "REGEN" to regenPts,
            "HP" to hpPts,
            "PDEF" to pdefPts,
            "MDEF" to mdefPts
        ).sortedByDescending { it.second }

        val awakeningSummary = statPairs.take(3).joinToString(" ➔ ") { "${it.first} (${it.second})" }
        val totalPoints = atkPts + breakPts + regenPts + hpPts + pdefPts + mdefPts

        val tierRating = when {
            totalDps >= 150000 -> "SSS Tier Meta"
            totalDps >= 120000 -> "SS Tier Optimal"
            totalDps >= 90000 -> "S Tier Competitive"
            else -> "A Tier Viable"
        }

        CalculatedDpsResult(
            totalDps = totalDps,
            burstWindowDps = burstWindowDps,
            synergyBonusPercent = totalSynergyBoost,
            resonanceTitle = resonanceTitle,
            tierRating = tierRating,
            itemBonusPercent = totalItemBonusPct,
            breakEfficiencyPercent = breakEfficiency,
            energyRegenBonusPercent = energyRegenBonus,
            effectiveHpScore = effectiveHp,
            awakeningSummary = awakeningSummary,
            totalAwakeningPoints = totalPoints
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CalculatedDpsResult(156000, 385000, 42, "Prismana Lightning-Light Resonance", "SSS Tier Meta")
    )

    // User Actions
    fun selectElementFilter(element: Element?) {
        _selectedElementFilter.value = element
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectAniimo(aniimo: Aniimo) {
        _selectedAniimo.value = aniimo
    }

    fun selectBossFilter(bossId: String?) {
        _selectedBossFilter.value = bossId
    }

    fun setLeaderboardSort(sort: LeaderboardSort) {
        _leaderboardSort.value = sort
    }

    fun setShowOnlyLegitEntries(onlyLegit: Boolean) {
        _showOnlyLegitEntries.value = onlyLegit
    }

    fun toggleShowOnlyLegitEntries() {
        _showOnlyLegitEntries.value = !_showOnlyLegitEntries.value
    }

    fun openProofDialog(entry: LeaderboardEntry) {
        _activeProofEntry.value = entry
    }

    fun closeProofDialog() {
        _activeProofEntry.value = null
    }

    fun openSubmitRunDialog(
        mainAniimoId: String? = null,
        partnerAniimoId: String? = null,
        slot3AniimoId: String? = null,
        slot4AniimoId: String? = null,
        weaponName: String = "",
        relicSetName: String = ""
    ) {
        _submitRunPrefill.value = SubmitRunPrefill(
            mainAniimoId = mainAniimoId,
            partnerAniimoId = partnerAniimoId,
            slot3AniimoId = slot3AniimoId,
            slot4AniimoId = slot4AniimoId,
            weaponName = weaponName,
            relicSetName = relicSetName
        )
        _isSubmitRunDialogOpen.value = true
    }

    fun closeSubmitRunDialog() {
        _isSubmitRunDialogOpen.value = false
    }

    fun showNotice(message: String) {
        _userMessage.value = UiMessage(message)
    }

    fun importBuildPayload(
        mainId: String,
        mainItem: String,
        partnerId: String,
        partnerItem: String,
        slot3Id: String,
        slot3Item: String,
        slot4Id: String,
        slot4Item: String,
        atk: Int,
        breakStat: Int,
        regen: Int,
        hp: Int,
        pdef: Int,
        mdef: Int
    ) {
        repository.aniimos.find { it.id.equals(mainId, ignoreCase = true) }?.let { _simMainAniimo.value = it }
        repository.aniimos.find { it.id.equals(partnerId, ignoreCase = true) }?.let { _simPartnerAniimo.value = it }
        repository.aniimos.find { it.id.equals(slot3Id, ignoreCase = true) }?.let { _simSlot3Aniimo.value = it }
        repository.aniimos.find { it.id.equals(slot4Id, ignoreCase = true) }?.let { _simSlot4Aniimo.value = it }
        if (mainItem.isNotBlank()) _simMainHeldItem.value = mainItem
        if (partnerItem.isNotBlank()) _simPartnerHeldItem.value = partnerItem
        if (slot3Item.isNotBlank()) _simSlot3HeldItem.value = slot3Item
        if (slot4Item.isNotBlank()) _simSlot4HeldItem.value = slot4Item
        setAwakeningStats(atk, breakStat, regen, hp, pdef, mdef)
        _userMessage.value = UiMessage("Loaded 4-Aniimo Build Code into My Builds!")
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }

    fun toggleUpvote(entry: LeaderboardEntry) {
        viewModelScope.launch {
            repository.toggleUpvote(entry.id, entry.hasUserUpvoted)
        }
    }

    fun voteLeaderboardLegitimacy(entry: LeaderboardEntry, voteLegit: Boolean) {
        viewModelScope.launch {
            repository.voteLeaderboardLegitimacy(entry.id, voteLegit)
            try {
                val docId = cloudEntryDocIds[entry.id]
                val voterUid = Firebase.auth.currentUser?.uid
                if (!docId.isNullOrBlank() && !voterUid.isNullOrBlank()) {
                    firestoreRepository.castLegitVote(
                        entryId = docId,
                        voterUid = voterUid,
                        isLegit = voteLegit,
                        reason = if (voteLegit) "Verified by community member" else "Flagged by community member"
                    )
                }
            } catch (_: Exception) {}
            // Keep active proof dialog in sync if open
            if (_activeProofEntry.value?.id == entry.id) {
                val newLegit = if (voteLegit) entry.legitVotes + 1 else if (entry.userLegitVote == "LEGIT") (entry.legitVotes - 1).coerceAtLeast(0) else entry.legitVotes
                val newNotLegit = if (!voteLegit) entry.notLegitVotes + 1 else if (entry.userLegitVote == "NOT_LEGIT") (entry.notLegitVotes - 1).coerceAtLeast(0) else entry.notLegitVotes
                _activeProofEntry.value = entry.copy(
                    legitVotes = newLegit,
                    notLegitVotes = newNotLegit,
                    userLegitVote = if (voteLegit) "LEGIT" else "NOT_LEGIT",
                    isVerified = newLegit > newNotLegit
                )
            }
            val msg = if (voteLegit) {
                "Voted LEGIT on ${entry.runnerName}'s speedrun proof!"
            } else {
                "Voted NOT LEGIT on ${entry.runnerName}'s submission."
            }
            _userMessage.value = UiMessage(msg)
        }
    }

    fun submitClearRun(
        runnerName: String,
        bossId: String,
        mainAniimoId: String,
        partnerAniimoId: String,
        slot3AniimoId: String = "",
        slot4AniimoId: String = "",
        minutes: Int,
        seconds: Int,
        millis: Int,
        videoUrl: String,
        weaponName: String,
        relicSetName: String,
        notes: String,
        videoDurationSeconds: Int = (minutes * 60 + seconds + 4).coerceAtLeast(1),
        customBossName: String? = null,
        customMainAniimoName: String? = null,
        customPartnerAniimoName: String? = null,
        customSlot3AniimoName: String? = null,
        customSlot4AniimoName: String? = null
    ): Boolean {
        if (runnerName.isBlank() || videoUrl.isBlank()) {
            _userMessage.value = UiMessage("Please enter runner name and video proof URL.", isError = true)
            return false
        }
        findActiveBanForUser(runnerName.trim())?.let { ban ->
            _userMessage.value = UiMessage(formatBanMessage(ban), isError = true)
            return false
        }

        val totalMillis = (minutes * 60L * 1000L) + (seconds * 1000L) + (millis * 10L)
        if (totalMillis <= 0) {
            _userMessage.value = UiMessage("Clear time must be greater than 0.", isError = true)
            return false
        }

        val formattedTime = String.format("%02d:%02d.%02d", minutes, seconds, millis)
        val boss = repository.getBossById(bossId) ?: repository.bossEncounters.first()
        val resolvedBossName = if (!customBossName.isNullOrBlank()) customBossName.trim() else boss.name
        val resolvedBossId = if (!customBossName.isNullOrBlank()) "custom_${resolvedBossName.lowercase().replace(" ", "_")}" else boss.id

        val main = repository.getAniimoById(mainAniimoId) ?: repository.aniimos.first()
        val resolvedMainAniimoName = if (!customMainAniimoName.isNullOrBlank()) customMainAniimoName.trim() else main.name
        val resolvedMainAniimoId = if (!customMainAniimoName.isNullOrBlank()) "custom_${resolvedMainAniimoName.lowercase().replace(" ", "_")}" else main.id

        val partner = repository.getAniimoById(partnerAniimoId) ?: repository.aniimos.last()
        val resolvedPartnerAniimoName = if (!customPartnerAniimoName.isNullOrBlank()) customPartnerAniimoName.trim() else partner.name
        val resolvedPartnerAniimoId = if (!customPartnerAniimoName.isNullOrBlank()) "custom_${resolvedPartnerAniimoName.lowercase().replace(" ", "_")}" else partner.id

        val slot3 = repository.getAniimoById(slot3AniimoId) ?: repository.aniimos.getOrNull(2) ?: repository.aniimos.first()
        val resolvedSlot3Name = if (!customSlot3AniimoName.isNullOrBlank()) customSlot3AniimoName.trim() else slot3.name
        val resolvedSlot3Id = if (!customSlot3AniimoName.isNullOrBlank()) "custom_${resolvedSlot3Name.lowercase().replace(" ", "_")}" else slot3.id

        val slot4 = repository.getAniimoById(slot4AniimoId) ?: repository.aniimos.getOrNull(3) ?: repository.aniimos.last()
        val resolvedSlot4Name = if (!customSlot4AniimoName.isNullOrBlank()) customSlot4AniimoName.trim() else slot4.name
        val resolvedSlot4Id = if (!customSlot4AniimoName.isNullOrBlank()) "custom_${resolvedSlot4Name.lowercase().replace(" ", "_")}" else slot4.id

        val platform = when {
            videoUrl.contains("youtube.com", ignoreCase = true) || videoUrl.contains("youtu.be", ignoreCase = true) -> "YouTube"
            videoUrl.contains("twitch.tv", ignoreCase = true) -> "Twitch"
            videoUrl.contains("bilibili", ignoreCase = true) -> "Bilibili"
            videoUrl.contains("streamable", ignoreCase = true) -> "Streamable"
            else -> "Web Video"
        }

        val defaultMainItem = main.bestItems.firstOrNull()?.name ?: "Ferocious Fang (+15 Contracted)"
        val defaultPartnerAndAwakening = "Team: $resolvedMainAniimoName, $resolvedPartnerAniimoName, $resolvedSlot3Name, $resolvedSlot4Name"

        // Automatic 1-sec / short clip check: if video duration is shorter than the claimed clear time, auto-flag as Not Legit!
        val isClipTooShort = videoDurationSeconds <= 3 || (videoDurationSeconds * 1000L) < (totalMillis - 2000L)
        val initialLegitVotes = if (isClipTooShort) 0 else 1
        val initialNotLegitVotes = if (isClipTooShort) 2 else 0

        val newEntry = LeaderboardEntry(
            id = 0,
            bossId = resolvedBossId,
            bossName = resolvedBossName,
            runnerName = runnerName.trim(),
            mainAniimoId = resolvedMainAniimoId,
            mainAniimoName = resolvedMainAniimoName,
            mainAniimoElement = main.element,
            partnerAniimoId = resolvedPartnerAniimoId,
            partnerAniimoName = resolvedPartnerAniimoName,
            partnerAniimoElement = partner.element,
            slot3AniimoId = resolvedSlot3Id,
            slot3AniimoName = resolvedSlot3Name,
            slot3AniimoElement = slot3.element,
            slot4AniimoId = resolvedSlot4Id,
            slot4AniimoName = resolvedSlot4Name,
            slot4AniimoElement = slot4.element,
            clearTimeMillis = totalMillis,
            clearTimeDisplay = formattedTime,
            videoProofUrl = videoUrl.trim(),
            videoPlatform = platform,
            weaponEquipped = weaponName.ifBlank { defaultMainItem },
            relicSetEquipped = relicSetName.ifBlank { defaultPartnerAndAwakening },
            estimatedDpsOutput = main.maxOptimizedDps,
            submittedDate = "Just now",
            isVerified = !isClipTooShort,
            upvotes = 1,
            hasUserUpvoted = true,
            isUserSubmission = true,
            runnerNotes = notes.trim().ifBlank { "4-Aniimo squad speedrun submission." },
            legitVotes = initialLegitVotes,
            notLegitVotes = initialNotLegitVotes,
            userLegitVote = if (isClipTooShort) null else "LEGIT",
            videoDurationSeconds = videoDurationSeconds.coerceAtLeast(1)
        )

        viewModelScope.launch {
            repository.submitRun(newEntry)
            try {
                val userUid = Firebase.auth.currentUser?.uid ?: "user_${runnerName.lowercase().replace(" ", "_")}"
                firestoreRepository.submitLeaderboardEntry(
                    com.example.data.model.FirestoreLeaderboardEntry(
                        submitterUid = userUid,
                        runnerName = runnerName.trim(),
                        dungeonName = resolvedBossName,
                        teamName = "$resolvedMainAniimoName, $resolvedPartnerAniimoName, $resolvedSlot3Name, $resolvedSlot4Name",
                        aniimoIds = listOf(resolvedMainAniimoId, resolvedPartnerAniimoId, resolvedSlot3Id, resolvedSlot4Id),
                        clearTimeSeconds = (totalMillis / 1000L).toInt(),
                        clearTimeFormatted = formattedTime,
                        videoUrl = videoUrl.trim(),
                        notes = notes.trim(),
                        legitVotes = initialLegitVotes,
                        notLegitVotes = initialNotLegitVotes,
                        isVerified = !isClipTooShort
                    )
                )
            } catch (_: Exception) {
                // Preserved local Room persistence if offline or unauthenticated
            }

            val statusMsg = if (isClipTooShort) {
                "Run for $resolvedBossName submitted, but flagged RED (Not Legit) because video duration (${videoDurationSeconds}s) is shorter than clear time!"
            } else {
                "Run for $resolvedBossName submitted and verified GREEN (Legit) in leaderboard!"
            }
            _userMessage.value = UiMessage(statusMsg)
            _isSubmitRunDialogOpen.value = false
        }
        return true
    }

    // Simulator controls
    fun setSimMainAniimo(aniimo: Aniimo) {
        _simMainAniimo.value = aniimo
        _simMainHeldItem.value = aniimo.bestItems.firstOrNull()?.name ?: "Ferocious Fang (+15)"
        if (_simPartnerAniimo.value.id == aniimo.id) {
            val other = repository.aniimos.firstOrNull { it.id != aniimo.id }
            if (other != null) {
                _simPartnerAniimo.value = other
                _simPartnerHeldItem.value = other.bestItems.firstOrNull()?.name ?: "Capacitous Battery (+15)"
            }
        }
    }

    fun setSimPartnerAniimo(partner: Aniimo) {
        _simPartnerAniimo.value = partner
        _simPartnerHeldItem.value = partner.bestItems.firstOrNull()?.name ?: "Capacitous Battery (+15)"
    }

    fun setSimSlot3Aniimo(aniimo: Aniimo) {
        _simSlot3Aniimo.value = aniimo
        _simSlot3HeldItem.value = aniimo.bestItems.firstOrNull()?.name ?: "Bailite Crest (+15)"
    }

    fun setSimSlot4Aniimo(aniimo: Aniimo) {
        _simSlot4Aniimo.value = aniimo
        _simSlot4HeldItem.value = aniimo.bestItems.firstOrNull()?.name ?: "Supernova Shard (+15)"
    }

    fun setSimMainHeldItem(itemName: String) {
        _simMainHeldItem.value = itemName
    }

    fun setSimPartnerHeldItem(itemName: String) {
        _simPartnerHeldItem.value = itemName
    }

    fun setSimSlot3HeldItem(itemName: String) {
        _simSlot3HeldItem.value = itemName
    }

    fun setSimSlot4HeldItem(itemName: String) {
        _simSlot4HeldItem.value = itemName
    }

    fun setSimItemEnhanceLevel(level: Int) {
        _simItemEnhanceLevel.value = level.coerceIn(0, 15)
    }

    fun setAwakeningStats(
        atk: Int = _simAwakeningAtk.value,
        breakStat: Int = _simAwakeningBreak.value,
        regen: Int = _simAwakeningRegen.value,
        hp: Int = _simAwakeningHp.value,
        pdef: Int = _simAwakeningPdef.value,
        mdef: Int = _simAwakeningMdef.value
    ) {
        _simAwakeningAtk.value = atk.coerceIn(0, 20)
        _simAwakeningBreak.value = breakStat.coerceIn(0, 20)
        _simAwakeningRegen.value = regen.coerceIn(0, 20)
        _simAwakeningHp.value = hp.coerceIn(0, 20)
        _simAwakeningPdef.value = pdef.coerceIn(0, 20)
        _simAwakeningMdef.value = mdef.coerceIn(0, 20)
    }

    fun setSkillLevels(s1: Int, s2: Int, ult: Int) {
        _simAwakeningAtk.value = s1.coerceIn(0, 20)
        _simAwakeningBreak.value = s2.coerceIn(0, 20)
        _simAwakeningRegen.value = ult.coerceIn(0, 20)
    }

    fun setGearBonuses(weapon: Int, relic: Int) {
        _simItemEnhanceLevel.value = weapon.coerceIn(0, 15)
        _simAwakeningHp.value = relic.coerceIn(0, 20)
    }

    fun saveCurrentSimulation(title: String) {
        val result = calculatedDpsResult.value
        val main = _simMainAniimo.value
        val partner = _simPartnerAniimo.value
        val slot3 = _simSlot3Aniimo.value
        val slot4 = _simSlot4Aniimo.value
        val enhanceSuffix = "+${_simItemEnhanceLevel.value}" + if (_simItemEnhanceLevel.value >= 15) " Contracted" else ""

        val build = CustomBuild(
            id = 0,
            buildTitle = title.ifBlank { "${main.name}, ${partner.name}, ${slot3.name} & ${slot4.name}" },
            aniimoId = main.id,
            aniimoName = main.name,
            aniimoElement = main.element,
            partnerId = partner.id,
            partnerName = partner.name,
            partnerElement = partner.element,
            skill1Level = _simAwakeningAtk.value,
            skill2Level = _simAwakeningBreak.value,
            ultimateLevel = _simAwakeningRegen.value,
            weaponName = "${_simMainHeldItem.value} ($enhanceSuffix)",
            relicSetName = "Partner: ${_simPartnerHeldItem.value} • Awakening: ${result.awakeningSummary}",
            calculatedDps = result.totalDps,
            burstDps = result.burstWindowDps,
            elementalBonusPercent = result.synergyBonusPercent,
            critRatePercent = main.critRate,
            critDmgPercent = main.critDmg
        )

        viewModelScope.launch {
            repository.saveCustomBuild(build)
            try {
                val uid = Firebase.auth.currentUser?.uid
                if (!uid.isNullOrBlank()) {
                    firestoreRepository.saveTeam(
                        com.example.data.model.FirestoreTeam(
                            userId = uid,
                            authorName = Firebase.auth.currentUser?.displayName ?: "Community Builder",
                            name = build.buildTitle,
                            tag = "Public Build",
                            description = "4-Aniimo Build (${main.name}, ${partner.name}, ${slot3.name}, ${slot4.name}) • ${build.calculatedDps} DPS",
                            memberIds = listOf(main.id, partner.id, slot3.id, slot4.id),
                            isPublic = true,
                            upvotes = 1
                        )
                    )
                }
            } catch (_: Exception) {
                // Local Room persistence succeeded even if offline
            }
            _userMessage.value = UiMessage("Public 4-Aniimo Build '${build.buildTitle}' saved & synced unrestricted!")
        }
    }

    fun deleteCustomBuild(id: Long) {
        viewModelScope.launch {
            repository.deleteCustomBuild(id)
            _userMessage.value = UiMessage("Build deleted.")
        }
    }

    // Discussion Board State & Actions
    private val _selectedDiscussionAniimoFilter = MutableStateFlow<String?>(null)
    val selectedDiscussionAniimoFilter: StateFlow<String?> = _selectedDiscussionAniimoFilter.asStateFlow()

    private val _selectedDiscussionCategory = MutableStateFlow(com.example.data.model.DiscussionCategory.ALL)
    val selectedDiscussionCategory: StateFlow<com.example.data.model.DiscussionCategory> = _selectedDiscussionCategory.asStateFlow()

    private val _discussionSearchQuery = MutableStateFlow("")
    val discussionSearchQuery: StateFlow<String> = _discussionSearchQuery.asStateFlow()

    private val cloudDiscussionFlow: Flow<List<com.example.data.model.FirestoreDiscussionPost>> =
        authUidFlow.flatMapLatest { firestoreRepository.observeDiscussionPosts() }

    val allDiscussionPosts: StateFlow<List<com.example.data.model.DiscussionPost>> = combine(
        repository.getAllDiscussionPosts(),
        cloudDiscussionFlow,
        _locallyDeletedPostIds,
        activeModerationBans
    ) { localPosts, cloudPosts, deletedIds, bans ->
        val bannedNames = bans.map { it.targetAuthorName.lowercase() }.toSet()
        val bannedIds = bans.map { it.targetIdentifier.lowercase() }.toSet()
        val heldRegex = Regex("""\[Held Item:\s*([^\]]*)\]""")
        val awakeRegex = Regex("""\[Awakening:\s*([^\]]*)\]""")
        val mediaUrlRegex = Regex("""\[MediaUrl:\s*([^\]]*)\]""")
        val mediaTypeRegex = Regex("""\[MediaType:\s*([^\]]*)\]""")

        val mappedCloud = cloudPosts.mapNotNull { fsPost ->
            val syntheticId = ((fsPost.id.hashCode() and 0x7FFFFFFF) % 900_000) + 100_000
            if (deletedIds.contains(syntheticId)) return@mapNotNull null
            if (bannedIds.contains(fsPost.authorUid.lowercase()) || bannedNames.contains(fsPost.authorName.lowercase())) {
                return@mapNotNull null
            }
            if (fsPost.id.isNotBlank()) {
                cloudPostDocIds[syntheticId] = fsPost.id
            }
            val matchedAniimo = repository.aniimos.find {
                it.id.equals(fsPost.aniimoId, ignoreCase = true) ||
                    it.name.equals(fsPost.aniimoName, ignoreCase = true)
            }
            val parsedHeld = heldRegex.find(fsPost.body)?.groupValues?.getOrNull(1)?.trim().orEmpty()
            val parsedAwake = awakeRegex.find(fsPost.body)?.groupValues?.getOrNull(1)?.trim().orEmpty()
            val parsedMediaUrl = fsPost.mediaUrl.ifBlank {
                mediaUrlRegex.find(fsPost.body)?.groupValues?.getOrNull(1)?.trim().orEmpty()
            }
            val parsedMediaType = fsPost.mediaType.ifBlank {
                mediaTypeRegex.find(fsPost.body)?.groupValues?.getOrNull(1)?.trim().orEmpty()
            }
            val cleanBody = fsPost.body
                .replace(heldRegex, "")
                .replace(awakeRegex, "")
                .replace(mediaUrlRegex, "")
                .replace(mediaTypeRegex, "")
                .trim()

            val category = com.example.data.model.DiscussionCategory.values().find {
                it.displayName.equals(fsPost.tag, ignoreCase = true) ||
                    it.id.equals(fsPost.tag, ignoreCase = true)
            } ?: com.example.data.model.DiscussionCategory.BUILD_IDEA

            com.example.data.model.DiscussionPost(
                id = syntheticId,
                aniimoId = fsPost.aniimoId.ifBlank { matchedAniimo?.id ?: "fulmintis" },
                aniimoName = fsPost.aniimoName.ifBlank { matchedAniimo?.name ?: "Fulmintis" },
                aniimoElement = matchedAniimo?.element ?: Element.LIGHTNING,
                authorName = fsPost.authorName.ifBlank { "Community Explorer" },
                authorBadge = if (category == com.example.data.model.DiscussionCategory.PRISMANA_SHOWCASE) "Prismana Architect" else "Community Member",
                category = category,
                title = fsPost.title,
                content = cleanBody.ifBlank { fsPost.body },
                recommendedHeldItem = parsedHeld,
                recommendedAwakening = parsedAwake,
                mediaUrl = parsedMediaUrl,
                mediaType = parsedMediaType,
                upvotes = fsPost.upvotes.coerceAtLeast(1),
                hasUserUpvoted = false,
                timestamp = "Cloud Live",
                replies = emptyList()
            )
        }

        val merged = localPosts
            .filter { !deletedIds.contains(it.id) && !bannedNames.contains(it.authorName.lowercase()) }
            .map { local ->
                val matchingCloud = mappedCloud.find { cloud ->
                    cloud.title.equals(local.title, ignoreCase = true) &&
                        cloud.authorName.equals(local.authorName, ignoreCase = true)
                }
                if (matchingCloud != null) {
                    cloudPostDocIds[matchingCloud.id]?.let { docId -> cloudPostDocIds[local.id] = docId }
                    local.copy(upvotes = maxOf(local.upvotes, matchingCloud.upvotes))
                } else {
                    local
                }
            }.toMutableList()

        for (cloud in mappedCloud) {
            val exists = merged.any { local ->
                local.title.equals(cloud.title, ignoreCase = true) &&
                    local.authorName.equals(cloud.authorName, ignoreCase = true)
            }
            if (!exists) {
                merged.add(0, cloud)
            }
        }
        merged
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredDiscussionPosts: StateFlow<List<com.example.data.model.DiscussionPost>> = combine(
        allDiscussionPosts,
        _selectedDiscussionAniimoFilter,
        _selectedDiscussionCategory,
        _discussionSearchQuery
    ) { posts, aniimoFilter, category, query ->
        posts.filter { post ->
            val matchesAniimo = aniimoFilter == null || post.aniimoId.equals(aniimoFilter, ignoreCase = true)
            val matchesCategory = category == com.example.data.model.DiscussionCategory.ALL || post.category == category
            val matchesQuery = query.isBlank() ||
                post.title.contains(query, ignoreCase = true) ||
                post.content.contains(query, ignoreCase = true) ||
                post.aniimoName.contains(query, ignoreCase = true) ||
                post.authorName.contains(query, ignoreCase = true) ||
                post.recommendedHeldItem.contains(query, ignoreCase = true) ||
                post.recommendedAwakening.contains(query, ignoreCase = true) ||
                post.replies.any { it.content.contains(query, ignoreCase = true) }
            matchesAniimo && matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectDiscussionAniimoFilter(aniimoId: String?) {
        _selectedDiscussionAniimoFilter.value = aniimoId
    }

    fun selectDiscussionCategory(category: com.example.data.model.DiscussionCategory) {
        _selectedDiscussionCategory.value = category
    }

    fun setDiscussionSearchQuery(query: String) {
        _discussionSearchQuery.value = query
    }

    fun submitDiscussionPost(
        aniimo: Aniimo,
        authorName: String,
        category: com.example.data.model.DiscussionCategory,
        title: String,
        content: String,
        recommendedHeldItem: String = "",
        recommendedAwakening: String = "",
        customTopicName: String? = null,
        mediaUrl: String = "",
        mediaType: String = ""
    ) {
        if (title.isBlank() || content.isBlank()) {
            _userMessage.value = UiMessage("Please enter a title and idea description.", isError = true)
            return
        }
        findActiveBanForUser(authorName.trim())?.let { ban ->
            _userMessage.value = UiMessage(formatBanMessage(ban), isError = true)
            return
        }
        val resolvedAniimoName = if (!customTopicName.isNullOrBlank()) customTopicName.trim() else aniimo.name
        val resolvedAniimoId = if (!customTopicName.isNullOrBlank()) "custom_${resolvedAniimoName.lowercase().replace(" ", "_")}" else aniimo.id

        val resolvedMediaType = if (mediaType.isNotBlank()) mediaType else {
            val url = mediaUrl.trim().lowercase()
            when {
                url.contains("youtube.com") || url.contains("youtu.be") || url.endsWith(".mp4") || url.endsWith(".webm") -> "video"
                url.isNotBlank() -> "image"
                else -> ""
            }
        }

        val newPost = com.example.data.model.DiscussionPost(
            id = 0,
            aniimoId = resolvedAniimoId,
            aniimoName = resolvedAniimoName,
            aniimoElement = aniimo.element,
            authorName = authorName.ifBlank { "Community Explorer" },
            authorBadge = if (category == com.example.data.model.DiscussionCategory.PRISMANA_SHOWCASE) "Prismana Architect"
                else if (!customTopicName.isNullOrBlank()) "Theorycrafter (Custom)" else "Community Member",
            category = if (category == com.example.data.model.DiscussionCategory.ALL) {
                com.example.data.model.DiscussionCategory.BUILD_IDEA
            } else {
                category
            },
            title = title.trim(),
            content = content.trim(),
            recommendedHeldItem = recommendedHeldItem.trim(),
            recommendedAwakening = recommendedAwakening.trim(),
            mediaUrl = mediaUrl.trim(),
            mediaType = resolvedMediaType,
            upvotes = 1,
            hasUserUpvoted = true,
            timestamp = "Just now",
            replies = emptyList()
        )
        viewModelScope.launch {
            repository.addDiscussionPost(newPost)
            try {
                val userUid = Firebase.auth.currentUser?.uid ?: "user_${authorName.lowercase().replace(" ", "_")}"
                val encodedBody = buildString {
                    append(newPost.content)
                    append("\n\n[Held Item: ${newPost.recommendedHeldItem}] [Awakening: ${newPost.recommendedAwakening}]")
                    if (newPost.mediaUrl.isNotBlank()) {
                        append(" [MediaUrl: ${newPost.mediaUrl}] [MediaType: ${newPost.mediaType}]")
                    }
                }.take(3950)
                firestoreRepository.createDiscussionPost(
                    com.example.data.model.FirestoreDiscussionPost(
                        authorUid = userUid,
                        authorName = newPost.authorName,
                        aniimoId = newPost.aniimoId,
                        aniimoName = newPost.aniimoName,
                        title = newPost.title,
                        body = encodedBody,
                        tag = newPost.category.displayName,
                        mediaUrl = newPost.mediaUrl,
                        mediaType = newPost.mediaType,
                        upvotes = 1
                    )
                )
            } catch (_: Exception) {}
            _userMessage.value = UiMessage("Posted your $resolvedAniimoName showcase to the Discussion Board!")
        }
    }

    fun toggleDiscussionUpvote(postId: Int) {
        viewModelScope.launch {
            repository.toggleDiscussionUpvote(postId)
            try {
                val docId = cloudPostDocIds[postId]
                val currentPost = allDiscussionPosts.value.find { it.id == postId }
                if (!docId.isNullOrBlank() && currentPost != null) {
                    firestoreRepository.upvoteDiscussionPost(docId, currentPost.upvotes)
                }
            } catch (_: Exception) {}
        }
    }

    fun submitDiscussionReply(postId: Int, authorName: String, replyContent: String) {
        if (replyContent.isBlank()) {
            _userMessage.value = UiMessage("Please enter a reply comment.", isError = true)
            return
        }
        findActiveBanForUser(authorName.trim())?.let { ban ->
            _userMessage.value = UiMessage(formatBanMessage(ban), isError = true)
            return
        }
        viewModelScope.launch {
            repository.addDiscussionReply(postId, authorName, replyContent)
            try {
                val docId = cloudPostDocIds[postId]
                val uid = Firebase.auth.currentUser?.uid
                if (!docId.isNullOrBlank() && !uid.isNullOrBlank()) {
                    firestoreRepository.addComment(
                        postId = docId,
                        comment = com.example.data.model.FirestorePostComment(
                            authorUid = uid,
                            authorName = authorName.ifBlank { Firebase.auth.currentUser?.displayName ?: "Community Member" },
                            content = replyContent.trim()
                        )
                    )
                }
            } catch (_: Exception) {}
            _userMessage.value = UiMessage("Reply added to discussion!")
        }
    }


    private fun findActiveBanForUser(authorName: String): com.example.data.model.FirestoreModerationBan? {
        val currentUid = try { Firebase.auth.currentUser?.uid } catch (_: Exception) { null }
        val normalizedAuthor = authorName.trim().lowercase()
        return activeModerationBans.value.firstOrNull { ban ->
            ban.isCurrentlyActive && (
                (currentUid != null && ban.targetIdentifier.equals(currentUid, ignoreCase = true)) ||
                (normalizedAuthor.isNotBlank() && (
                    ban.targetAuthorName.equals(normalizedAuthor, ignoreCase = true) ||
                    ban.targetIdentifier.equals(normalizedAuthor, ignoreCase = true)
                ))
            )
        }
    }

    private fun formatBanMessage(ban: com.example.data.model.FirestoreModerationBan): String {
        return if (ban.actionType == "PERMABAN" || ban.expiresAtMillis == 0L) {
            "Action blocked: Account '${ban.targetAuthorName}' is permanently banned by Admin (${ban.reason})."
        } else {
            val remainingMins = ((ban.expiresAtMillis - System.currentTimeMillis()) / 60_000L).coerceAtLeast(1L)
            "Action blocked: '${ban.targetAuthorName}' is on a moderation timeout for $remainingMins more min (${ban.reason})."
        }
    }

    fun adminDeleteDiscussionPost(post: com.example.data.model.DiscussionPost) {
        _locallyDeletedPostIds.value = _locallyDeletedPostIds.value + post.id
        viewModelScope.launch {
            repository.deleteDiscussionPost(post.id)
            try {
                cloudPostDocIds[post.id]?.let { docId ->
                    firestoreRepository.deleteDiscussionPost(docId)
                }
            } catch (_: Exception) {}
            _userMessage.value = UiMessage("Admin: Removed post '${post.title}' by ${post.authorName}.")
        }
    }

    fun adminDeleteLeaderboardEntry(entry: LeaderboardEntry) {
        _locallyDeletedEntryIds.value = _locallyDeletedEntryIds.value + entry.id
        viewModelScope.launch {
            repository.deleteLeaderboardEntry(entry.id)
            try {
                cloudEntryDocIds[entry.id]?.let { docId ->
                    firestoreRepository.deleteLeaderboardEntry(docId)
                }
            } catch (_: Exception) {}
            _userMessage.value = UiMessage("Admin: Removed speedrun by ${entry.runnerName} (${entry.clearTimeDisplay}).")
        }
    }

    fun adminDeleteTopRatedTeam(team: com.example.data.model.TopRatedTeam) {
        _locallyDeletedTeamIds.value = _locallyDeletedTeamIds.value + team.id
        viewModelScope.launch {
            repository.deleteTopRatedTeam(team.id)
            try {
                firestoreRepository.deleteTeam(team.id)
            } catch (_: Exception) {}
            _userMessage.value = UiMessage("Admin: Removed 4-Aniimo team '${team.title}' by ${team.author}.")
        }
    }

    fun adminIssueTimeoutOrBan(
        targetAuthorOrUid: String,
        actionType: String, // "TIMEOUT_1H", "TIMEOUT_24H", "TIMEOUT_7D", "PERMABAN"
        reason: String = "Community guidelines violation"
    ) {
        val cleanTarget = targetAuthorOrUid.trim()
        if (cleanTarget.isBlank()) {
            _userMessage.value = UiMessage("Please enter a username or UID to timeout/ban.", isError = true)
            return
        }
        val now = System.currentTimeMillis()
        val expiresAt = when (actionType) {
            "TIMEOUT_1H" -> now + 3_600_000L
            "TIMEOUT_24H" -> now + 86_400_000L
            "TIMEOUT_7D" -> now + 7 * 86_400_000L
            else -> 0L // PERMABAN
        }
        val safeId = cleanTarget.lowercase().replace(Regex("[^a-z0-9_-]"), "_").take(64).ifBlank { "ban_${now}" }
        val ban = com.example.data.model.FirestoreModerationBan(
            id = safeId,
            targetIdentifier = cleanTarget,
            targetAuthorName = cleanTarget,
            actionType = actionType,
            reason = reason.ifBlank { "Community guidelines violation" },
            expiresAtMillis = expiresAt,
            issuedByEmail = ADMIN_EMAIL
        )
        _localModerationBans.value = _localModerationBans.value.filterNot {
            it.targetIdentifier.equals(cleanTarget, ignoreCase = true)
        } + ban

        viewModelScope.launch {
            try {
                firestoreRepository.issueModerationBan(ban)
            } catch (_: Exception) {}
            val label = when (actionType) {
                "TIMEOUT_1H" -> "1-Hour Timeout"
                "TIMEOUT_24H" -> "24-Hour Timeout"
                "TIMEOUT_7D" -> "7-Day Timeout"
                else -> "Permanent Ban"
            }
            _userMessage.value = UiMessage("Admin: Issued $label to '$cleanTarget' and hid their posts.")
        }
    }

    fun adminRevokeBan(ban: com.example.data.model.FirestoreModerationBan) {
        _localModerationBans.value = _localModerationBans.value.filterNot {
            it.id == ban.id || it.targetIdentifier.equals(ban.targetIdentifier, ignoreCase = true)
        }
        viewModelScope.launch {
            try {
                if (ban.id.isNotBlank()) {
                    firestoreRepository.revokeModerationBan(ban.id)
                }
            } catch (_: Exception) {}
            _userMessage.value = UiMessage("Admin: Lifted timeout/ban for '${ban.targetAuthorName}'.")
        }
    }

    fun toggleAdminModeOverride(enabled: Boolean) {
        _adminOverrideEnabled.value = enabled
        val status = if (enabled) "Admin Moderator Controls ENABLED (Override On)" else "Admin Moderator override disabled"
        _userMessage.value = UiMessage(status)
    }

    fun adminPublishLiveAppConfig(
        latestVersionName: String,
        githubRepoSlug: String,
        directApkUrl: String,
        liveAnnouncement: String,
        triggerRemoteDataClean: Boolean = false
    ) {
        val current = liveAppConfig.value
        val nextEpoch = if (triggerRemoteDataClean) current.remoteResetEpoch + 1 else current.remoteResetEpoch
        val cleanSlug = githubRepoSlug.trim().removePrefix("https://github.com/").removeSuffix("/")
        val resolvedApkUrl = directApkUrl.trim().ifBlank {
            if (cleanSlug.contains("/")) {
                "https://github.com/$cleanSlug/releases/latest/download/Aniimo-DPS-v1.0.apk"
            } else {
                ""
            }
        }
        val updated = com.example.data.model.FirestoreAppConfig(
            id = "live_release",
            latestVersionName = latestVersionName.trim().ifBlank { "1.0" },
            githubRepoSlug = cleanSlug,
            directApkUrl = resolvedApkUrl,
            liveAnnouncement = liveAnnouncement.trim(),
            remoteResetEpoch = nextEpoch,
            updatedByEmail = ADMIN_EMAIL
        )
        _localAppConfig.value = updated
        viewModelScope.launch {
            if (triggerRemoteDataClean) {
                repository.clearAllCommunityPostsAndVotes()
            }
            try {
                firestoreRepository.publishAppConfig(updated)
            } catch (_: Exception) {}
            _userMessage.value = UiMessage(
                if (triggerRemoteDataClean) {
                    "Admin: Pushed Over-The-Air clean & live update to all connected apps!"
                } else {
                    "Admin: Published Over-The-Air live app config (v${updated.latestVersionName})!"
                }
            )
        }
    }
}

