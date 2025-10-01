package com.example.a2zfoods.Activity.DetailFood

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2zfoods.R

// This composable shows the description section of the food item
@Composable
fun DescriptionSection(description : String){
    Column {
        Text(
            text = "Details",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = description,
            fontSize = 16.sp,
            color = colorResource(R.color.black),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Buy 2 items for free delivery",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}