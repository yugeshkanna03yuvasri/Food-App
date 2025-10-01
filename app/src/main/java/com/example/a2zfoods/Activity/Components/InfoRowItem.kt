package com.example.a2zfoods.Activity.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2zfoods.R

// A reusable composable for a icon with a text
@Composable
fun InfoRowItem(
    iconRes: Int,
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = colorResource(R.color.darkPurple), // default purple
    fontSize: TextUnit = 15.sp // default text size
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            color = textColor
        )
    }
}
