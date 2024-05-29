package com.example.foodish

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageUrl: String,
    var isFavorite: Boolean = false
)
