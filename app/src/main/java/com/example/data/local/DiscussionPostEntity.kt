package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.DiscussionCategory
import com.example.data.model.DiscussionPost
import com.example.data.model.DiscussionReply
import com.example.data.model.Element

@Entity(tableName = "discussion_posts")
data class DiscussionPostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val aniimoId: String,
    val aniimoName: String,
    val aniimoElement: String,
    val authorName: String,
    val authorBadge: String,
    val categoryId: String,
    val title: String,
    val content: String,
    val recommendedHeldItem: String,
    val recommendedAwakening: String,
    val mediaUrl: String = "",
    val mediaType: String = "",
    val upvotes: Int,
    val hasUserUpvoted: Boolean,
    val timestamp: String,
    val repliesSerialized: String
) {
    fun toDomainModel(): DiscussionPost {
        val parsedElement = try {
            Element.valueOf(aniimoElement.uppercase())
        } catch (e: Exception) {
            Element.FIRE
        }
        val parsedReplies = if (repliesSerialized.isBlank()) {
            emptyList()
        } else {
            repliesSerialized.split("||REPLY||").mapNotNull { raw ->
                val parts = raw.split("::FIELD::")
                if (parts.size >= 4) {
                    DiscussionReply(
                        id = parts[0],
                        authorName = parts[1],
                        content = parts[2],
                        timestamp = parts[3]
                    )
                } else null
            }
        }
        return DiscussionPost(
            id = id,
            aniimoId = aniimoId,
            aniimoName = aniimoName,
            aniimoElement = parsedElement,
            authorName = authorName,
            authorBadge = authorBadge,
            category = DiscussionCategory.fromId(categoryId),
            title = title,
            content = content,
            recommendedHeldItem = recommendedHeldItem,
            recommendedAwakening = recommendedAwakening,
            mediaUrl = mediaUrl,
            mediaType = mediaType,
            upvotes = upvotes,
            hasUserUpvoted = hasUserUpvoted,
            timestamp = timestamp,
            replies = parsedReplies
        )
    }

    companion object {
        fun serializeReplies(replies: List<DiscussionReply>): String {
            return replies.joinToString("||REPLY||") { reply ->
                "${reply.id}::FIELD::${reply.authorName}::FIELD::${reply.content}::FIELD::${reply.timestamp}"
            }
        }

        fun fromDomainModel(post: DiscussionPost): DiscussionPostEntity {
            return DiscussionPostEntity(
                id = post.id,
                aniimoId = post.aniimoId,
                aniimoName = post.aniimoName,
                aniimoElement = post.aniimoElement.name,
                authorName = post.authorName,
                authorBadge = post.authorBadge,
                categoryId = post.category.id,
                title = post.title,
                content = post.content,
                recommendedHeldItem = post.recommendedHeldItem,
                recommendedAwakening = post.recommendedAwakening,
                mediaUrl = post.mediaUrl,
                mediaType = post.mediaType,
                upvotes = post.upvotes,
                hasUserUpvoted = post.hasUserUpvoted,
                timestamp = post.timestamp,
                repliesSerialized = serializeReplies(post.replies)
            )
        }
    }
}
