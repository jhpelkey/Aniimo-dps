package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.FirestoreAppConfig
import com.example.data.model.FirestoreDiscussionPost
import com.example.data.model.FirestoreLeaderboardEntry
import com.example.data.model.FirestoreLivePatchNote
import com.example.data.model.FirestoreModerationBan
import com.example.data.model.FirestorePostComment
import com.example.data.model.FirestoreSubmissionVote
import com.example.data.model.FirestoreTeam
import com.example.data.util.OperationType
import com.example.data.util.handleFirestoreError
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AniimoFirestoreRepository(
    private val db: FirebaseFirestore
) {
    // Convenience constructor: resolves named database ID from string resources
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    // ==========================================
    // 1. TEAMS COLLECTION
    // ==========================================
    fun observePublicTeams(): Flow<List<FirestoreTeam>> {
        return db.collection("teams")
            .whereEqualTo("isPublic", true)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreTeam::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            }
            .catch { emit(emptyList()) }
    }

    fun observeUserTeams(userId: String): Flow<List<FirestoreTeam>> {
        return db.collection("teams")
            .whereEqualTo("userId", userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreTeam::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            }
            .catch { emit(emptyList()) }
    }

    suspend fun saveTeam(team: FirestoreTeam): Result<String> {
        val docId = if (team.id.isNotBlank()) team.id else UUID.randomUUID().toString()
        val docRef = db.collection("teams").document(docId)
        return try {
            val payload = mapOf(
                "userId" to team.userId,
                "authorName" to team.authorName,
                "name" to team.name,
                "tag" to team.tag,
                "description" to team.description,
                "memberIds" to team.memberIds,
                "membersJson" to team.membersJson,
                "isPublic" to team.isPublic,
                "upvotes" to team.upvotes,
                "createdAt" to (team.createdAt ?: FieldValue.serverTimestamp()),
                "updatedAt" to FieldValue.serverTimestamp()
            ).filterValues { it != null }

            docRef.set(payload).await()
            Result.success(docId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun upvoteTeam(teamId: String, currentUpvotes: Int): Result<Unit> {
        val docRef = db.collection("teams").document(teamId)
        return try {
            docRef.update(
                mapOf(
                    "upvotes" to currentUpvotes + 1,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun deleteTeam(teamId: String): Result<Unit> {
        val docRef = db.collection("teams").document(teamId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    // ==========================================
    // 2. DISCUSSION POSTS & COMMENTS
    // ==========================================
    fun observeDiscussionPosts(): Flow<List<FirestoreDiscussionPost>> {
        return db.collection("posts")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreDiscussionPost::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            }
            .catch { emit(emptyList()) }
    }

    suspend fun createDiscussionPost(post: FirestoreDiscussionPost): Result<String> {
        val docId = if (post.id.isNotBlank()) post.id else UUID.randomUUID().toString()
        val docRef = db.collection("posts").document(docId)
        return try {
            val payload = mapOf(
                "authorUid" to post.authorUid,
                "authorName" to post.authorName,
                "aniimoId" to post.aniimoId,
                "aniimoName" to post.aniimoName,
                "title" to post.title,
                "body" to post.body,
                "tag" to post.tag,
                "upvotes" to post.upvotes,
                "commentsCount" to post.commentsCount,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            ).filterValues { it != null }

            docRef.set(payload).await()
            Result.success(docId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun upvoteDiscussionPost(postId: String, currentUpvotes: Int): Result<Unit> {
        val docRef = db.collection("posts").document(postId)
        return try {
            docRef.update(
                mapOf(
                    "upvotes" to currentUpvotes + 1,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    fun observeComments(postId: String): Flow<List<FirestorePostComment>> {
        return db.collection("posts").document(postId).collection("comments")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestorePostComment::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            }
            .catch { emit(emptyList()) }
    }

    suspend fun addComment(postId: String, comment: FirestorePostComment): Result<String> {
        val commentId = UUID.randomUUID().toString()
        val commentRef = db.collection("posts").document(postId).collection("comments").document(commentId)
        val postRef = db.collection("posts").document(postId)
        return try {
            val payload = mapOf(
                "authorUid" to comment.authorUid,
                "authorName" to comment.authorName,
                "content" to comment.content,
                "createdAt" to FieldValue.serverTimestamp()
            ).filterValues { it != null }

            commentRef.set(payload).await()
            // Increment comment count on parent post
            postRef.update(
                mapOf(
                    "commentsCount" to FieldValue.increment(1),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(commentId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, commentRef.path)
            Result.failure(e)
        }
    }

    // ==========================================
    // 3. LEADERBOARD SPEEDRUN RUNS & LEGIT VOTES
    // ==========================================
    fun observeLeaderboardEntries(): Flow<List<FirestoreLeaderboardEntry>> {
        return db.collection("leaderboard_entries")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreLeaderboardEntry::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            }
            .catch { emit(emptyList()) }
    }

    suspend fun submitLeaderboardEntry(entry: FirestoreLeaderboardEntry): Result<String> {
        val docId = if (entry.id.isNotBlank()) entry.id else UUID.randomUUID().toString()
        val docRef = db.collection("leaderboard_entries").document(docId)
        return try {
            val payload = mapOf(
                "submitterUid" to entry.submitterUid,
                "runnerName" to entry.runnerName,
                "dungeonName" to entry.dungeonName,
                "teamName" to entry.teamName,
                "aniimoIds" to entry.aniimoIds,
                "clearTimeSeconds" to entry.clearTimeSeconds,
                "clearTimeFormatted" to entry.clearTimeFormatted,
                "videoUrl" to entry.videoUrl,
                "notes" to entry.notes,
                "legitVotes" to entry.legitVotes,
                "notLegitVotes" to entry.notLegitVotes,
                "isVerified" to entry.isVerified,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            ).filterValues { it != null }

            docRef.set(payload).await()
            Result.success(docId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun castLegitVote(entryId: String, voterUid: String, isLegit: Boolean, reason: String = ""): Result<Unit> {
        val entryRef = db.collection("leaderboard_entries").document(entryId)
        val voteRef = entryRef.collection("votes").document(voterUid)
        return try {
            val votePayload = mapOf(
                "voterUid" to voterUid,
                "isLegit" to isLegit,
                "reason" to reason,
                "createdAt" to FieldValue.serverTimestamp()
            ).filterValues { it != null }
            voteRef.set(votePayload).await()

            // Update entry vote tallies
            val snapshot = entryRef.get().await()
            val currentLegit = snapshot.getLong("legitVotes")?.toInt() ?: 0
            val currentNotLegit = snapshot.getLong("notLegitVotes")?.toInt() ?: 0
            val newLegit = if (isLegit) currentLegit + 1 else currentLegit
            val newNotLegit = if (!isLegit) currentNotLegit + 1 else currentNotLegit
            val verified = newLegit > newNotLegit

            entryRef.update(
                mapOf(
                    "legitVotes" to newLegit,
                    "notLegitVotes" to newNotLegit,
                    "isVerified" to verified,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, entryRef.path)
            Result.failure(e)
        }
    }

    // ==========================================
    // 4. LIVE PATCH NOTES & BALANCES
    // ==========================================
    fun observePatchNotes(): Flow<List<FirestoreLivePatchNote>> {
        return db.collection("patches")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreLivePatchNote::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            }
            .catch { emit(emptyList()) }
    }

    suspend fun publishPatchNote(patch: FirestoreLivePatchNote): Result<String> {
        val patchId = if (patch.id.isNotBlank()) patch.id else "v${patch.version.replace(".", "_")}_${UUID.randomUUID().toString().take(6)}"
        val docRef = db.collection("patches").document(patchId)
        return try {
            val payload = mapOf(
                "version" to patch.version,
                "title" to patch.title,
                "releaseDate" to patch.releaseDate,
                "sourceUrl" to patch.sourceUrl,
                "summary" to patch.summary,
                "skillChangesJson" to patch.skillChangesJson,
                "statChangesJson" to patch.statChangesJson,
                "newAniimoJson" to patch.newAniimoJson,
                "syncedByUid" to patch.syncedByUid,
                "createdAt" to FieldValue.serverTimestamp()
            ).filterValues { it != null }

            docRef.set(payload).await()
            Result.success(patchId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    // ==========================================
    // 5. ADMIN MODERATION (DELETE POSTS/RUNS & TIMEOUTS/BANS)
    // ==========================================
    suspend fun deleteDiscussionPost(postId: String): Result<Unit> {
        val docRef = db.collection("posts").document(postId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun deleteLeaderboardEntry(entryId: String): Result<Unit> {
        val docRef = db.collection("leaderboard_entries").document(entryId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    fun observeModerationBans(): Flow<List<FirestoreModerationBan>> {
        return db.collection("moderation_bans")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreModerationBan::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            }
            .catch { emit(emptyList()) }
    }

    suspend fun issueModerationBan(ban: FirestoreModerationBan): Result<String> {
        val safeDocId = ban.id.ifBlank {
            ban.targetIdentifier.lowercase().replace(Regex("[^a-z0-9_-]"), "_").take(64).ifBlank { UUID.randomUUID().toString() }
        }
        val docRef = db.collection("moderation_bans").document(safeDocId)
        return try {
            val payload = mapOf(
                "targetIdentifier" to ban.targetIdentifier,
                "targetAuthorName" to ban.targetAuthorName,
                "actionType" to ban.actionType,
                "reason" to ban.reason,
                "expiresAtMillis" to ban.expiresAtMillis,
                "issuedByEmail" to ban.issuedByEmail,
                "createdAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(safeDocId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun revokeModerationBan(banId: String): Result<Unit> {
        val docRef = db.collection("moderation_bans").document(banId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    // ==========================================
    // 6. OVER-THE-AIR LIVE APP CONFIG & GITHUB RELEASE FEED
    // ==========================================
    fun observeAppConfig(): Flow<FirestoreAppConfig?> {
        return db.collection("app_config").document("live_release")
            .snapshots()
            .map { snapshot ->
                if (snapshot.exists()) {
                    snapshot.toObject(FirestoreAppConfig::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                } else {
                    null
                }
            }
            .catch { emit(null) }
    }

    suspend fun publishAppConfig(config: FirestoreAppConfig): Result<Unit> {
        val docRef = db.collection("app_config").document("live_release")
        return try {
            val payload = mapOf(
                "latestVersionName" to config.latestVersionName,
                "githubRepoSlug" to config.githubRepoSlug,
                "directApkUrl" to config.directApkUrl,
                "liveAnnouncement" to config.liveAnnouncement,
                "remoteResetEpoch" to config.remoteResetEpoch,
                "updatedByEmail" to config.updatedByEmail,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }
}

