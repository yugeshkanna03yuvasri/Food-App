package com.example.a2zfoods.Activity.DetailFood

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.ViewModel.MainViewModel
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

//This composable shows a horizontal list of recommended food items with images.

@Composable
fun RecommendationList(){
    val viewModel : MainViewModel= viewModel()
    val foods = remember { mutableStateListOf<FoodModel>() }
    var showLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadBestFood().observeForever {
            foods.clear()
            foods.addAll(it)
            showLoading= false

        }
    }
    if(showLoading){
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
           contentAlignment = Alignment.Center ){
            CircularProgressIndicator()
        }
    }
    else{
        LazyRow(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(start = 16.dp , end = 16.dp , top = 8.dp)
        ) {
            items(foods.size){ index->
                AsyncImage(
                    model = foods[index].ImagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clickable{
                            val intent = Intent(context , DetailEachFoodActivity::class.java).apply {
                                putExtra("object", foods[index])
                            }
                            startActivity(context , intent , null)
                        }
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop

                )
            }
        }
    }
}