package com.propertyfinder.shopr.ui.listscreen.viewmodel

sealed class GroceryListSideEffect {
    data class ItemAddedToast(val name: String) : GroceryListSideEffect()
    data class ItemRemovedToast(val name: String) : GroceryListSideEffect()
    data class ItemMarkedPurchasedToast(val name: String) : GroceryListSideEffect()
    data class AddItemFailedToast(val name: String) : GroceryListSideEffect()
    data class UpdateItemFailedToast(val name: String) : GroceryListSideEffect()
    data class ItemUpdatedToast(val name: String) : GroceryListSideEffect()
}
