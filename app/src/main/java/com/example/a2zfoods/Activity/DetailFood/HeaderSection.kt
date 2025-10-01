package com.example.a2zfoods.Activity.DetailFood

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.Helper.previewFood
import com.example.a2zfoods.R

//This composable displays the header section of the food detail screen

@Composable
@Preview
fun HeaderSection(
    item: FoodModel = previewFood,
    onBackClick: () -> Unit = {}
){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        val (back, fav , mainImage , arc) = createRefs()
        Image(painter = rememberAsyncImagePainter(model = item.ImagePath),
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .fillMaxWidth()
                  .height(400.dp)
                  .clip(RoundedCornerShape(bottomStart = 30.dp , bottomEnd = 30.dp))
                  .constrainAs(mainImage){
                      top.linkTo(parent.top)
                      end.linkTo(parent.end)
                      start.linkTo(parent.start)
                  }
            )
            BackButton(onBackClick , Modifier.constrainAs(back){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })
        FavoriteButton(
            Modifier.constrainAs(fav) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )
        Image(painter = painterResource(R.drawable.arc),
            contentDescription = null,
            modifier = Modifier.constrainAs(arc)
            {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            })
    }

}

@Composable
private fun BackButton(onClick: () -> Unit , modifier: Modifier){
    Image(
        painter = painterResource(R.drawable.back),
        contentDescription = null,
        modifier = modifier
            .padding(start = 16.dp , top = 48.dp)
            .clickable{onClick()}
    )
}

@Composable
private fun FavoriteButton(
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }

    IconButton(
        onClick = { isFavorite = !isFavorite },
        modifier = modifier
            .padding(end = 16.dp, top = 48.dp)
            .size(40.dp) // Circle size
            .background(
                color = Color.White,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Red else Color.Black // 🔴 red when selected
        )
    }
}