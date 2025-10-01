package com.example.a2zfoods.Activity.Dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.Helper.ManagmentCart
import com.example.a2zfoods.ViewModel.MainViewModel

  /*The DashboardScreen composable provides the app’s main navigation with a bottom bar,
  allowing users to switch between Home, Cart (with order management), and Profile sections.*/

@Composable
fun DashboardScreen() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { MainScreen() }          // your existing Home
            composable("cart") {
                val context = LocalContext.current
                val managmentCart = remember { ManagmentCart(context) }
                val cartItems = remember { mutableStateListOf<FoodModel>() }

                LaunchedEffect(Unit) {
                    cartItems.clear()
                    cartItems.addAll(managmentCart.getListCart())
                }
                OrderSection(
                    cartItems = cartItems,
                    onOrderPlaced = {items , total ->
                        viewModel.saveOrder(items, total)
                        managmentCart.clearCart()

                    }
                ) }
            composable("profile") { ProfileSection() }
        }
    }
}
