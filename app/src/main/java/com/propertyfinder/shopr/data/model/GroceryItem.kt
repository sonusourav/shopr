package com.propertyfinder.shopr.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_items", indices = [Index(value = ["category"])])
data class GroceryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: GroceryCategory,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
