package com.example.a2zfoods.Activity.DetailFood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a2zfoods.Activity.Components.InfoRowItem
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.Helper.previewFood
import com.example.a2zfoods.R

//This composable show the details like time of delivery , rating , calorie intake in the food
@Composable
fun RowDetail(
    item: FoodModel = previewFood,
    modifier: Modifier = Modifier
) {
    androidx.compose.material.Card(
        shape = RoundedCornerShape(50.dp),
        elevation = 8.dp,
        backgroundColor = Color.White,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(55.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            InfoRowItem(
                iconRes = R.drawable.time_color,
                text = "${item.TimeValue} min"
            )

            Spacer(modifier = Modifier.width(48.dp))

            InfoRowItem(
                iconRes = R.drawable.star,
                text = "${item.Star}"
            )

            Spacer(modifier = Modifier.width(48.dp))

            InfoRowItem(
                iconRes = R.drawable.flame,
                text = "${item.Calorie}"
            )
        }
    }
}
