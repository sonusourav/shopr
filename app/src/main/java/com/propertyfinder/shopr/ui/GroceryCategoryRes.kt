package com.propertyfinder.shopr.ui

import androidx.annotation.StringRes
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.data.GroceryCategory

@StringRes
fun categoryLabelRes(category: GroceryCategory): Int = when (category) {
    GroceryCategory.MILK -> R.string.category_milk
    GroceryCategory.VEGETABLES -> R.string.category_vegetables
    GroceryCategory.FRUITS -> R.string.category_fruits
    GroceryCategory.BREADS -> R.string.category_breads
    GroceryCategory.MEATS -> R.string.category_meats
}

fun categoryEmoji(category: GroceryCategory): String = when (category) {
    GroceryCategory.MILK -> "🥛"
    GroceryCategory.VEGETABLES -> "🥕"
    GroceryCategory.FRUITS -> "🍎"
    GroceryCategory.BREADS -> "🍞"
    GroceryCategory.MEATS -> "🥩"
}
