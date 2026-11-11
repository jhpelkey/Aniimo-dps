package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Aniimo
import com.example.data.model.DiscussionCategory
import com.example.data.model.DiscussionPost
import com.example.ui.components.AniimoElementBadge
import com.example.ui.components.AniimoFaceAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionBoardScreen(
    posts: List<DiscussionPost>,
    aniimos: List<Aniimo>,
    selectedAniimoFilter: String?,
    selectedCategory: DiscussionCategory,
    searchQuery: String,
    onSelectAniimoFilter: (String?) -> Unit,
    onSelectCategory: (DiscussionCategory) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSubmitPost: (
        aniimo: Aniimo,
        authorName: String,
        category: DiscussionCategory,
        title: String,
        content: String,
        recommendedHeldItem: String,
        recommendedAwakening: String,
        customTopicName: String?,
        mediaUrl: String,
        mediaType: String
    ) -> Unit,
    onToggleUpvote: (Int) -> Unit,
    onSubmitReply: (postId: Int, authorName: String, replyContent: String) -> Unit,
    onNavigateToAniimoDetail: ((Aniimo) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var isNewPostDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
            .testTag("discussion_board_screen")
    ) {
        // Header Banner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A))
                    )
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Forum,
                        contentDescription = "Discussion Board",
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Aniimo Discussion Board",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Share builds, 1 Held Item setups, Awakening orders & strategies for all ${aniimos.size} Aniimos",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                Button(
                    onClick = { isNewPostDialogOpen = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("open_new_discussion_dialog_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.AddComment,
                        contentDescription = null,
                        tint = Color(0xFF0F172A),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+ Start a Post",
                        color = Color(0xFF0F172A),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        text = "Search ideas by Aniimo, Held Item, Awakening, or topic...",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Discussions",
                        tint = Color(0xFF38BDF8)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Search",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF38BDF8),
                    unfocusedBorderColor = Color(0xFF1E293B),
                    focusedContainerColor = Color(0xFF0A0F1D),
                    unfocusedContainerColor = Color(0xFF0A0F1D),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("discussion_search_input")
            )
        }

        // Filter by Specific Aniimo Ribbon
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "FILTER BY ANIIMO",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF94A3B8),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    val isAllSelected = selectedAniimoFilter == null
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isAllSelected) Color(0xFF38BDF8) else Color(0xFF131D33))
                            .border(
                                1.dp,
                                if (isAllSelected) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { onSelectAniimoFilter(null) }
                            .padding(horizontal = 14.dp)
                            .testTag("discussion_filter_aniimo_all"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "All Aniimos (${aniimos.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isAllSelected) Color(0xFF0F172A) else Color.White
                        )
                    }
                }

                items(aniimos, key = { it.id }) { aniimo ->
                    val isSelected = selectedAniimoFilter.equals(aniimo.id, ignoreCase = true)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) Color(0xFF1E293B) else Color(0xFF131D33))
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                onSelectAniimoFilter(if (isSelected) null else aniimo.id)
                            }
                            .padding(horizontal = 10.dp)
                            .testTag("discussion_filter_aniimo_${aniimo.id}")
                    ) {
                        AniimoFaceAvatar(
                            aniimo = aniimo,
                            size = 30.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = aniimo.name,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF38BDF8) else Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Topic Category Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DiscussionCategory.values()) { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCategory(category) },
                        label = {
                            Text(
                                text = category.displayName,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(category.badgeColorHex).copy(alpha = 0.22f),
                            selectedLabelColor = Color(category.badgeColorHex),
                            containerColor = Color(0xFF131D33),
                            labelColor = Color(0xFF94A3B8)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = Color(0xFF1E293B),
                            selectedBorderColor = Color(category.badgeColorHex)
                        ),
                        modifier = Modifier.testTag("discussion_category_${category.id}")
                    )
                }
            }
        }

        // Discussion Threads List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Prominent "Start a Post" Composer Card on the Board
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                        .clickable { isNewPostDialogOpen = true }
                        .testTag("board_start_a_post_banner")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddComment,
                                contentDescription = null,
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Start a New Discussion Post",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Share your Aniimo theorycraft, 1 Held Item setup, Awakening stat order, or regional form guide...",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF38BDF8))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Start a Post",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF0F172A)
                            )
                        }
                    }
                }
            }

            if (posts.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChatBubbleOutline,
                                contentDescription = null,
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "No discussion threads match this filter yet.",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Be the first to share a build idea, Awakening priority, or strategy for this Aniimo!",
                                color = Color(0xFF94A3B8),
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { isNewPostDialogOpen = true },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Start First Thread",
                                    color = Color(0xFF0F172A),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            } else {
                items(posts, key = { it.id }) { post ->
                    val matchedAniimo = aniimos.find { it.id.equals(post.aniimoId, ignoreCase = true) }
                    DiscussionPostCard(
                        post = post,
                        aniimo = matchedAniimo,
                        onToggleUpvote = { onToggleUpvote(post.id) },
                        onSubmitReply = { author, replyText ->
                            onSubmitReply(post.id, author, replyText)
                        },
                        onClickAniimo = {
                            if (matchedAniimo != null && onNavigateToAniimoDetail != null) {
                                onNavigateToAniimoDetail(matchedAniimo)
                            }
                        }
                    )
                }
            }
        }
    }

    if (isNewPostDialogOpen) {
        val defaultAniimo = selectedAniimoFilter?.let { filterId ->
            aniimos.find { it.id.equals(filterId, ignoreCase = true) }
        } ?: aniimos.firstOrNull()

        if (defaultAniimo != null) {
            NewDiscussionPostDialog(
                aniimos = aniimos,
                initialAniimo = defaultAniimo,
                onDismiss = { isNewPostDialogOpen = false },
                onSubmit = { chosenAniimo, author, category, title, content, item, awakening, customTopic, mediaUrl, mediaType ->
                    onSubmitPost(chosenAniimo, author, category, title, content, item, awakening, customTopic, mediaUrl, mediaType)
                    isNewPostDialogOpen = false
                }
            )
        }
    }
}

@Composable
fun DiscussionPostCard(
    post: DiscussionPost,
    aniimo: Aniimo?,
    onToggleUpvote: () -> Unit,
    onSubmitReply: (authorName: String, replyContent: String) -> Unit,
    onClickAniimo: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showReplies by remember { mutableStateOf(false) }
    var replyAuthor by remember { mutableStateOf("") }
    var replyText by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("discussion_post_card_${post.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Top Row: Aniimo Avatar + Name + Category Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(enabled = onClickAniimo != null) { onClickAniimo?.invoke() }
                        .padding(end = 6.dp)
                ) {
                    if (aniimo != null) {
                        AniimoFaceAvatar(
                            aniimo = aniimo,
                            size = 38.dp
                        )
                    } else {
                        com.example.ui.components.AniimoFaceAvatarById(
                            aniimoId = post.aniimoId,
                            aniimoName = post.aniimoName,
                            element = post.aniimoElement,
                            size = 38.dp,
                            shape = CircleShape
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = post.aniimoName,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            AniimoElementBadge(element = post.aniimoElement, compact = true)
                        }
                        Text(
                            text = "by ${post.authorName} (${post.authorBadge}) • ${post.timestamp}",
                            fontSize = 10.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(post.category.badgeColorHex).copy(alpha = 0.18f))
                        .border(
                            1.dp,
                            Color(post.category.badgeColorHex).copy(alpha = 0.6f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = post.category.displayName,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(post.category.badgeColorHex)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Post Title
            Text(
                text = post.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF8FAFC)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Post Content
            Text(
                text = post.content,
                fontSize = 12.sp,
                color = Color(0xFFCBD5E1),
                lineHeight = 18.sp
            )

            // Optional Build Callouts (1 Held Item & 6-Stat Awakening Order)
            if (post.recommendedHeldItem.isNotBlank() || post.recommendedAwakening.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F172A))
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (post.recommendedHeldItem.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "1 Held Item: ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700)
                            )
                            Text(
                                text = post.recommendedHeldItem,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                    if (post.recommendedAwakening.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Awakening Priority: ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8)
                            )
                            Text(
                                text = post.recommendedAwakening,
                                fontSize = 11.sp,
                                color = Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            }

            // Prismana & Home Showcase Media Attachment (Image or Video)
            if (post.mediaUrl.isNotBlank()) {
                val context = LocalContext.current
                val isVideo = post.mediaType.equals("video", ignoreCase = true) ||
                    post.mediaUrl.contains("youtu") ||
                    post.mediaUrl.endsWith(".mp4") ||
                    post.mediaUrl.endsWith(".webm")

                Spacer(modifier = Modifier.height(10.dp))

                if (isVideo) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEC4899).copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.mediaUrl))
                                    context.startActivity(intent)
                                } catch (_: Exception) {}
                            }
                            .testTag("post_video_media_${post.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEC4899)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Watch Video",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Prismana / Home Video Walkthrough",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFEC4899)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.OpenInNew,
                                        contentDescription = null,
                                        tint = Color(0xFF94A3B8),
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                                Text(
                                    text = post.mediaUrl,
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F172A))
                            .border(1.dp, Color(0xFFEC4899).copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                            .padding(8.dp)
                            .testTag("post_image_media_${post.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = null,
                                    tint = Color(0xFFEC4899),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Prismana / Home Showcase Media",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFEC4899)
                                )
                            }
                            Text(
                                text = "Tap to open",
                                fontSize = 10.sp,
                                color = Color(0xFF38BDF8),
                                modifier = Modifier.clickable {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.mediaUrl))
                                        context.startActivity(intent)
                                    } catch (_: Exception) {}
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        AsyncImage(
                            model = post.mediaUrl,
                            contentDescription = "Prismana & Home Showcase Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.mediaUrl))
                                        context.startActivity(intent)
                                    } catch (_: Exception) {}
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Bar: Upvote & Replies Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (post.hasUserUpvoted) Color(0xFF38BDF8).copy(alpha = 0.2f)
                            else Color(0xFF0F172A)
                        )
                        .border(
                            1.dp,
                            if (post.hasUserUpvoted) Color(0xFF38BDF8) else Color(0xFF1E293B),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onToggleUpvote() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("discussion_upvote_btn_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Upvote Idea",
                        tint = if (post.hasUserUpvoted) Color(0xFF38BDF8) else Color(0xFF94A3B8),
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.upvotes} Upvotes",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (post.hasUserUpvoted) Color(0xFF38BDF8) else Color(0xFFCBD5E1)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F172A))
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                        .clickable { showReplies = !showReplies }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("discussion_reply_toggle_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Reply,
                        contentDescription = "View or Add Replies",
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.replies.size} Replies",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF38BDF8)
                    )
                }
            }

            // Expandable Replies & Add Reply Input
            AnimatedVisibility(visible = showReplies) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0A0F1D))
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (post.replies.isEmpty()) {
                        Text(
                            text = "No replies yet. Add your thoughts below!",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    } else {
                        post.replies.forEach { reply ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF131D33))
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = reply.authorName,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF38BDF8)
                                    )
                                    Text(
                                        text = reply.timestamp,
                                        fontSize = 10.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = reply.content,
                                    fontSize = 12.sp,
                                    color = Color(0xFFE2E8F0)
                                )
                            }
                        }
                    }

                    // Inline Reply Composer
                    OutlinedTextField(
                        value = replyAuthor,
                        onValueChange = { replyAuthor = it },
                        label = { Text("Your Nickname (optional)", fontSize = 11.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF1E293B),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reply_author_input_${post.id}")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = replyText,
                            onValueChange = { replyText = it },
                            placeholder = { Text("Write a reply about ${post.aniimoName}...", fontSize = 11.sp) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF1E293B),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("reply_content_input_${post.id}")
                        )

                        IconButton(
                            onClick = {
                                if (replyText.isNotBlank()) {
                                    onSubmitReply(replyAuthor, replyText)
                                    replyText = ""
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF38BDF8))
                                .testTag("discussion_submit_reply_btn_${post.id}")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Reply",
                                tint = Color(0xFF0F172A)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDiscussionPostDialog(
    aniimos: List<Aniimo>,
    initialAniimo: Aniimo,
    lockAniimoSelection: Boolean = false,
    onDismiss: () -> Unit,
    onSubmit: (
        aniimo: Aniimo,
        authorName: String,
        category: DiscussionCategory,
        title: String,
        content: String,
        recommendedHeldItem: String,
        recommendedAwakening: String,
        customTopicName: String?,
        mediaUrl: String,
        mediaType: String
    ) -> Unit
) {
    var selectedAniimo by remember { mutableStateOf(initialAniimo) }
    var aniimoDropdownExpanded by remember { mutableStateOf(false) }
    var isCustomTopicMode by remember { mutableStateOf(false) }
    var customTopicName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(DiscussionCategory.BUILD_IDEA) }
    var authorName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var mediaUrl by remember { mutableStateOf("") }
    var recommendedItem by remember {
        mutableStateOf(initialAniimo.bestItems.firstOrNull()?.name ?: "")
    }
    var recommendedAwakening by remember {
        mutableStateOf("ATK (20) > BREAK (15) > REGEN (12)")
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF0F172A),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Share Aniimo Build or Idea",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                // Aniimo / Custom Subject Selector
                if (!lockAniimoSelection) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isCustomTopicMode) "Custom Topic / Unreleased Idea" else "Aniimo Subject",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (isCustomTopicMode) "Pick from Catalog" else "+ Custom Aniimo, Item, Skill or Alpha",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable { isCustomTopicMode = !isCustomTopicMode }
                                    .padding(4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))

                        if (isCustomTopicMode) {
                            OutlinedTextField(
                                value = customTopicName,
                                onValueChange = { customTopicName = it },
                                label = { Text("Topic Name (e.g. Alpha Titan Boss, Future Aniimo: Zephyrion, Skill: Thunderburst)") },
                                placeholder = { Text("e.g. Alpha Sovereign or Skill: Plasma Shock") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF38BDF8),
                                    unfocusedBorderColor = Color(0xFF1E293B),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("custom_discussion_topic_input")
                            )
                        } else {
                            ExposedDropdownMenuBox(
                                expanded = aniimoDropdownExpanded,
                                onExpandedChange = { aniimoDropdownExpanded = !aniimoDropdownExpanded }
                            ) {
                                OutlinedTextField(
                                    value = "${selectedAniimo.aniilogNumber} • ${selectedAniimo.name} (${selectedAniimo.element.displayName})",
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = aniimoDropdownExpanded)
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF38BDF8),
                                        unfocusedBorderColor = Color(0xFF1E293B),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = aniimoDropdownExpanded,
                                    onDismissRequest = { aniimoDropdownExpanded = false },
                                    modifier = Modifier.background(Color(0xFF131D33))
                                ) {
                                    aniimos.forEach { itemAniimo ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = "${itemAniimo.aniilogNumber} • ${itemAniimo.name}",
                                                    color = Color.White,
                                                    fontSize = 13.sp
                                                )
                                            },
                                            onClick = {
                                                selectedAniimo = itemAniimo
                                                recommendedItem = itemAniimo.bestItems.firstOrNull()?.name ?: ""
                                                aniimoDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF131D33))
                            .padding(10.dp)
                    ) {
                        AniimoFaceAvatar(aniimo = selectedAniimo, size = 36.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Posting in ${selectedAniimo.name} Discussion",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                // Category Selector Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    DiscussionCategory.values()
                        .filter { it != DiscussionCategory.ALL }
                        .forEach { cat ->
                            val isSelected = selectedCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat.displayName, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(cat.badgeColorHex).copy(alpha = 0.25f),
                                    selectedLabelColor = Color(cat.badgeColorHex),
                                    containerColor = Color(0xFF131D33),
                                    labelColor = Color(0xFF94A3B8)
                                )
                            )
                        }
                }

                OutlinedTextField(
                    value = authorName,
                    onValueChange = { authorName = it },
                    label = { Text("Your Nickname") },
                    placeholder = { Text("e.g., BreezyTheorycrafter") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("new_post_author_input")
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Thread Title") },
                    placeholder = { Text("e.g., Best Held Item & Awakening for ${selectedAniimo.name}") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("new_post_title_input")
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Your Idea / Strategy / Discussion") },
                    placeholder = { Text("Share rotation tips, partner synergies, or counters...") },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("new_post_content_input")
                )

                OutlinedTextField(
                    value = recommendedItem,
                    onValueChange = { recommendedItem = it },
                    label = { Text("1 Held Item Recommendation (Optional)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = recommendedAwakening,
                    onValueChange = { recommendedAwakening = it },
                    label = { Text("6-Stat Awakening Order (Optional)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Prismana / Home Showcase Media Input
                OutlinedTextField(
                    value = mediaUrl,
                    onValueChange = { mediaUrl = it },
                    label = { Text("Image / Video URL for Prismana or Home Build (Optional)") },
                    placeholder = { Text("e.g. image link (web/imgur) or YouTube video URL") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFEC4899),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_media_url_input")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Cancel", color = Color(0xFF94A3B8))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val finalCustomTopic = if (isCustomTopicMode && customTopicName.isNotBlank()) customTopicName.trim() else null
                            val resolvedMediaType = when {
                                mediaUrl.contains("youtu") || mediaUrl.endsWith(".mp4") || mediaUrl.endsWith(".webm") -> "video"
                                mediaUrl.isNotBlank() -> "image"
                                else -> ""
                            }
                            onSubmit(
                                selectedAniimo,
                                authorName,
                                selectedCategory,
                                title,
                                content,
                                recommendedItem,
                                recommendedAwakening,
                                finalCustomTopic,
                                mediaUrl.trim(),
                                resolvedMediaType
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                        modifier = Modifier.testTag("submit_new_discussion_post_btn")
                    ) {
                        Text(
                            text = "Publish Idea",
                            color = Color(0xFF0F172A),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}
