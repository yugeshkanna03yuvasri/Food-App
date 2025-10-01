package com.example.a2zfoods.Activity.Dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.a2zfoods.Activity.DetailFood.saveOrderToFirebase
import com.example.a2zfoods.Domain.FoodModel

/* The composable for viewing the cart and placing the order */

@Composable
fun OrderSection(cartItems: List<FoodModel> , onOrderPlaced: (List<FoodModel>, Double) -> Unit) {
    val cartState = remember { mutableStateListOf<FoodModel>().apply { addAll(cartItems) } }
    val totalAmount = cartItems.sumOf { it.Price * it.numberInCart }
    val roundedTotal = String.format("%.2f", totalAmount)
    var orderPlaced by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Text("🛒 Your Cart", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp)) //

        // Cart List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cartItems.size) { index ->
                val item = cartItems[index]
                CartItemCard(item)
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) //

        // Delivery info + Total
        Text("🚚 Delivery: Free (90% areas covered)", fontSize = 14.sp, color = Color.DarkGray)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Total: ₹$roundedTotal",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color(0xFF6200EE)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Animated Button
        val scale by animateFloatAsState(targetValue = if (orderPlaced) 1.05f else 1f)

        Button(
            onClick = { orderPlaced = true
                      onOrderPlaced(cartItems, totalAmount)
                saveOrderToFirebase(cartItems, totalAmount)},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03A9F4)),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .scale(scale),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Order Now", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Success Message
        AnimatedVisibility(
            visible = orderPlaced,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                "🎉 Order placed successfully!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CartItemCard(item: FoodModel) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            // ✅ Load actual food image from Firebase (using Coil)
            AsyncImage(
                model = item.ImagePath,
                contentDescription = item.Title,
                modifier = Modifier
                    .size(65.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.Title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("₹${item.Price} x ${item.numberInCart}", fontSize = 14.sp, color = Color.Gray)
            }

            Text(
                "₹${String.format("%.2f", item.Price * item.numberInCart)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF6200EE)
            )
        }
    }
}
