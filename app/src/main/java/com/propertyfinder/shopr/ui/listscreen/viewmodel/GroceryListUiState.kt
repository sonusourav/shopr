package com.propertyfinder.shopr.ui.listscreen.viewmodel

import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.ui.listscreen.SortOrder
import com.propertyfinder.shopr.ui.listscreen.filterAndSort

data class GroceryListUiState(
    val rawItems: List<GroceryItem> = emptyList(),
    val filterCategory: GroceryCategory? = null,
    val filterStatus: FilterStatus = FilterStatus.ALL,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val itemNameInput: String = "",
    val selectedCategory: GroceryCategory = GroceryCategory.MILK,
    val itemBeingEdited: GroceryItem? = null
) {
    val items: List<GroceryItem> get() = rawItems
        .filter { when (filterStatus) {
            FilterStatus.ALL -> true
            FilterStatus.COMPLETED -> it.isCompleted
            FilterStatus.PENDING -> !it.isCompleted
        } }
        .filterAndSort(filterCategory, sortOrder)
}
