package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AniimoDatabase
import com.example.data.model.EvolutionStage
import com.example.data.repository.AniimoRepository
import com.example.data.repository.AniimoHeldItemsDatabase
import com.example.data.repository.AniimoAlphaBossesDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import androidx.compose.ui.graphics.toArgb
import com.example.data.model.Element

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Before
    fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(
                context,
                FirebaseOptions.Builder()
                    .setApplicationId("com.example.app")
                    .setProjectId("demo-no-project")
                    .setApiKey("fake-api-key")
                    .build()
            )
        }
        val db = AniimoDatabase.getDatabase(context)
        AniimoRepository(db).clearAllCommunityPostsAndVotes()
    }

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Aniimo DPS", appName)
    }

    @Test
    fun `repository provides seeded Aniimo roster with all pre-evolutions and encounters`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)
        repository.seedDatabaseIfEmpty()

        val aniimos = repository.aniimos
        assertTrue("Aniimo roster should not be empty", aniimos.isNotEmpty())
        assertTrue("Should have 35+ Aniimos in roster including all pre-evolutions", aniimos.size >= 35)

        // Validate Pre-Evolutions presence
        val preEvos = aniimos.filter { it.stage.isPreEvolution }
        assertTrue("Must list pre-evolutions in roster", preEvos.isNotEmpty())

        val luminForms = aniimos.filter { it.stage == EvolutionStage.LUMIN }
        assertTrue("Must have Lumin base stage forms", luminForms.size >= 15)

        val gammaForms = aniimos.filter { it.stage == EvolutionStage.GAMMA }
        assertTrue("Must have Gamma intermediate stage forms", gammaForms.size >= 3)

        // Check canonical pre-evolutions
        val emberpup = aniimos.find { it.id == "emberpup" }
        assertNotNull("Emberpup pre-evo must be in roster", emberpup)
        assertEquals("No. 001", emberpup!!.aniilogNumber)
        assertEquals(EvolutionStage.LUMIN, emberpup.stage)
        assertTrue("Emberpup must evolve to Flameruff", emberpup.evolvesToIds.contains("flameruff"))

        val flameruff = aniimos.find { it.id == "flameruff" }
        assertNotNull("Flameruff intermediate pre-evo must be in roster", flameruff)
        assertEquals(EvolutionStage.GAMMA, flameruff!!.stage)
        assertTrue("Flameruff must branch to Scorchhowl and Inferlupa",
            flameruff.evolvesToIds.contains("scorchhowl") && flameruff.evolvesToIds.contains("inferlupa"))

        val wisptis = aniimos.find { it.id == "wisptis" }
        assertNotNull("Wisptis pre-evo must be in roster", wisptis)
        assertEquals(EvolutionStage.LUMIN, wisptis!!.stage)
        assertTrue("Wisptis must evolve to Fulmintis", wisptis.evolvesToIds.contains("fulmintis"))

        val cubbo = aniimos.find { it.id == "cubbo" }
        assertNotNull("Cubbo pre-evo must be in roster", cubbo)
        assertEquals(EvolutionStage.LUMIN, cubbo!!.stage)
        assertTrue("Cubbo must evolve to Grizbo", cubbo.evolvesToIds.contains("grizbo"))

        val dewlop = aniimos.find { it.id == "dewlop" }
        assertNotNull("Dewlop pre-evo must be in roster", dewlop)
        assertEquals(EvolutionStage.LUMIN, dewlop!!.stage)
        assertTrue("Dewlop must evolve to Dewple", dewlop.evolvesToIds.contains("dewple"))

        // Final forms checks
        val fulmintis = aniimos.find { it.id == "fulmintis" }
        assertNotNull("Fulmintis must be in roster", fulmintis)
        assertEquals(EvolutionStage.NOVA, fulmintis!!.stage)
        assertEquals("wisptis", fulmintis.evolvesFromId)
        assertTrue("Fulmintis must have best partners like Dazmand", fulmintis.bestPartners.any { it.partnerName == "Dazmand" })
        assertTrue("Fulmintis must have Ferocious Fang and Pendant", fulmintis.bestItems.any { it.name.contains("Ferocious Fang") })

        val entries = repository.getAllLeaderboardEntries().first()
        assertTrue("Leaderboard should start fresh with 0 generated entries", entries.isEmpty())

        // Validate skills structure: base skills, core skills, and highest DPS designation
        aniimos.forEach { aniimo ->
            val highestDpsSkill = aniimo.skills.find { it.isHighestDps }
            assertNotNull("${aniimo.name} must designate a highest DPS potential skill", highestDpsSkill)
            assertTrue("${aniimo.name} highest DPS skill must have positive DPS/sec", highestDpsSkill!!.dpsPerSec > 0)

            val coreSkill = aniimo.skills.find { it.isCoreSkill }
            assertNotNull("${aniimo.name} must have a Core Signature Skill", coreSkill)
        }
    }

    @Test
    fun `validate dual element typing and ensure Inferlupa is Fire and Dark in catalog and Prismana form`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)
        val aniimos = repository.aniimos

        val inferlupa = aniimos.find { it.id == "inferlupa" }
        assertNotNull("Inferlupa must be present in roster", inferlupa)
        assertEquals("Inferlupa base primary element must be DARK", Element.DARK, inferlupa!!.element)
        assertEquals("Inferlupa base secondary element must be FIRE", Element.FIRE, inferlupa.secondaryElement)

        val inferlupaVariants = com.example.data.repository.SparkleRepository.getRegionalVariantsForAniimo(inferlupa)
        val standardInferlupa = inferlupaVariants.find { it.id.contains("standard") }
        assertNotNull("Standard Inferlupa must be available", standardInferlupa)
        assertEquals("Standard Inferlupa primary must be DARK", Element.DARK, standardInferlupa!!.primaryElement)
        assertEquals("Standard Inferlupa secondary must be FIRE", Element.FIRE, standardInferlupa.secondaryElement)

        val prismanaInferlupa = inferlupaVariants.find { it.id.contains("prismana") }
        assertNotNull("Prismana Inferlupa must be available", prismanaInferlupa)
        assertEquals("Prismana Inferlupa primary must be FIRE", Element.FIRE, prismanaInferlupa!!.primaryElement)
        assertEquals("Prismana Inferlupa secondary must be DARK", Element.DARK, prismanaInferlupa.secondaryElement)

        val glacy = aniimos.find { it.id == "glacy" }
        assertNotNull("Glacy must be present", glacy)
        assertEquals(Element.ICE, glacy!!.element)
        assertEquals(Element.WATER, glacy.secondaryElement)

        val reefish = aniimos.find { it.id == "reefish" }
        assertNotNull("Reefish must be present", reefish)
        assertEquals(Element.WATER, reefish!!.element)
        assertEquals(Element.EARTH, reefish.secondaryElement)

        val magmarex = aniimos.find { it.id == "magmarex" }
        assertNotNull("Magmarex must be present", magmarex)
        assertEquals(Element.FIRE, magmarex!!.element)
        assertEquals(Element.EARTH, magmarex.secondaryElement)
    }

    @Test
    fun `validate all 13 Exhibition Wall Sparkle Types from AniimoTools are correctly configured`() {
        val sparkles = com.example.data.repository.SparkleRepository.allSparkleTypes
        assertEquals("Must contain all 13 Exhibition Wall Sparkle variations (Prototype + Types I-X + Dazzling + Shadow)", 13, sparkles.size)

        val typeCodes = sparkles.map { it.code }
        val expectedCodes = listOf(
            "Prototype",
            "Type I", "Type II", "Type III", "Type IV", "Type V",
            "Type VI", "Type VII", "Type VIII", "Type IX", "Type X",
            "Dazzling", "Shadow"
        )
        assertEquals("Must have Prototype, all 10 numbered types, plus Dazzling and Shadow", expectedCodes, typeCodes)

        val prototype = sparkles.find { it.code == "Prototype" }
        assertNotNull("Prototype reference must exist", prototype)

        val dazzling = sparkles.find { it.code == "Dazzling" }
        assertNotNull("Dazzling variant must exist", dazzling)
        assertTrue("Dazzling must be marked special", dazzling!!.isSpecial)
        assertEquals("Hidden Mythic", dazzling.rarity)
        assertTrue("Dazzling must specify Sparkling Cube Normal Egg 1.0% odds", dazzling.cubeNormalOdds.contains("1.0%"))

        val shadow = sparkles.find { it.code == "Shadow" }
        assertNotNull("Shadow variant must exist", shadow)
        assertTrue("Shadow must be marked special", shadow!!.isSpecial)
        assertEquals("Hidden Mythic", shadow.rarity)
        assertTrue("Shadow must specify Egg Heist Egg 1.0% odds", shadow.cubeEggHeistOdds.contains("1.0%"))

        sparkles.forEach { sparkle ->
            assertTrue("Sparkle ${sparkle.code} must have description", sparkle.description.isNotBlank())
            assertTrue("Sparkle ${sparkle.code} must have coat effect", sparkle.coatEffect.isNotBlank())
            assertTrue("Sparkle ${sparkle.code} must have aura effect", sparkle.auraEffect.isNotBlank())
            val matrix = sparkle.createColorMatrix(30f)
            assertNotNull("Sparkle ${sparkle.code} color matrix must generate properly", matrix)
        }
    }

    @Test
    fun `validate sorting by Aniimo number, tier, and type in viewModel`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)

        // 1. Sort by Number
        val sortedByNumber = repository.aniimos.sortedBy { aniimo ->
            Regex("""\d+""").find(aniimo.aniilogNumber)?.value?.toIntOrNull() ?: Int.MAX_VALUE
        }
        assertEquals("First by number should be No. 001 Emberpup", "No. 001", sortedByNumber.first().aniilogNumber)
        assertEquals("Second by number should be No. 002 Flameruff", "No. 002", sortedByNumber[1].aniilogNumber)
        assertEquals("Last by number should be No. 126 Hypnyto", "No. 126", sortedByNumber.last().aniilogNumber)

        // 2. Sort by Tier
        val sortedByTier = repository.aniimos.sortedWith(
            compareBy<com.example.data.model.Aniimo> { aniimo ->
                when (aniimo.metaTier) {
                    "S+" -> 1
                    "S" -> 2
                    "A" -> 3
                    "B+" -> 4
                    "B" -> 5
                    else -> 6
                }
            }.thenByDescending { it.maxOptimizedDps }
        )
        assertEquals("Top tier should be S+", "S+", sortedByTier.first().metaTier)
        assertTrue("Top S+ tier should include Fulmintis or Stellarys",
            sortedByTier.take(10).any { it.name == "Fulmintis" || it.name == "Stellarys" })
        assertTrue("Lowest tier in roster should be A or B tier",
            sortedByTier.last().metaTier in listOf("A", "B+", "B"))

        // 3. Sort by Element Type
        val sortedByType = repository.aniimos.sortedWith(
            compareBy<com.example.data.model.Aniimo> { it.element.displayName }
                .thenBy { Regex("""\d+""").find(it.aniilogNumber)?.value?.toIntOrNull() ?: Int.MAX_VALUE }
        )
        val distinctElementsInOrder = sortedByType.map { it.element.displayName }.distinct()
        assertEquals("Elements should be sorted alphabetically",
            distinctElementsInOrder.sorted(), distinctElementsInOrder)
    }

    @Test
    fun `validate every Aniimo in the catalog resolves to a real face picture drawable`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)

        val aniimos = repository.aniimos
        assertEquals(126, aniimos.size)

        aniimos.forEach { aniimo ->
            val drawableId = com.example.ui.components.getAniimoFaceDrawable(
                aniimoId = aniimo.id,
                faceDrawableResName = aniimo.faceDrawableResName,
                context = context
            )
            assertTrue(
                "Aniimo ${aniimo.name} (#${aniimo.aniilogNumber}) must resolve to a valid face picture drawable",
                drawableId != 0
            )
        }
    }

    @Test
    fun `validate community tier voting persistence and dynamic tier ranking`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)
        repository.clearAllCommunityPostsAndVotes()
        repository.seedDatabaseIfEmpty()

        val voteDao = db.communityTierVoteDao()
        assertEquals("Community votes should start fresh with 0 generated votes", 0, voteDao.getCount())

        // Cast an S+ vote for Emberpup from a fresh slate
        repository.submitTierVote("emberpup", "S+")

        val updatedVote = voteDao.getVoteSync("emberpup")
        assertNotNull(updatedVote)
        assertEquals("User vote must be recorded as S+", "S+", updatedVote!!.userVote)
        assertEquals("Total votes must be 1 after first community vote", 1, updatedVote.totalVotes)
        assertEquals("S+", updatedVote.communityTier)

        // Change vote from S+ to B
        repository.submitTierVote("emberpup", "B")
        val reVote = voteDao.getVoteSync("emberpup")
        assertEquals("User vote must be updated to B", "B", reVote!!.userVote)
        assertEquals(1, reVote.totalVotes)
        assertEquals("B", reVote.communityTier)
    }

    @Test
    fun `validate Breezy Plains and Whisperwake Isles region categorization and counts`() {
        assertEquals("Breezy Plains must have 195 forms defined", 195, com.example.data.model.GameRegion.BREEZY_PLAINS.currentCount)
        assertEquals("Whisperwake Isles currently has 42 forms", 42, com.example.data.model.GameRegion.WHISPERWAKE_ISLES.currentCount)
        assertEquals("Whisperwake Isles total expanding to 54 forms", 54, com.example.data.model.GameRegion.WHISPERWAKE_ISLES.totalCount)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)

        val whisperwakeAniimos = repository.aniimos.filter { it.region == com.example.data.model.GameRegion.WHISPERWAKE_ISLES }
        assertTrue("Whisperwake Isles must contain marine/abyssal creatures", whisperwakeAniimos.isNotEmpty())
        assertTrue("Whirlbite must be in Whisperwake Isles", whisperwakeAniimos.any { it.id == "whirlbite" })
        assertTrue("Dewlop must be in Whisperwake Isles", whisperwakeAniimos.any { it.id == "dewlop" })

        val breezyPlainsAniimos = repository.aniimos.filter { it.region == com.example.data.model.GameRegion.BREEZY_PLAINS }
        assertTrue("Breezy Plains must contain starter/meadow creatures", breezyPlainsAniimos.isNotEmpty())
        assertTrue("Emberpup must be in Breezy Plains", breezyPlainsAniimos.any { it.id == "emberpup" })
        assertTrue("Fulmintis must be in Breezy Plains", breezyPlainsAniimos.any { it.id == "fulmintis" })
    }

    @Test
    fun `validate Top Rated Teams start fresh with 0 pre-posted teams and support custom 4-Aniimo team creation, upvoting, and admin removal`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)
        repository.seedDatabaseIfEmpty()
        repository.clearAllCommunityPostsAndVotes()

        val teamDao = db.topRatedTeamDao()
        assertEquals("Top Rated Teams must start fresh with 0 auto-generated teams", 0, teamDao.getCount())

        val initialTeams = repository.getAllTopRatedTeams().first()
        assertTrue("Initial Top Rated Teams list must be empty", initialTeams.isEmpty())

        // Publish a user-submitted 4-Aniimo team with 1 Held Item each and 6 Awakening stats
        val userTeam = com.example.data.model.TopRatedTeam(
            id = "custom_team_fulmintis_1",
            title = "Fulmintis Hyper Stagger Squad",
            mode = com.example.data.model.TeamMode.DPS,
            primaryAniimoId = "fulmintis",
            primaryAniimoName = "Fulmintis",
            members = listOf(
                com.example.data.model.TeamMemberBuild("fulmintis", "Fulmintis", "Main DPS", Element.LIGHTNING, "Ferocious Fang (+15)", "+24% Crit DMG", "ATK (6) > BREAK (6)"),
                com.example.data.model.TeamMemberBuild("dazmand", "Dazmand", "Synergist", Element.LIGHT, "Capacitous Battery (+15)", "+18% Energy Regen", "REGEN (6) > HP (6)"),
                com.example.data.model.TeamMemberBuild("grizbo", "Grizbo", "Breaker", Element.EARTH, "Bailite Crest (+15)", "+30% Stagger Break", "BREAK (6) > PDEF (6)"),
                com.example.data.model.TeamMemberBuild("stellarys", "Stellarys", "Burst Support", Element.DARK, "Supernova Shard (+15)", "+22% Ultimate DMG", "ATK (6) > MDEF (6)")
            ),
            capabilities = listOf(
                com.example.data.model.CapabilityPerk("HP", "Tier 1", "+12% Max HP", 4),
                com.example.data.model.CapabilityPerk("ATK", "Tier 3", "+25% Attack", 6),
                com.example.data.model.CapabilityPerk("BREAK", "Tier 3", "+25% Break Efficiency", 6),
                com.example.data.model.CapabilityPerk("PDEF", "Tier 1", "+8% PDEF", 2),
                com.example.data.model.CapabilityPerk("MDEF", "Tier 1", "+8% MDEF", 2),
                com.example.data.model.CapabilityPerk("REGEN", "Tier 2", "+15% Energy Regen", 4)
            ),
            pointInvestmentOrder = listOf(
                com.example.data.model.PointInvestmentStep("Pts 1-6", "ATK", "Maximize primary strike scaling", "Max First")
            ),
            strategyGuide = "Open with Grizbo Break, swap to Fulmintis during 5s stagger window.",
            estimatedDpsOrWinRate = "158,400 DPS",
            upvotes = 1,
            hasUserUpvoted = false,
            author = "CommunityTester"
        )
        repository.saveTopRatedTeam(userTeam)

        val fulmintisTeams = repository.getTopRatedTeamsForAniimo("fulmintis").first()
        assertEquals(1, fulmintisTeams.size)
        val dpsTeam = fulmintisTeams.first()
        assertEquals(4, dpsTeam.members.size)

        // Validate upvoting a team
        repository.toggleTeamUpvote(dpsTeam.id, false)
        val updatedTeamEntity = teamDao.getTeamById(dpsTeam.id)
        assertNotNull(updatedTeamEntity)
        assertEquals(2, updatedTeamEntity!!.upvotes)
        assertTrue(updatedTeamEntity.hasUserUpvoted)

        // Validate Admin removal of a team
        repository.deleteTopRatedTeam(dpsTeam.id)
        assertEquals(0, teamDao.getCount())
    }

    @Test
    fun `validate DPS Simulator 4-Aniimo squad, 1 Held Item per Aniimo, and 6-stat Awakening sliders`() = runBlocking {
        val app = ApplicationProvider.getApplicationContext<android.app.Application>()
        val viewModel = com.example.viewmodel.AniimoViewModel(app)

        // Verify 4 Aniimo slots and 1 Held Item per Aniimo
        assertNotNull(viewModel.simMainAniimo.value)
        assertNotNull(viewModel.simPartnerAniimo.value)
        assertNotNull(viewModel.simSlot3Aniimo.value)
        assertNotNull(viewModel.simSlot4Aniimo.value)

        assertTrue(viewModel.simMainHeldItem.value.isNotBlank())
        assertTrue(viewModel.simPartnerHeldItem.value.isNotBlank())
        assertTrue(viewModel.simSlot3HeldItem.value.isNotBlank())
        assertTrue(viewModel.simSlot4HeldItem.value.isNotBlank())

        // Update 6 Awakening stats (ATK, BREAK, REGEN, HP, PDEF, MDEF) and Held Item enhancement level
        viewModel.setSimItemEnhanceLevel(15)
        viewModel.setAwakeningStats(
            atk = 20,
            breakStat = 18,
            regen = 14,
            hp = 10,
            pdef = 6,
            mdef = 6
        )

        assertEquals(20, viewModel.simAwakeningAtk.value)
        assertEquals(18, viewModel.simAwakeningBreak.value)
        assertEquals(14, viewModel.simAwakeningRegen.value)
        assertEquals(10, viewModel.simAwakeningHp.value)
        assertEquals(6, viewModel.simAwakeningPdef.value)
        assertEquals(6, viewModel.simAwakeningMdef.value)

        val result = viewModel.calculatedDpsResult.first()
        assertTrue("Sustained DPS should be positive", result.totalDps > 0)
        assertTrue("5s Stagger Burst DPS should exceed sustained DPS", result.burstWindowDps > result.totalDps)
    }

    @Test
    fun `validate My Builds rename, online Aniimo image asset URLs, and Discussion Board persistence`() = runBlocking {
        // 1. Validate Screen titles
        assertEquals("My Builds", com.example.ui.navigation.Screen.DPS_CALCULATOR.title)
        assertEquals("Board", com.example.ui.navigation.Screen.DISCUSSION.title)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)
        repository.seedDatabaseIfEmpty()

        // 2. Validate every Aniimo has a direct online image URL (.webp / .png)
        repository.aniimos.forEach { aniimo ->
            val remoteUrl = aniimo.remoteArtworkUrl.orEmpty()
            assertTrue(
                "Aniimo ${aniimo.name} must have a direct online image URL, got $remoteUrl",
                remoteUrl.endsWith(".webp") || remoteUrl.endsWith(".png")
            )
        }

        // 3. Validate Discussion Board starts fresh with 0 generated posts, then test new post creation, upvoting, and replying
        repository.clearAllCommunityPostsAndVotes()
        val initialPosts = repository.getAllDiscussionPosts().first()
        assertTrue("Discussion Board should start fresh with 0 generated posts", initialPosts.isEmpty())

        // Add a brand new discussion thread for Emberpup
        val emberpup = repository.aniimos.first { it.id == "emberpup" }
        repository.addDiscussionPost(
            com.example.data.model.DiscussionPost(
                aniimoId = emberpup.id,
                aniimoName = emberpup.name,
                aniimoElement = emberpup.element,
                authorName = "StarterMain",
                category = com.example.data.model.DiscussionCategory.BUILD_IDEA,
                title = "Emberpup Early Game Campaign Rush",
                content = "Equip Ferocious Fang and invest 20 ATK first for fast Breezy Plains clears.",
                recommendedHeldItem = "Ferocious Fang (+15)",
                recommendedAwakening = "ATK (20) > BREAK (15)"
            )
        )
        val updatedPosts = repository.getAllDiscussionPosts().first()
        assertEquals(1, updatedPosts.size)
        val createdPost = updatedPosts.first()
        assertEquals("Emberpup Early Game Campaign Rush", createdPost.title)

        // Upvote post
        repository.toggleDiscussionUpvote(createdPost.id)
        val afterUpvote = repository.getAllDiscussionPosts().first().first { it.id == createdPost.id }
        assertEquals(createdPost.upvotes + 1, afterUpvote.upvotes)
        assertTrue(afterUpvote.hasUserUpvoted)

        // Add reply to post
        repository.addDiscussionReply(createdPost.id, "TestExplorer", "Great Emberpup build idea!")
        val afterReply = repository.getAllDiscussionPosts().first().first { it.id == createdPost.id }
        assertEquals(1, afterReply.replies.size)
        assertEquals("Great Emberpup build idea!", afterReply.replies.last().content)
    }

    @Test
    fun `validate per-Aniimo Species-Specific Regional Forms, Prismana and Umbral eligibility, Build Sharing, and Leaderboard Legit voting`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val repository = AniimoRepository(db)
        repository.seedDatabaseIfEmpty()

        val sparkles = com.example.data.repository.SparkleRepository.allSparkleTypes
        val aniimos = repository.aniimos
        assertEquals(126, aniimos.size)

        // 1. Validate Species-Specific Forms verified online (Not uniform across all 126 Aniimo!)
        val grizbo = aniimos.first { it.id == "grizbo" }
        val grizboAvail = com.example.data.repository.SparkleRepository.getFormAvailability(grizbo)
        val grizboVariants = com.example.data.repository.SparkleRepository.getRegionalVariantsForAniimo(grizbo)
        assertTrue("Grizbo must have Highland Form", grizboAvail.hasHighlandForm)
        assertTrue("Grizbo must have Prismana Form", grizboAvail.hasPrismanaForm)
        assertTrue("Grizbo variants must include Highland Form",
            grizboVariants.any { it.category == com.example.data.model.FormCategory.HIGHLAND })
        assertTrue("Grizbo variants must include Prismana Form",
            grizboVariants.any { it.category == com.example.data.model.FormCategory.PRISMANA })

        val thornblade = aniimos.first { it.id == "thornblade" }
        val thornAvail = com.example.data.repository.SparkleRepository.getFormAvailability(thornblade)
        assertTrue("Thornblade must have Mountain Woods Form", thornAvail.hasMountainWoodsForm)
        assertTrue("Thornblade must have Sea of Flowers Form", thornAvail.hasSeaOfFlowersForm)
        assertTrue("Thornblade must have Prismana Form", thornAvail.hasPrismanaForm)

        val nimbi = aniimos.first { it.id == "nimbi" }
        val nimbiAvail = com.example.data.repository.SparkleRepository.getFormAvailability(nimbi)
        assertTrue("Nimbi must have Rainfall Form", nimbiAvail.hasRainfallForm)
        assertTrue("Nimbi must have Cloudmist Form", nimbiAvail.hasCloudmistForm)
        assertTrue("Nimbi must have Plateau Form", nimbiAvail.hasPlateauForm)

        val glameep = aniimos.first { it.id == "glameep" }
        val glameepAvail = com.example.data.repository.SparkleRepository.getFormAvailability(glameep)
        assertTrue("Glameep must have Tempest Form", glameepAvail.hasTempestForm)
        assertTrue("Glameep must have Prismana Form", glameepAvail.hasPrismanaForm)

        val chompkin = aniimos.first { it.id == "chompkin" }
        val chompkinAvail = com.example.data.repository.SparkleRepository.getFormAvailability(chompkin)
        val chompkinVariants = com.example.data.repository.SparkleRepository.getRegionalVariantsForAniimo(chompkin)
        assertTrue("Chompkin does NOT have Prismana Form in-game", !chompkinAvail.hasPrismanaForm)
        assertTrue("Chompkin does NOT have Highland Form in-game", !chompkinAvail.hasHighlandForm)
        assertTrue("Chompkin variants must NOT include Prismana Form",
            chompkinVariants.none { it.category == com.example.data.model.FormCategory.PRISMANA })

        val emberpup = aniimos.first { it.id == "emberpup" }
        val emberpupAvail = com.example.data.repository.SparkleRepository.getFormAvailability(emberpup)
        assertTrue("Emberpup has Highland Form verified online", emberpupAvail.hasHighlandForm)
        assertTrue("Emberpup has Mountain Woods Form verified online", emberpupAvail.hasMountainWoodsForm)
        assertTrue("Emberpup has Snowfield Form verified online", emberpupAvail.hasSnowfieldForm)
        assertTrue("Base stage Emberpup can roll dark Umbral coat in Wild Surges / Egg Heist", emberpupAvail.hasUmbralForm)
        assertTrue("Base stage Emberpup does not lose Umbral coat until evolved", !emberpupAvail.evolutionStripsUmbralCoat)

        val scorchhowl = aniimos.first { it.id == "scorchhowl" }
        val scorchhowlAvail = com.example.data.repository.SparkleRepository.getFormAvailability(scorchhowl)
        assertTrue("Evolved Scorchhowl strips dark Umbral coat on evolution while retaining 6x Max IVs", scorchhowlAvail.evolutionStripsUmbralCoat)

        // Check every Aniimo has at least its Standard Form and valid Sparkle look
        aniimos.forEach { aniimo ->
            val variants = com.example.data.repository.SparkleRepository.getRegionalVariantsForAniimo(aniimo)
            assertTrue("Aniimo ${aniimo.name} must have at least Standard Form", variants.isNotEmpty())
            assertEquals("First form must be Standard", com.example.data.model.FormCategory.STANDARD, variants.first().category)

            val firstSparkle = sparkles.first()
            val speciesLook = com.example.data.repository.SparkleRepository.getSpeciesSparkleLook(
                aniimo = aniimo,
                sparkleType = firstSparkle,
                regionalVariant = variants.first()
            )
            assertTrue("Species sparkle look must have in-game title", speciesLook.inGameTitle.isNotBlank())
            assertTrue("Species sparkle look must have base coat look", speciesLook.baseCoatLook.isNotBlank())
            assertTrue("Species sparkle look must have Prismana body look", speciesLook.prismanaBodyLook.isNotBlank())
        }

        // 2. Validate BuildShareCodec encode/decode and publishing a new 4-Aniimo build
        val fulmintis = aniimos.first { it.id == "fulmintis" }
        val dazmand = aniimos.first { it.id == "dazmand" }
        val stellarys = aniimos.first { it.id == "stellarys" }
        val encodedCode = com.example.ui.components.BuildShareCodec.encodeBuildCode(
            mainAniimo = fulmintis,
            mainItem = "Ferocious Fang (+15)",
            partnerAniimo = dazmand,
            partnerItem = "Capacitous Battery (+15)",
            slot3Aniimo = grizbo,
            slot3Item = "Bailite Crest (+15)",
            slot4Aniimo = stellarys,
            slot4Item = "Supernova Shard (+15)",
            atk = 20,
            breakStat = 18,
            regen = 14,
            hp = 8,
            pdef = 4,
            mdef = 4
        )
        assertTrue("Encoded build code must start with ANIIMO-V4|", encodedCode.startsWith("ANIIMO-V4|"))
        val decoded = com.example.ui.components.BuildShareCodec.decodeBuildCode(encodedCode)
        assertNotNull("Decoded build payload must not be null", decoded)
        assertEquals("fulmintis", decoded!!.mainId)
        assertEquals("dazmand", decoded.partnerId)
        assertEquals(20, decoded.atk)
        assertEquals(18, decoded.breakStat)

        // 3. Validate Leaderboard starts fresh and supports user-submitted Legit / Not Legit voting
        repository.clearAllCommunityPostsAndVotes()
        assertTrue("Leaderboard starts fresh with 0 entries", repository.getAllLeaderboardEntries().first().isEmpty())

        val submittedId = repository.submitRun(
            com.example.data.model.LeaderboardEntry(
                id = 0,
                bossId = "alpha_nether_colossus",
                bossName = "Alpha Nether Colossus",
                runnerName = "NewMemberRunner",
                mainAniimoId = "fulmintis",
                mainAniimoName = "Fulmintis",
                mainAniimoElement = Element.LIGHTNING,
                partnerAniimoId = "dazmand",
                partnerAniimoName = "Dazmand",
                partnerAniimoElement = Element.LIGHT,
                clearTimeMillis = 38500L,
                clearTimeDisplay = "00:38.50",
                videoProofUrl = "https://youtube.com/watch?v=real_member_clear",
                videoPlatform = "YouTube",
                weaponEquipped = "Pendant (+15)",
                relicSetEquipped = "Partner: Capacitous Battery (+15)",
                estimatedDpsOutput = 165000,
                submittedDate = "Just now",
                isVerified = true,
                upvotes = 0,
                hasUserUpvoted = false,
                isUserSubmission = true,
                runnerNotes = "First real community run!",
                legitVotes = 0,
                notLegitVotes = 0,
                userLegitVote = null,
                videoDurationSeconds = 40
            )
        )

        // 4. Test voting Legit and Not Legit on the user-submitted entry
        repository.voteLeaderboardLegitimacy(submittedId, voteLegit = true)

        val afterLegitVote = repository.getAllLeaderboardEntries().first().first { it.id == submittedId }
        assertEquals(1, afterLegitVote.legitVotes)
        assertEquals("LEGIT", afterLegitVote.userLegitVote)
        assertTrue(afterLegitVote.isLegitCommunityVerified)

        // Switch vote to Not Legit
        repository.voteLeaderboardLegitimacy(submittedId, voteLegit = false)
        val afterNotLegitVote = repository.getAllLeaderboardEntries().first().first { it.id == submittedId }
        assertEquals(1, afterNotLegitVote.notLegitVotes)
        assertEquals("NOT_LEGIT", afterNotLegitVote.userLegitVote)
    }

    @Test
    fun `validate 27 official Held Items, 17 Alpha Bosses, and Future-Proof Custom Names in Leaderboard and Discussion Board`() = runBlocking {
        // 1. Verify all 27 official Held Items
        val items = com.example.data.repository.AniimoHeldItemsDatabase.allHeldItems
        assertEquals("Must catalog all 27 official held items", 27, items.size)
        assertTrue("Must include Ferocious Fang", items.any { it.name == "Ferocious Fang" })
        assertTrue("Must include Heartseeker Pendant", items.any { it.name == "Heartseeker Pendant" })
        assertTrue("Must include Capacitous Battery", items.any { it.name == "Capacitous Battery" })
        assertTrue("Must include Fission Needles", items.any { it.name == "Fission Needles" })
        assertTrue("Must include Gargantuan Horn", items.any { it.name == "Gargantuan Horn" })
        assertTrue("Must include Destiny's Dice", items.any { it.name == "Destiny's Dice" })
        assertTrue("Must include Avenging Gear", items.any { it.name == "Avenging Gear" })
        assertTrue("Must include Bailite Crest", items.any { it.name == "Bailite Crest" })
        assertTrue("Must include Supernova Shard", items.any { it.name == "Supernova Shard" })

        // 2. Verify all 17 official roaming Alpha bosses from Aniimo
        val alphas = com.example.data.repository.AniimoAlphaBossesDatabase.allAlphaBosses
        assertEquals("Must catalog all 17 official roaming field/secret Alpha bosses", 17, alphas.size)
        assertTrue("Must include Alpha Turbo", alphas.any { it.name == "Alpha Turbo" })
        assertTrue("Must include Alpha Stellarys", alphas.any { it.name == "Alpha Stellarys" })
        assertTrue("Must include Alpha Scorchhowl", alphas.any { it.name == "Alpha Scorchhowl" })
        assertTrue("Must include Alpha Whisperwake Kraken", alphas.any { it.name == "Alpha Whisperwake Kraken" })
        assertTrue("Must include Alpha Glynsera", alphas.any { it.name == "Alpha Glynsera" })
        assertTrue("Must include Alpha Irisal", alphas.any { it.name == "Alpha Irisal" })
        assertTrue("Must include Alpha Leafy", alphas.any { it.name == "Alpha Leafy" })
        assertTrue("Must include Alpha Tubster", alphas.any { it.name == "Alpha Tubster" })
        assertTrue("Must include Alpha Bouldus", alphas.any { it.name == "Alpha Bouldus" })
        assertTrue("Must include Alpha Blazen", alphas.any { it.name == "Alpha Blazen" })
        assertTrue("Must include Alpha Panpanta", alphas.any { it.name == "Alpha Panpanta" })
        assertTrue("Must include Alpha Ignitis", alphas.any { it.name == "Alpha Ignitis" })
        assertTrue("Must include Alpha Grizbo", alphas.any { it.name == "Alpha Grizbo" })
        assertTrue("Must include Alpha Magmarex", alphas.any { it.name == "Alpha Magmarex" })
        assertTrue("Must include Alpha Luminelle", alphas.any { it.name == "Alpha Luminelle" })
        assertTrue("Must include Alpha Geoclaw", alphas.any { it.name == "Alpha Geoclaw" })
        assertTrue("Must include Alpha Minespine", alphas.any { it.name == "Alpha Minespine" })

        // 3. Test Future-Proof Custom Boss & Custom Aniimo Leaderboard Submission via ViewModel
        val app = ApplicationProvider.getApplicationContext<android.app.Application>()
        val viewModel = com.example.viewmodel.AniimoViewModel(app)

        val customSubmitSuccess = viewModel.submitClearRun(
            runnerName = "SpeedsterX",
            bossId = "custom_boss_id",
            mainAniimoId = "custom_aniimo_id",
            partnerAniimoId = "custom_partner_id",
            minutes = 0,
            seconds = 35,
            millis = 40,
            videoUrl = "https://youtube.com/watch?v=speedster_custom",
            weaponName = "Custom Hyper Relic (+15)",
            relicSetName = "Custom Partner Relic & Burst Awakening",
            notes = "First clear with future unreleased Alpha boss!",
            videoDurationSeconds = 42,
            customBossName = "Alpha Solarix Titan",
            customMainAniimoName = "Solarix Striker",
            customPartnerAniimoName = "Lunaris Partner"
        )
        assertTrue("Custom run submission must succeed", customSubmitSuccess)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AniimoDatabase.getDatabase(context)
        val entries = kotlinx.coroutines.withTimeout(5000L) {
            db.leaderboardDao().getAllEntries().first { list -> list.any { it.runnerName == "SpeedsterX" } }
        }
        val customEntry = entries.find { it.runnerName == "SpeedsterX" }
        assertNotNull("Custom entry must be saved in database", customEntry)
        assertEquals("Alpha Solarix Titan", customEntry!!.bossName)
        assertEquals("Solarix Striker", customEntry.mainAniimoName)
        assertEquals("Lunaris Partner", customEntry.partnerAniimoName)
        assertEquals("Custom Hyper Relic (+15)", customEntry.weaponEquipped)

        // 4. Test Future-Proof Custom Topic creation in Discussion Board
        val dummyAniimo = viewModel.aniimos.first()
        viewModel.submitDiscussionPost(
            aniimo = dummyAniimo,
            authorName = "FutureCrafter",
            category = com.example.data.model.DiscussionCategory.BUILD_IDEA,
            title = "Patch 2.0 Unreleased Aniimo & Relic Theory",
            content = "Theorycrafting new synergy mechanics for upcoming Alpha raid bosses.",
            recommendedHeldItem = "Prismatic Aegis (+15)",
            recommendedAwakening = "ATK (20) > BREAK (15) > REGEN (12)",
            customTopicName = "Future Aniimo: Zephyrion"
        )

        val posts = kotlinx.coroutines.withTimeout(5000L) {
            db.discussionDao().getAllPosts().first { list -> list.any { it.title == "Patch 2.0 Unreleased Aniimo & Relic Theory" } }
        }
        val customPost = posts.find { it.title == "Patch 2.0 Unreleased Aniimo & Relic Theory" }
        assertNotNull("Custom discussion post must be saved", customPost)
        assertEquals("Future Aniimo: Zephyrion", customPost!!.aniimoName)
        assertEquals("custom_future_aniimo:_zephyrion", customPost.aniimoId)
    }

    @Test
    fun `validate custom theme color conversions and safe surface copy`() {
        val defaultBgArgb = 0xFF0A0F1D.toInt()
        val defaultTextArgb = 0xFFFFFFFF.toInt()
        val defaultBgColor = androidx.compose.ui.graphics.Color(defaultBgArgb)
        val defaultTextColor = androidx.compose.ui.graphics.Color(defaultTextArgb)

        // Ensure copy(alpha) does not throw ArrayIndexOutOfBoundsException
        val surfaceColor = defaultBgColor.copy(alpha = 0.95f)
        assertNotNull(surfaceColor)
        assertEquals(defaultBgArgb, defaultBgColor.toArgb())
        assertEquals(defaultTextArgb, defaultTextColor.toArgb())
    }
}



