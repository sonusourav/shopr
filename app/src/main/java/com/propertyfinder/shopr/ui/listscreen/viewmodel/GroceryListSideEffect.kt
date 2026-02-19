package com.propertyfinder.shopr.ui.listscreen.viewmodel

/**
 * One-time UI effects (toasts, navigation, etc.) emitted by the ViewModel.
 * Collected once by the UI; not part of state.
 */
sealed class GroceryListSideEffect {
    data class ItemAddedToast(val name: String) : GroceryListSideEffect()
    data class ItemRemovedToast(val name: String) : GroceryListSideEffect()
    data class ItemMarkedPurchasedToast(val name: String) : GroceryListSideEffect()
    data class AddItemFailedToast(val name: String) : GroceryListSideEffect()
    data class UpdateItemFailedToast(val name: String) : GroceryListSideEffect()
}
