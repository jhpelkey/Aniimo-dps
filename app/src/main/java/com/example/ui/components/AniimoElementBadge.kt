package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Element

@Composable
fun AniimoElementBadge(
    element: Element,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    compact: Boolean = false
) {
    val shape = RoundedCornerShape(percent = 50)
    Row(
        modifier = modifier
            .clip(shape)
            .background(element.glowColor)
            .border(1.dp, element.primaryColor.copy(alpha = 0.8f), shape)
            .padding(
                horizontal = if (compact) 8.dp else 10.dp,
                vertical = if (compact) 3.dp else 5.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 8.dp else 10.dp)
                .clip(CircleShape)
                .background(element.primaryColor)
        )
        if (showLabel) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = element.displayName,
                color = Color.White,
                fontSize = if (compact) 11.sp else 12.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun AniimoElementBadges(
    primaryElement: Element,
    secondaryElement: Element? = null,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    compact: Boolean = false
) {
    Row(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AniimoElementBadge(
            element = primaryElement,
            showLabel = showLabel,
            compact = compact
        )
        if (secondaryElement != null && secondaryElement != primaryElement) {
            AniimoElementBadge(
                element = secondaryElement,
                showLabel = showLabel,
                compact = compact
            )
        }
    }
}

