package com.example.a2zfoods.Activity.Dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.a2zfoods.Domain.SpotlightModel
import com.example.a2zfoods.R

// This composable displays a horizontally scrollable carousel of spotlight cards with animations
@Composable
fun SpotlightCarousel(
    spotlights: List<SpotlightModel>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val itemWidthDp = 300.dp
    val itemSpacing = 12.dp

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(spotlights) { index, item ->
            SpotlightCard(
                model = item,
                index = index,
                listState = listState,
                cardWidth = itemWidthDp
            )
        }
    }
}

@Composable
fun SpotlightCard(
    model: SpotlightModel,
    index: Int,
    listState: LazyListState,
    cardWidth: Dp
) {
    // Compute animation values based on item position relative to viewport center
    val layoutInfo = listState.layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo
    val itemInfo = remember(layoutInfo) { visibleItems.find { it.index == index } }

    val targetScale = remember { mutableStateOf(0.94f) }
    val targetAlpha = remember { mutableStateOf(0.9f) }

    LaunchedEffect(itemInfo, layoutInfo) {
        if (itemInfo != null && layoutInfo.viewportEndOffset > layoutInfo.viewportStartOffset) {
            val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2f
            val itemCenter = itemInfo.offset + itemInfo.size / 2f
            val distance = kotlin.math.abs(viewportCenter - itemCenter)
            val maxDistance = viewportCenter.coerceAtLeast(1f)
            val fraction = (1f - (distance / maxDistance)).coerceIn(0f, 1f)

            targetScale.value = 0.94f + 0.06f * fraction
            targetAlpha.value = 0.85f + 0.15f * fraction
        } else {
            targetScale.value = 0.94f
            targetAlpha.value = 0.9f
        }
    }

    val scale by animateFloatAsState(targetValue = targetScale.value, animationSpec = tween(350))
    val alpha by animateFloatAsState(targetValue = targetAlpha.value, animationSpec = tween(350))

    var liked by rememberSaveable(model.id) { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(320.dp)
            .height(220.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            },
        shape = RoundedCornerShape(14.dp),
        elevation = 6.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            AsyncImage(
                model = model.imageUrl,
                contentDescription = model.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC000000)),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Heart button (top-right)
            IconButton(
                onClick = { liked = !liked },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(36.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (liked) "Liked" else "Like",
                    tint = if (liked) Color.Red else Color.White
                )
            }

            // Bottom row: Title+Subtitle (left) | Rating (right)
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = model.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = model.subtitle,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f", model.rating),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

