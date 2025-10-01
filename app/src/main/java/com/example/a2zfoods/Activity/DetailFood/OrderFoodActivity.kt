package com.example.a2zfoods.Activity.DetailFood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a2zfoods.Activity.Dashboard.OrderSection
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.Helper.ManagmentCart
import com.example.a2zfoods.ViewModel.MainViewModel

class OrderFoodActivity : ComponentActivity() {
    private lateinit var managmentCart: ManagmentCart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managmentCart = ManagmentCart(this)

        setContent {
            val viewModel: MainViewModel = viewModel()

            val cartItems = remember { mutableStateListOf<FoodModel>() }
            LaunchedEffect(Unit) {
                cartItems.clear()
                cartItems.addAll(managmentCart.getListCart())
            }

            OrderSection(cartItems = cartItems,
                onOrderPlaced = {items , total ->
                    viewModel.saveOrder(items, total)
                    managmentCart.clearCart()
                    //cartItems.clear()
                })
        }
    }
}
