package com.propertyfinder.shopr.ui.listscreen.viewmodel

import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.ui.listscreen.GroceryListError
import com.propertyfinder.shopr.ui.listscreen.SortOrder
import com.propertyfinder.shopr.ui.listscreen.filterAndSort

/**
 * Single source of truth for the screen. [rawItems] is the list from the data layer;
 * [items] is derived (filtered + sorted) for display.
 */
data class GroceryListUiState(
    val rawItems: List<GroceryItem> = emptyList(),
    val filterCategory: GroceryCategory? = null,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val itemNameInput: String = "",
    val selectedCategory: GroceryCategory = GroceryCategory.MILK,
    val error: GroceryListError? = null,
    val itemBeingEdited: GroceryItem? = null,
    val editName: String = "",
    val editCategory: GroceryCategory = GroceryCategory.MILK
) {
    /** Display list: raw items filtered by category and sorted. Derived from state only. */
    val items: List<GroceryItem> get() = rawItems.filterAndSort(filterCategory, sortOrder)
}
