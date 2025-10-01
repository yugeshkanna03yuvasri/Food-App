package com.example.a2zfoods.Activity.Dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a2zfoods.R

// Data class for bottom menu items
data class BottomMenuItem(
    val label: String,
    val icon: Painter
)

// Function to prepare the menu list
@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    return listOf(
        BottomMenuItem(label = "Home", icon = painterResource(R.drawable.btn_1)),
        BottomMenuItem(label = "Cart", icon = painterResource(R.drawable.btn_2)),
        BottomMenuItem(label = "Profile", icon = painterResource(R.drawable.btn_5))
    )
}

// Bottom Bar Composable which has a home , cart and profile icon for navigation
@Composable
fun MyBottomBar(navController: NavController) {
    val bottomMenuItems = prepareBottomMenu()
    var selectedItem by remember { mutableStateOf(bottomMenuItems[0].label) }

    BottomAppBar(
        backgroundColor = colorResource(R.color.white),
        elevation = 3.dp
    ) {
        bottomMenuItems.forEach { bottomMenuItem ->
            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    when (bottomMenuItem.label) {
                        "Home" -> navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                        "Cart" -> navController.navigate("cart")
                        "Profile" -> navController.navigate("profile")
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(20.dp)
                    )
                },
                label = {
                    Text(bottomMenuItem.label)
                }
            )
        }
    }
}
