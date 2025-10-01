package com.example.a2zfoods.Domain

data class SpotlightModel(
    val id: String,
    val title: String,      // e.g. "40% off on Pepperoni Pizza"
    val subtitle: String,   // e.g. "Limited time"
    val imageUrl: String,   // Firebase Storage download URL
    val rating: Float       // 0..5
)