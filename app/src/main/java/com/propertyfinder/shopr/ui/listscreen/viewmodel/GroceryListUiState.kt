package com.propertyfinder.shopr.ui.listscreen.viewmodel

import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.ui.listscreen.SortOrder
import com.propertyfinder.shopr.ui.listscreen.filterAndSort

/**
 * Single source of truth for the screen. [rawItems] is the list from the data layer;
 * [items] is derived (filtered + sorted) for display.
 */
data class GroceryListUiState(
    val rawItems: List<GroceryItem> = emptyList(),
    val filterCategory: GroceryCategory? = null,
    val filterStatus: FilterStatus = FilterStatus.ALL,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val itemNameInput: String = "",
    val selectedCategory: GroceryCategory = GroceryCategory.MILK,
    val itemBeingEdited: GroceryItem? = null
) {
    /** Display list: by status, then by category (null = all), then sorted. */
    val items: List<GroceryItem> get() = rawItems
        .filter { when (filterStatus) {
            FilterStatus.ALL -> true
            FilterStatus.COMPLETED -> it.isCompleted
            FilterStatus.PENDING -> !it.isCompleted
        } }
        .filterAndSort(filterCategory, sortOrder)
}
