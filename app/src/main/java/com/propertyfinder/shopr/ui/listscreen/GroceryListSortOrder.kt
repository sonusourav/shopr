package com.propertyfinder.shopr.ui.listscreen

import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem

enum class SortOrder(val label: String) {

    DEFAULT("Default"),
    NAME_ASC("A–Z"),
    NAME_DESC("Z–A"),
    CATEGORY("Category"),
    STATUS("Status");

    fun comparator(): Comparator<GroceryItem> = when (this) {
        DEFAULT -> compareByDescending(GroceryItem::createdAt)
        NAME_ASC -> compareBy { it.name.lowercase() }
        NAME_DESC -> compareByDescending { it.name.lowercase() }
        CATEGORY -> compareBy({ it.category.ordinal }, { it.name.lowercase() })
        STATUS -> compareBy(GroceryItem::isCompleted).thenBy { it.name.lowercase() }
    }
}

/**
 * Filters by category (if set) then sorts. Keeps combine block in ViewModel minimal.
 */
fun List<GroceryItem>.filterAndSort(
    filterCategory: GroceryCategory?,
    sortOrder: SortOrder
): List<GroceryItem> {
    val filtered = filterCategory?.let { cat -> filter { it.category == cat } } ?: this
    return filtered.sortedWith(sortOrder.comparator())
}
