package com.example.a2zfoods.Activity.Dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2zfoods.R
import com.example.a2zfoods.ViewModel.MainViewModel
import com.example.a2zfoods.Domain.SpotlightModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardScreen()
        }
    }
}

  /*The MainScreen composable displays the home page with food categories and spotlight offers,
 using a grid layout with sections like Top Bar, Categories, and Bottom Bar.*/

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val categories by viewModel.loadCategory().observeAsState(emptyList())
    val showCategoryLoading = categories.isEmpty()

    // No Scaffold here with bottomBar
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white)),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item(span = { GridItemSpan(2) }) { TopBar() }

        item(span = { GridItemSpan(2) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Left line
                androidx.compose.material.Divider(
                    color = colorResource(R.color.lightgrey),
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "WHAT'S ON YOUR MIND?",
                    color = colorResource(R.color.darkPurple),
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )

                // Right line
                androidx.compose.material.Divider(
                    color = colorResource(R.color.lightgrey),
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
            }
        }


        item(span = { GridItemSpan(2) }) {
            CategorySection(
                categories = categories.toMutableStateList(),
                showCategoryLoading = showCategoryLoading
            )
        }

        item(span = { GridItemSpan(2) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                androidx.compose.material.Divider(
                    color = colorResource(R.color.lightgrey),
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "IN THE SPOTLIGHT",
                    color = colorResource(R.color.darkPurple),
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )

                androidx.compose.material.Divider(
                    color = colorResource(R.color.lightgrey),
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item(span = { GridItemSpan(2) }) {
            SpotlightCarousel(
                spotlights = listOf(
                    SpotlightModel(
                        id = "1",
                        title = "40% off on Pepperoni Pizza",
                        subtitle = "Today only • Hot & Fresh",
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/project162-foodapp.appspot.com/o/Pepperoni%20Lovers.jpg?alt=media&token=d9d172ff-eb4b-4149-bcc5-5690994a0f1f",
                        rating = 4.6f
                    ),
                    SpotlightModel(
                        id = "3",
                        title = "50% off on BBQ Chicken",
                        subtitle = "Chef's special",
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/project162-foodapp.appspot.com/o/Teriyaki%20Chicken%20Wings.jpg?alt=media&token=6934d5cf-5a2e-4383-bb96-0eae2598c804",
                        rating = 4.8f
                    ),
                    SpotlightModel(
                        id = "2",
                        title = "30% off on Margherita",
                        subtitle = "Limited stock",
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/project162-foodapp.appspot.com/o/Margherita%20Flatbread.jpg?alt=media&token=d79daa6d-a9e2-4ba8-900d-271eca183705",
                        rating = 4.3f
                    )
                )
            )
        }
    }
}


