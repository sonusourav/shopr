package com.propertyfinder.shopr.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGroceryCategory(category: GroceryCategory): String = category.name

    @TypeConverter
    fun toGroceryCategory(value: String): GroceryCategory = GroceryCategory.valueOf(value)
}
