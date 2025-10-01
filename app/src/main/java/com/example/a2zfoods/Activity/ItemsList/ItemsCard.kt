package com.example.a2zfoods.Activity.ItemsList

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.a2zfoods.Activity.DetailFood.DetailEachFoodActivity
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.R

//this composable displays the variety of items in the particular category of the food
@Composable
fun ItemsList(items:List<FoodModel>)
{
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp , vertical = 16.dp)) {
        itemsIndexed(items){index , item ->
            Items(item = item)
        }
    }
}

@Composable
fun Items(item: FoodModel) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp, // 🔥 Shadow depth
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val intent = Intent(context, DetailEachFoodActivity::class.java).apply {
                    putExtra("object", item)
                }
                startActivity(context, intent, null)
            }
    ) {
        Row(
            modifier = Modifier
                .background(colorResource(R.color.white))
                .padding(10.dp)
        ) {
            FoodImage(item = item)
            FoodDetail(item = item)
        }
    }
}

@Composable
fun RowScope.FoodDetail(item: FoodModel){
    Column(modifier = Modifier
        .padding(start = 8.dp)
        .fillMaxWidth()
        .weight(1f)
    ) {
        Text(text = item.Title,
            color = colorResource(R.color.darkPurple),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp )
        )
        TimingRow(item.TimeValue)
        RatingBarRow(item.Star)
        PriceRow(item.Price)
    }
}

@Composable
fun PriceRow(price: Double) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)) {
        Text(text = "₹${price}",
            color = colorResource(R.color.darkPurple),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Text(text = "+ Add", color = Color.White, fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = colorResource(R.color.green),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 8.dp)
        )

    }
}

@Composable
fun RatingBarRow(star: Double) {
    Row(
        verticalAlignment = Alignment. CenterVertically,
        modifier = Modifier.padding (top = 8.dp)
    ) {
        Image(
            painter = painterResource (R.drawable.star),
            contentDescription = null,
            modifier = Modifier.padding (end = 8.dp)
        )
        Text(text= "$star", style = MaterialTheme.typography.bodyMedium )
    }
}

@Composable
fun TimingRow(timeValue: Int)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.time),
            contentDescription = null,
            modifier = Modifier.padding(end= 8.dp)
        )
        Text(text = "$timeValue min", style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable
fun FoodImage(item: FoodModel){
    AsyncImage(
        model = item.ImagePath,
        contentDescription = null,
        modifier = Modifier
            .size(130.dp)
            .background(colorResource(R.color.white), RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}