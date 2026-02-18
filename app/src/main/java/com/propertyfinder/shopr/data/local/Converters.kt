package com.propertyfinder.shopr.data.local

import androidx.room.TypeConverter
import com.propertyfinder.shopr.data.model.GroceryCategory

class Converters {

    @TypeConverter
    fun fromGroceryCategory(category: GroceryCategory): String = category.name

    @TypeConverter
    fun toGroceryCategory(value: String): GroceryCategory = GroceryCategory.valueOf(value)
}
