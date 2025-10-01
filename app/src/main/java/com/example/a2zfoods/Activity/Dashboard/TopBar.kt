package com.example.a2zfoods.Activity.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2zfoods.R
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

 /*The TopBar composable creates an animated header with the app name, profile icon,
  search bar with rotating hints, notification icon, and a sliding cravings message.*/
@Composable
fun TopBar() {
    // Fade-in animation for all elements
    val animatedAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    // 🔹 Cycling placeholder words
    val hints = listOf("Search \"chicken\"", "Search \"pizza\"", "Search \"burgers\"", "Search \"desserts\"")
    var currentHintIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) // change every 2s
            currentHintIndex = (currentHintIndex + 1) % hints.size
        }
    }

    // Background container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(310.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        //colorResource(R.color.darkgold),
                        colorResource(R.color.lightgold),
                        Color(0x00FFD700) // fade bottom
                    )
                ),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column {
            // Row 1 -> Shop name + Profile
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "A2Z Foods",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.darkPurple),
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(R.drawable.prof_image),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable { },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Row 2 -> Search bar + Bell
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(animatedAlpha.value),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var text by rememberSaveable { mutableStateOf("") }

                Surface(
                    shape = RoundedCornerShape(25.dp),
                    shadowElevation = 8.dp,
                    tonalElevation = 2.dp,
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                ) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = {
                            // 🔹 Animated changing placeholder
                            Text(
                                text = hints[currentHintIndex],
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        },
                        trailingIcon = {
                            Image(
                                painter = painterResource(R.drawable.search),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        shape = RoundedCornerShape(25.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            textColor = Color.DarkGray
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(colorResource(R.color.grey))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    painter = painterResource(R.drawable.bell_icon),
                    contentDescription = "Notifications",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(35.dp))
            AnimatedCravingsText()

        }
    }
}

@Composable
fun AnimatedCravingsText() {
    val offsetX = remember { Animatable(-300f) } // Start far left

    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = 0f, // move to center
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Text(
        text = "Cravings? We deliver happiness",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        color = colorResource(R.color.darkPurple),
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = offsetX.value.dp), // 👈 animated slide
        textAlign = TextAlign.Center
    )
    Text(
        text = "Order now to enjoy the delicious food",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        color = colorResource(R.color.darkPurple),
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = offsetX.value.dp), // 👈 animated slide
        textAlign = TextAlign.Center
    )
}



