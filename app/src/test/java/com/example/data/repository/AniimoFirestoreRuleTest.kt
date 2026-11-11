package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.model.FirestoreDiscussionPost
import com.example.data.model.FirestoreLeaderboardEntry
import com.example.data.model.FirestoreLivePatchNote
import com.example.data.model.FirestoreTeam
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.UUID

class AniimoFirestoreRuleTest : FirestoreEmulatorTestBase() {

  @Test
  fun saveTeam_authenticatedOwner_createsDocumentAndEmits() = runBlocking {
    val uid = signInTestUser(ALICE_EMAIL)
    val repository = AniimoFirestoreRepository(firestore)

    val team = FirestoreTeam(
      id = "team_${UUID.randomUUID().toString().take(8)}",
      userId = uid,
      authorName = "Alice",
      name = "Burst Storm Squad",
      tag = "DPS",
      description = "Fast boss shred",
      memberIds = listOf("001", "002"),
      membersJson = "[]",
      isPublic = true,
      upvotes = 0
    )

    val saveResult = withTimeout(DEFAULT_TIMEOUT_MS) { repository.saveTeam(team) }
    assertTrue("Save team should succeed", saveResult.isSuccess)

    val publicTeams = withTimeout(FLOW_TIMEOUT_MS) {
      repository.observePublicTeams().first { list: List<FirestoreTeam> -> list.any { it.name == team.name } }
    }
    assertTrue("Public teams should contain saved team", publicTeams.any { it.name == team.name })
  }

  @Test
  fun createDiscussionPost_authenticatedUser_success() = runBlocking {
    val uid = signInTestUser(ALICE_EMAIL)
    val repository = AniimoFirestoreRepository(firestore)

    val post = FirestoreDiscussionPost(
      id = "post_${UUID.randomUUID().toString().take(8)}",
      authorUid = uid,
      authorName = "Alice",
      aniimoId = "001",
      aniimoName = "Ignisaur",
      title = "Awakening ATK Guide",
      body = "Invest in ATK first for max DPS multiplier",
      tag = "Builds",
      upvotes = 0,
      commentsCount = 0
    )

    val result = withTimeout(DEFAULT_TIMEOUT_MS) { repository.createDiscussionPost(post) }
    assertTrue("Discussion post creation should succeed", result.isSuccess)
  }

  @Test
  fun submitLeaderboardEntry_andCastLegitVote_success() = runBlocking {
    val runnerUid = signInTestUser(ALICE_EMAIL)
    val aliceRepo = AniimoFirestoreRepository(firestore)

    val entryId = "run_${UUID.randomUUID().toString().take(8)}"
    val entry = FirestoreLeaderboardEntry(
      id = entryId,
      submitterUid = runnerUid,
      runnerName = "SpeedyAlice",
      dungeonName = "Ancient Grove",
      teamName = "Gale Burst",
      aniimoIds = listOf("001", "004"),
      clearTimeSeconds = 48,
      clearTimeFormatted = "00:48.50",
      videoUrl = "https://youtube.com/watch?v=sample",
      notes = "Sub 50 clean run",
      legitVotes = 0,
      notLegitVotes = 0,
      isVerified = false
    )

    val submitResult = withTimeout(DEFAULT_TIMEOUT_MS) { aliceRepo.submitLeaderboardEntry(entry) }
    assertTrue("Leaderboard entry submission should succeed", submitResult.isSuccess)

    // Bob signs in and votes Legit
    val bobUid = signInTestUser(BOB_EMAIL)
    val bobRepo = AniimoFirestoreRepository(firestore)
    val voteResult = withTimeout(DEFAULT_TIMEOUT_MS) {
      bobRepo.castLegitVote(entryId, bobUid, isLegit = true, reason = "Legit timer and HUD shown")
    }
    assertTrue("Cast legit vote should succeed", voteResult.isSuccess)
  }

  @Test
  fun publishPatchNote_authenticatedUser_success() = runBlocking {
    val uid = signInTestUser(ALICE_EMAIL)
    val repository = AniimoFirestoreRepository(firestore)

    val patch = FirestoreLivePatchNote(
      id = "patch_${UUID.randomUUID().toString().take(8)}",
      version = "1.2.5",
      title = "Balance Patch v1.2.5",
      releaseDate = "2026-11-04",
      sourceUrl = "https://aniimo.game/patch",
      summary = "Skill multiplier adjustments",
      skillChangesJson = "[]",
      statChangesJson = "[]",
      newAniimoJson = "[]",
      syncedByUid = uid
    )

    val result = withTimeout(DEFAULT_TIMEOUT_MS) { repository.publishPatchNote(patch) }
    assertTrue("Publish patch note should succeed", result.isSuccess)
  }

  @Test
  fun observePublicTeams_unauthenticatedUser_failsWithPermissionDenied() = runBlocking {
    auth.signOut()

    var caughtException: Exception? = null
    try {
      withTimeout(DEFAULT_TIMEOUT_MS) {
        firestore.collection("teams").whereEqualTo("isPublic", true).get().await()
      }
      fail("Expected unauthenticated query to fail with permission denied")
    } catch (e: Exception) {
      caughtException = e
    }

    val firestoreException = caughtException?.unwrapFirestoreException()
    assertTrue("Expected nested FirebaseFirestoreException", firestoreException != null)
    assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, firestoreException!!.code)
  }

  private companion object {
    const val ALICE_EMAIL = "alice_test@example.com"
    const val BOB_EMAIL = "bob_test@example.com"
    const val DEFAULT_TIMEOUT_MS = 5000L
    const val FLOW_TIMEOUT_MS = 4000L
  }
}
