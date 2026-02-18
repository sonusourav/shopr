package com.propertyfinder.shopr.ui.res

import androidx.annotation.StringRes
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.ui.listscreen.SortOrder

@StringRes
fun sortOrderLabelRes(sortOrder: SortOrder): Int = when (sortOrder) {
    SortOrder.DEFAULT -> R.string.sort_default
    SortOrder.NAME_ASC -> R.string.sort_name_asc
    SortOrder.NAME_DESC -> R.string.sort_name_desc
    SortOrder.CATEGORY -> R.string.sort_category
    SortOrder.STATUS -> R.string.sort_status
}
