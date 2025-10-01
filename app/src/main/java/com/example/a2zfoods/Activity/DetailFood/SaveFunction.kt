package com.example.a2zfoods.Activity.DetailFood

import android.util.Log
import com.example.a2zfoods.Domain.FoodModel
import com.google.firebase.database.FirebaseDatabase

fun saveOrderToFirebase(cartItems: List<FoodModel>, total: Double) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("orders")

    val order = hashMapOf(
        "items" to cartItems.map { item ->
            mapOf(
                "title" to item.Title,
                "quantity" to item.numberInCart,
                "price" to item.Price
            )
        },
        "total" to total,
        "timestamp" to System.currentTimeMillis()
    )

    ref.push().setValue(order)
        .addOnSuccessListener {
            Log.d("Firebase", "✅ Order saved successfully")
        }
        .addOnFailureListener { e ->
            Log.e("Firebase", "❌ Failed to save order", e)
        }
}
