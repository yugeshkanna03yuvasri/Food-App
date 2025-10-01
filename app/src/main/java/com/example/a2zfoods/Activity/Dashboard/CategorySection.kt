package com.example.a2zfoods.Activity.Dashboard

import android.content.Intent
import com.example.a2zfoods.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.a2zfoods.Activity.ItemsList.ItemsListActivity
import com.example.a2zfoods.Domain.CategoryModel

// This composable has the categories of the dishes in the app
@Composable
fun CategorySection(
    categories: SnapshotStateList<CategoryModel>, showCategoryLoading: Boolean

){
    if(showCategoryLoading)
    {
        Box(modifier = Modifier . fillMaxWidth()
            .height(400.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }
    else{
        val rows = categories.chunked(3)
        val context = LocalContext.current

        Column(modifier = Modifier. fillMaxWidth()) {
            rows.forEach { row ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEachIndexed {index, categoryModel->
                        CategoryItem(
                            category = categoryModel,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp),
                            onItemClick = {
                                val intent = Intent(context, ItemsListActivity::class.java ).apply {
                                    putExtra("id" , categoryModel.Id.toString())
                                    putExtra("title", categoryModel.Name)
                                }
                                  startActivity(context , intent , null)
                            }
                        )
                    }
                    if(row.size<3)
                    {
                        repeat(3-row.size){
                            Spacer(modifier = Modifier.weight(1f))

                        }
                    }
                }
            }
        }

    }

}

@Composable
fun CategoryItem(category: CategoryModel, modifier: Modifier= Modifier, onItemClick:() -> Unit)
{
    Column(modifier= modifier
        .fillMaxWidth()
        .clickable(onClick = onItemClick)
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = category.ImagePath,
            contentDescription = null,
            modifier= Modifier.size(100.dp)
        )
        Text(
            text= category.Name,
            color = colorResource(R.color.darkPurple),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier= Modifier.padding(top = 8.dp)
        )
    }
}