package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.Aniimo
import com.example.ui.components.SubmitRunDialog
import com.example.ui.components.VideoProofDialog
import com.example.ui.navigation.Screen
import com.example.ui.screens.AniimoDetailScreen
import com.example.ui.screens.DiscussionBoardScreen
import com.example.ui.screens.DpsCalculatorScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.RosterScreen
import com.example.ui.screens.SavedBuildsScreen
import com.example.ui.screens.SparkleShowcaseScreen
import com.example.ui.screens.TopTeamsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.data.util.configureAppCheck
import com.example.ui.screens.AuthGateScreen
import com.example.ui.screens.PatchNotesScreen
import com.example.ui.screens.SettingsScreen
import com.example.viewmodel.AniimoViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure App Check with launch intent before any UI or Firebase operations
        configureAppCheck(this, intent)
        enableEdgeToEdge()
        setContent {
            AniimoApp()
        }
    }
}

@Composable
fun AniimoApp() {
    var currentUser by remember { mutableStateOf(Firebase.auth.currentUser) }
    var currentBgColorArgb by rememberSaveable { mutableIntStateOf(0xFF0A0F1D.toInt()) }
    var currentTextColorArgb by rememberSaveable { mutableIntStateOf(0xFFFFFFFF.toInt()) }
    var currentFontName by rememberSaveable { mutableStateOf("Default") }

    val currentBgColor = remember(currentBgColorArgb) { Color(currentBgColorArgb) }
    val currentTextColor = remember(currentTextColorArgb) { Color(currentTextColorArgb) }
    val currentFontFamily = remember(currentFontName) {
        when (currentFontName) {
            "Monospace" -> FontFamily.Monospace
            "Serif" -> FontFamily.Serif
            "Cursive" -> FontFamily.Cursive
            else -> FontFamily.Default
        }
    }

    MyApplicationTheme(
        customBackgroundColor = currentBgColor,
        customTextColor = currentTextColor,
        customFontFamily = currentFontFamily
    ) {
        DisposableEffect(Unit) {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                currentUser = auth.currentUser
            }
            Firebase.auth.addAuthStateListener(listener)
            onDispose {
                Firebase.auth.removeAuthStateListener(listener)
            }
        }

        if (currentUser == null) {
            AuthGateScreen(
                onAuthSuccess = {
                    currentUser = Firebase.auth.currentUser
                }
            )
        } else {
            AniimoMainScaffold(
                userId = currentUser!!.uid,
                viewModel = viewModel(),
                currentBgColor = currentBgColor,
                currentTextColor = currentTextColor,
                currentFontName = currentFontName,
                onSelectBackgroundColor = { color -> currentBgColorArgb = color.toArgb() },
                onSelectTextColor = { color -> currentTextColorArgb = color.toArgb() },
                onSelectFontName = { name -> currentFontName = name },
                onRestoreDefaultTheme = {
                    currentBgColorArgb = 0xFF0A0F1D.toInt()
                    currentTextColorArgb = 0xFFFFFFFF.toInt()
                    currentFontName = "Default"
                }
            )
        }
    }
}

@Composable
fun AniimoMainScaffold(
    userId: String,
    viewModel: AniimoViewModel,
    currentBgColor: Color,
    currentTextColor: Color,
    currentFontName: String,
    onSelectBackgroundColor: (Color) -> Unit,
    onSelectTextColor: (Color) -> Unit,
    onSelectFontName: (String) -> Unit,
    onRestoreDefaultTheme: () -> Unit
) {
    var currentScreen by remember { mutableStateOf(Screen.ROSTER) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Collect Viewmodel States
    val filteredAniimos by viewModel.filteredAniimos.collectAsStateWithLifecycle()
    val selectedElementFilter by viewModel.selectedElementFilter.collectAsStateWithLifecycle()
    val selectedStageFilter by viewModel.selectedStageFilter.collectAsStateWithLifecycle()
    val selectedSortOption by viewModel.selectedSortOption.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedAniimo by viewModel.selectedAniimo.collectAsStateWithLifecycle()

    val bosses = viewModel.bosses
    val selectedBossFilter by viewModel.selectedBossFilter.collectAsStateWithLifecycle()
    val leaderboardSort by viewModel.leaderboardSort.collectAsStateWithLifecycle()
    val showOnlyLegitEntries by viewModel.showOnlyLegitEntries.collectAsStateWithLifecycle()
    val leaderboardEntries by viewModel.leaderboardEntries.collectAsStateWithLifecycle()

    val activeProofEntry by viewModel.activeProofEntry.collectAsStateWithLifecycle()
    val isSubmitRunDialogOpen by viewModel.isSubmitRunDialogOpen.collectAsStateWithLifecycle()
    val submitRunPrefill by viewModel.submitRunPrefill.collectAsStateWithLifecycle()
    val customBuilds by viewModel.customBuilds.collectAsStateWithLifecycle()

    val simMainAniimo by viewModel.simMainAniimo.collectAsStateWithLifecycle()
    val simPartnerAniimo by viewModel.simPartnerAniimo.collectAsStateWithLifecycle()
    val simSlot3Aniimo by viewModel.simSlot3Aniimo.collectAsStateWithLifecycle()
    val simSlot4Aniimo by viewModel.simSlot4Aniimo.collectAsStateWithLifecycle()
    val simMainHeldItem by viewModel.simMainHeldItem.collectAsStateWithLifecycle()
    val simPartnerHeldItem by viewModel.simPartnerHeldItem.collectAsStateWithLifecycle()
    val simSlot3HeldItem by viewModel.simSlot3HeldItem.collectAsStateWithLifecycle()
    val simSlot4HeldItem by viewModel.simSlot4HeldItem.collectAsStateWithLifecycle()
    val simItemEnhanceLevel by viewModel.simItemEnhanceLevel.collectAsStateWithLifecycle()
    val simAwakeningAtk by viewModel.simAwakeningAtk.collectAsStateWithLifecycle()
    val simAwakeningBreak by viewModel.simAwakeningBreak.collectAsStateWithLifecycle()
    val simAwakeningRegen by viewModel.simAwakeningRegen.collectAsStateWithLifecycle()
    val simAwakeningHp by viewModel.simAwakeningHp.collectAsStateWithLifecycle()
    val simAwakeningPdef by viewModel.simAwakeningPdef.collectAsStateWithLifecycle()
    val simAwakeningMdef by viewModel.simAwakeningMdef.collectAsStateWithLifecycle()
    val calculatedDpsResult by viewModel.calculatedDpsResult.collectAsStateWithLifecycle()

    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()
    val communityVotes by viewModel.communityVotes.collectAsStateWithLifecycle()
    val selectedRegionFilter by viewModel.selectedRegionFilter.collectAsStateWithLifecycle()

    val selectedTeamModeFilter by viewModel.selectedTeamModeFilter.collectAsStateWithLifecycle()
    val teamSearchQuery by viewModel.teamSearchQuery.collectAsStateWithLifecycle()
    val selectedTeamAniimoFilter by viewModel.selectedTeamAniimoFilter.collectAsStateWithLifecycle()
    val filteredTopRatedTeams by viewModel.filteredTopRatedTeams.collectAsStateWithLifecycle()
    val allTopRatedTeams by viewModel.allTopRatedTeams.collectAsStateWithLifecycle()

    val allDiscussionPosts by viewModel.allDiscussionPosts.collectAsStateWithLifecycle()
    val filteredDiscussionPosts by viewModel.filteredDiscussionPosts.collectAsStateWithLifecycle()
    val selectedDiscussionAniimoFilter by viewModel.selectedDiscussionAniimoFilter.collectAsStateWithLifecycle()
    val selectedDiscussionCategory by viewModel.selectedDiscussionCategory.collectAsStateWithLifecycle()
    val discussionSearchQuery by viewModel.discussionSearchQuery.collectAsStateWithLifecycle()

    // Handle Snackbars
    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg.text,
                duration = SnackbarDuration.Short
            )
            viewModel.clearUserMessage()
        }
    }

    val bottomNavItems = listOf(
        Screen.ROSTER,
        Screen.TOP_TEAMS,
        Screen.DPS_CALCULATOR,
        Screen.DISCUSSION,
        Screen.LEADERBOARD,
        Screen.SETTINGS
    )

    val cloudPatchNotes by viewModel.cloudPatchNotes.collectAsStateWithLifecycle()
    val activeSkillChanges by viewModel.activeSkillChanges.collectAsStateWithLifecycle()
    val activeStatChanges by viewModel.activeStatChanges.collectAsStateWithLifecycle()
    val isParsingWithAi by viewModel.isParsingWithAi.collectAsStateWithLifecycle()
    val isAdminUser by viewModel.isAdminUser.collectAsStateWithLifecycle()
    val currentUserEmail by viewModel.currentUserEmail.collectAsStateWithLifecycle()
    val activeModerationBans by viewModel.activeModerationBans.collectAsStateWithLifecycle()
    val liveAppConfig by viewModel.liveAppConfig.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = currentBgColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0F172A),
                contentColor = Color(0xFF94A3B8),
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar")
            ) {
                bottomNavItems.forEach { screen ->
                    val isSelected = currentScreen == screen || (currentScreen == Screen.ANIIMO_DETAIL && screen == Screen.ROSTER)
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentScreen = screen },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF0F172A),
                            selectedTextColor = Color(0xFF38BDF8),
                            indicatorColor = Color(0xFF38BDF8),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8)
                        ),
                        modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.ROSTER -> {
                    RosterScreen(
                        aniimos = filteredAniimos,
                        selectedElement = selectedElementFilter,
                        selectedStageFilter = selectedStageFilter,
                        selectedRegionFilter = selectedRegionFilter,
                        selectedSortOption = selectedSortOption,
                        searchQuery = searchQuery,
                        communityVotes = communityVotes,
                        onSelectElement = { viewModel.selectElementFilter(it) },
                        onSelectStageFilter = { viewModel.selectStageFilter(it) },
                        onSelectRegionFilter = { viewModel.selectRegionFilter(it) },
                        onSelectSortOption = { viewModel.selectSortOption(it) },
                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                        onAniimoClick = { aniimo ->
                            viewModel.selectAniimo(aniimo)
                            currentScreen = Screen.ANIIMO_DETAIL
                        }
                    )
                }
                Screen.TOP_TEAMS -> {
                    TopTeamsScreen(
                        teams = filteredTopRatedTeams,
                        aniimos = viewModel.aniimos,
                        selectedMode = selectedTeamModeFilter,
                        selectedAniimoFilter = selectedTeamAniimoFilter,
                        searchQuery = teamSearchQuery,
                        onSelectMode = { viewModel.selectTeamModeFilter(it) },
                        onSelectAniimoFilter = { viewModel.selectTeamAniimoFilter(it) },
                        onSearchQueryChange = { viewModel.setTeamSearchQuery(it) },
                        onToggleUpvote = { teamId, currentHasUpvoted ->
                            viewModel.toggleTeamUpvote(teamId, currentHasUpvoted)
                        },
                        onSimulateTeam = { main, partnerId ->
                            viewModel.setSimMainAniimo(main)
                            partnerId?.let { pId ->
                                viewModel.aniimos.find { it.id == pId }?.let { p ->
                                    viewModel.setSimPartnerAniimo(p)
                                }
                            }
                            currentScreen = Screen.DPS_CALCULATOR
                        },
                        onPublishTeam = { newTeam ->
                            viewModel.submitCustomTeam(newTeam)
                        },
                        onShowMessage = { msg ->
                            viewModel.showNotice(msg)
                        },
                        isAdmin = isAdminUser,
                        onAdminDeleteTeam = { team ->
                            viewModel.adminDeleteTopRatedTeam(team)
                        },
                        onAdminTimeoutOrBanAuthor = { authorName, actionType ->
                            viewModel.adminIssueTimeoutOrBan(authorName, actionType, "Inappropriate team build submission")
                        }
                    )
                }
                Screen.SPARKLES -> {
                    RosterScreen(
                        aniimos = filteredAniimos,
                        selectedElement = selectedElementFilter,
                        selectedStageFilter = selectedStageFilter,
                        selectedRegionFilter = selectedRegionFilter,
                        selectedSortOption = selectedSortOption,
                        searchQuery = searchQuery,
                        communityVotes = communityVotes,
                        onSelectElement = { viewModel.selectElementFilter(it) },
                        onSelectStageFilter = { viewModel.selectStageFilter(it) },
                        onSelectRegionFilter = { viewModel.selectRegionFilter(it) },
                        onSelectSortOption = { viewModel.selectSortOption(it) },
                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                        onAniimoClick = { aniimo ->
                            viewModel.selectAniimo(aniimo)
                            currentScreen = Screen.ANIIMO_DETAIL
                        }
                    )
                }
                Screen.ANIIMO_DETAIL -> {
                    AniimoDetailScreen(
                        aniimo = selectedAniimo,
                        allAniimos = viewModel.aniimos,
                        communityVote = communityVotes[selectedAniimo.id],
                        topRatedTeams = allTopRatedTeams,
                        onToggleTeamUpvote = { teamId, currentHasUpvoted ->
                            viewModel.toggleTeamUpvote(teamId, currentHasUpvoted)
                        },
                        onVoteTier = { tier ->
                            viewModel.voteForAniimoTier(selectedAniimo.id, tier)
                        },
                        discussionPosts = allDiscussionPosts,
                        onSubmitDiscussionPost = { author, category, title, content, item, awakening ->
                            viewModel.submitDiscussionPost(
                                aniimo = selectedAniimo,
                                authorName = author,
                                category = category,
                                title = title,
                                content = content,
                                recommendedHeldItem = item,
                                recommendedAwakening = awakening
                            )
                        },
                        onToggleDiscussionUpvote = { postId ->
                            viewModel.toggleDiscussionUpvote(postId)
                        },
                        onSubmitDiscussionReply = { postId, author, replyText ->
                            viewModel.submitDiscussionReply(postId, author, replyText)
                        },
                        onBack = { currentScreen = Screen.ROSTER },
                        onSelectAniimo = { aniimo ->
                            viewModel.selectAniimo(aniimo)
                        },
                        onNavigateToSimulator = { main, partnerId ->
                            viewModel.setSimMainAniimo(main)
                            partnerId?.let { pId ->
                                viewModel.aniimos.find { it.id == pId }?.let { p ->
                                    viewModel.setSimPartnerAniimo(p)
                                }
                            }
                            currentScreen = Screen.DPS_CALCULATOR
                        },
                        onPublishBuild = { newTeam ->
                            viewModel.submitCustomTeam(newTeam)
                        }
                    )
                }
                Screen.DPS_CALCULATOR -> {
                    DpsCalculatorScreen(
                        aniimos = viewModel.aniimos,
                        mainAniimo = simMainAniimo,
                        partnerAniimo = simPartnerAniimo,
                        slot3Aniimo = simSlot3Aniimo,
                        slot4Aniimo = simSlot4Aniimo,
                        mainHeldItem = simMainHeldItem,
                        partnerHeldItem = simPartnerHeldItem,
                        slot3HeldItem = simSlot3HeldItem,
                        slot4HeldItem = simSlot4HeldItem,
                        itemEnhanceLevel = simItemEnhanceLevel,
                        awakeningAtk = simAwakeningAtk,
                        awakeningBreak = simAwakeningBreak,
                        awakeningRegen = simAwakeningRegen,
                        awakeningHp = simAwakeningHp,
                        awakeningPdef = simAwakeningPdef,
                        awakeningMdef = simAwakeningMdef,
                        calcResult = calculatedDpsResult,
                        onSelectMain = { viewModel.setSimMainAniimo(it) },
                        onSelectPartner = { viewModel.setSimPartnerAniimo(it) },
                        onSelectSlot3 = { viewModel.setSimSlot3Aniimo(it) },
                        onSelectSlot4 = { viewModel.setSimSlot4Aniimo(it) },
                        onSelectMainHeldItem = { viewModel.setSimMainHeldItem(it) },
                        onSelectPartnerHeldItem = { viewModel.setSimPartnerHeldItem(it) },
                        onSelectSlot3HeldItem = { viewModel.setSimSlot3HeldItem(it) },
                        onSelectSlot4HeldItem = { viewModel.setSimSlot4HeldItem(it) },
                        onItemEnhanceLevelChanged = { viewModel.setSimItemEnhanceLevel(it) },
                        onAwakeningStatsChanged = { atk, brk, reg, hp, pdef, mdef ->
                            viewModel.setAwakeningStats(atk, brk, reg, hp, pdef, mdef)
                        },
                        onSkillLevelsChanged = { s1, s2, ult -> viewModel.setSkillLevels(s1, s2, ult) },
                        onGearBonusChanged = { w, r -> viewModel.setGearBonuses(w, r) },
                        onSaveBuild = { title -> viewModel.saveCurrentSimulation(title) },
                        savedBuilds = customBuilds,
                        onDeleteBuild = { viewModel.deleteCustomBuild(it) },
                        onLoadBuild = { build ->
                            viewModel.aniimos.find { it.id == build.aniimoId }?.let { main ->
                                viewModel.setSimMainAniimo(main)
                            }
                            viewModel.aniimos.find { it.id == build.partnerId }?.let { partner ->
                                viewModel.setSimPartnerAniimo(partner)
                            }
                            viewModel.setSkillLevels(build.skill1Level, build.skill2Level, build.ultimateLevel)
                        },
                        onPublishBuildToCommunity = { newTeam ->
                            viewModel.submitCustomTeam(newTeam)
                        },
                        onPostBuildToDiscussion = { aniimo, author, category, title, content, item, awakening ->
                            viewModel.submitDiscussionPost(
                                aniimo = aniimo,
                                authorName = author,
                                category = category,
                                title = title,
                                content = content,
                                recommendedHeldItem = item,
                                recommendedAwakening = awakening
                            )
                        },
                        onImportBuildCode = { p ->
                            viewModel.importBuildPayload(
                                mainId = p.mainId,
                                mainItem = p.mainItem,
                                partnerId = p.partnerId,
                                partnerItem = p.partnerItem,
                                slot3Id = p.slot3Id,
                                slot3Item = p.slot3Item,
                                slot4Id = p.slot4Id,
                                slot4Item = p.slot4Item,
                                atk = p.atk,
                                breakStat = p.breakStat,
                                regen = p.regen,
                                hp = p.hp,
                                pdef = p.pdef,
                                mdef = p.mdef
                            )
                        },
                        onShowMessage = { msg ->
                            viewModel.showNotice(msg)
                        }
                    )
                }
                Screen.DISCUSSION -> {
                    DiscussionBoardScreen(
                        posts = filteredDiscussionPosts,
                        aniimos = viewModel.aniimos,
                        selectedAniimoFilter = selectedDiscussionAniimoFilter,
                        selectedCategory = selectedDiscussionCategory,
                        searchQuery = discussionSearchQuery,
                        onSelectAniimoFilter = { viewModel.selectDiscussionAniimoFilter(it) },
                        onSelectCategory = { viewModel.selectDiscussionCategory(it) },
                        onSearchQueryChange = { viewModel.setDiscussionSearchQuery(it) },
                        onSubmitPost = { aniimo, author, category, title, content, item, awakening, customTopic, mediaUrl, mediaType ->
                            viewModel.submitDiscussionPost(
                                aniimo = aniimo,
                                authorName = author,
                                category = category,
                                title = title,
                                content = content,
                                recommendedHeldItem = item,
                                recommendedAwakening = awakening,
                                customTopicName = customTopic,
                                mediaUrl = mediaUrl,
                                mediaType = mediaType
                            )
                        },
                        onToggleUpvote = { postId ->
                            viewModel.toggleDiscussionUpvote(postId)
                        },
                        onSubmitReply = { postId, author, replyText ->
                            viewModel.submitDiscussionReply(postId, author, replyText)
                        },
                        onNavigateToAniimoDetail = { aniimo ->
                            viewModel.selectAniimo(aniimo)
                            currentScreen = Screen.ANIIMO_DETAIL
                        },
                        isAdmin = isAdminUser,
                        onAdminDeletePost = { post ->
                            viewModel.adminDeleteDiscussionPost(post)
                        },
                        onAdminTimeoutOrBanAuthor = { authorName, actionType ->
                            viewModel.adminIssueTimeoutOrBan(authorName, actionType, "Unwanted / rule-breaking discussion post")
                        }
                    )
                }
                Screen.LEADERBOARD -> {
                    LeaderboardScreen(
                        bosses = bosses,
                        selectedBossId = selectedBossFilter,
                        entries = leaderboardEntries,
                        currentSort = leaderboardSort,
                        onSelectBoss = { viewModel.selectBossFilter(it) },
                        onSelectSort = { viewModel.setLeaderboardSort(it) },
                        onWatchProof = { entry -> viewModel.openProofDialog(entry) },
                        onUpvote = { entry -> viewModel.toggleUpvote(entry) },
                        onSubmitClick = { viewModel.openSubmitRunDialog() },
                        showOnlyLegitEntries = showOnlyLegitEntries,
                        onToggleShowOnlyLegit = { viewModel.setShowOnlyLegitEntries(it) },
                        onVoteLegit = { entry, isLegit -> viewModel.voteLeaderboardLegitimacy(entry, isLegit) },
                        isAdmin = isAdminUser,
                        onAdminDeleteEntry = { entry ->
                            viewModel.adminDeleteLeaderboardEntry(entry)
                        },
                        onAdminTimeoutOrBanRunner = { runnerName, actionType ->
                            viewModel.adminIssueTimeoutOrBan(runnerName, actionType, "Invalid / fake speedrun submission")
                        }
                    )
                }
                Screen.SAVED_BUILDS -> {
                    SavedBuildsScreen(
                        builds = customBuilds,
                        onDeleteBuild = { viewModel.deleteCustomBuild(it) },
                        onLoadIntoSimulator = { build ->
                            viewModel.aniimos.find { it.id == build.aniimoId }?.let { main ->
                                viewModel.setSimMainAniimo(main)
                            }
                            viewModel.aniimos.find { it.id == build.partnerId }?.let { partner ->
                                viewModel.setSimPartnerAniimo(partner)
                            }
                            viewModel.setSkillLevels(build.skill1Level, build.skill2Level, build.ultimateLevel)
                            currentScreen = Screen.DPS_CALCULATOR
                        },
                        onGoToSimulator = { currentScreen = Screen.DPS_CALCULATOR }
                    )
                }
                Screen.PATCH_NOTES,
                Screen.SETTINGS -> {
                    SettingsScreen(
                        currentBackgroundColor = currentBgColor,
                        currentTextColor = currentTextColor,
                        currentFontName = currentFontName,
                        onSelectBackgroundColor = onSelectBackgroundColor,
                        onSelectTextColor = onSelectTextColor,
                        onSelectFontName = onSelectFontName,
                        onRestoreDefaultTheme = onRestoreDefaultTheme,
                        patchNotes = cloudPatchNotes,
                        activeSkillChanges = activeSkillChanges,
                        activeStatChanges = activeStatChanges,
                        isParsingWithAi = isParsingWithAi,
                        onSyncOfficialNotes = { url -> viewModel.syncOfficialPatchNotesUrl(url) },
                        onParseRawText = { text -> viewModel.parseRawPatchNotesText(text) },
                        onShowMessage = { msg -> viewModel.showNotice(msg) },
                        isAdmin = isAdminUser,
                        currentUserEmail = currentUserEmail,
                        activeBans = activeModerationBans,
                        onToggleAdminOverride = { enabled -> viewModel.toggleAdminModeOverride(enabled) },
                        onIssueTimeoutOrBan = { username, actionType, reason ->
                            viewModel.adminIssueTimeoutOrBan(username, actionType, reason)
                        },
                        onRevokeBan = { ban -> viewModel.adminRevokeBan(ban) },
                        liveAppConfig = liveAppConfig,
                        onPublishLiveAppConfig = { latestVer, repoSlug, apkUrl, announcement, triggerClean ->
                            viewModel.adminPublishLiveAppConfig(
                                latestVersionName = latestVer,
                                githubRepoSlug = repoSlug,
                                directApkUrl = apkUrl,
                                liveAnnouncement = announcement,
                                triggerRemoteDataClean = triggerClean
                            )
                        }
                    )
                }
            }
        }
    }

    // Active Proof Modal Dialog
    activeProofEntry?.let { entry ->
        VideoProofDialog(
            entry = entry,
            onDismiss = { viewModel.closeProofDialog() },
            onVoteLegit = { e, isLegit -> viewModel.voteLeaderboardLegitimacy(e, isLegit) }
        )
    }

    // Submit Run Modal Dialog
    if (isSubmitRunDialogOpen) {
        SubmitRunDialog(
            bosses = bosses,
            aniimos = viewModel.aniimos,
            initialMainAniimoId = submitRunPrefill.mainAniimoId,
            initialPartnerAniimoId = submitRunPrefill.partnerAniimoId,
            initialSlot3AniimoId = submitRunPrefill.slot3AniimoId,
            initialSlot4AniimoId = submitRunPrefill.slot4AniimoId,
            initialWeaponName = submitRunPrefill.weaponName,
            initialRelicSetName = submitRunPrefill.relicSetName,
            onDismiss = { viewModel.closeSubmitRunDialog() },
            onSubmit = { runner, bossId, mainId, partnerId, slot3Id, slot4Id, min, sec, ms, url, wpn, relic, notes, clipSec, customBoss, customMain, customPartner, customSlot3, customSlot4 ->
                viewModel.submitClearRun(
                    runnerName = runner,
                    bossId = bossId,
                    mainAniimoId = mainId,
                    partnerAniimoId = partnerId,
                    slot3AniimoId = slot3Id,
                    slot4AniimoId = slot4Id,
                    minutes = min,
                    seconds = sec,
                    millis = ms,
                    videoUrl = url,
                    weaponName = wpn,
                    relicSetName = relic,
                    notes = notes,
                    videoDurationSeconds = clipSec,
                    customBossName = customBoss,
                    customMainAniimoName = customMain,
                    customPartnerAniimoName = customPartner,
                    customSlot3AniimoName = customSlot3,
                    customSlot4AniimoName = customSlot4
                )
            }
        )
    }
}
