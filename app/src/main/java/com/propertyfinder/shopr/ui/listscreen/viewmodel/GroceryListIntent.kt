package com.propertyfinder.shopr.ui.listscreen.viewmodel

import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.ui.listscreen.SortOrder

/**
 * MVI: User intents (actions) that the View can send to the ViewModel.
 */
sealed class GroceryListIntent {
    data class SetItemNameInput(val value: String) : GroceryListIntent()
    data class SetSelectedCategory(val category: GroceryCategory) : GroceryListIntent()
    data class SetFilterCategory(val category: GroceryCategory?) : GroceryListIntent()
    data class SetSortOrder(val order: SortOrder) : GroceryListIntent()
    object AddItem : GroceryListIntent()
    data class ToggleCompleted(val item: GroceryItem) : GroceryListIntent()
    data class DeleteItem(val item: GroceryItem) : GroceryListIntent()
    data class StartEdit(val item: GroceryItem) : GroceryListIntent()
    data class SetEditName(val value: String) : GroceryListIntent()
    data class SetEditCategory(val category: GroceryCategory) : GroceryListIntent()
    object SaveEdit : GroceryListIntent()
    object CancelEdit : GroceryListIntent()
    object ClearError : GroceryListIntent()
}
