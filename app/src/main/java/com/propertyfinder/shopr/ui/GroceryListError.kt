package com.propertyfinder.shopr.ui

import androidx.annotation.StringRes
import com.propertyfinder.shopr.R

enum class GroceryListError(@StringRes val messageResId: Int) {
    ADD_ITEM_FAILED(R.string.error_add_item),
    UPDATE_ITEM_FAILED(R.string.error_update_item)
}
