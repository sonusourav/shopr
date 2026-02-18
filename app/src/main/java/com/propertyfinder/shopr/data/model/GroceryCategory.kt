package com.propertyfinder.shopr.data.model

/**
 * Predefined categories for grocery items.
 */
enum class GroceryCategory(val displayName: String) {
    MILK("Milk"),
    VEGETABLES("Vegetables"),
    FRUITS("Fruits"),
    BREADS("Breads"),
    MEATS("Meats");

    companion object {
        fun fromString(value: String?): GroceryCategory? =
            entries.find { it.name.equals(value, ignoreCase = true) }
    }
}
