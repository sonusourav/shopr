package com.propertyfinder.shopr.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.ui.listscreen.SortOrder
import com.propertyfinder.shopr.ui.res.categoryLabelRes
import com.propertyfinder.shopr.ui.res.sortOrderLabelRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterAndSortBar(
    filterCategory: GroceryCategory?,
    sortOrder: SortOrder,
    onFilterChange: (GroceryCategory?) -> Unit,
    onSortChange: (SortOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        FilterChips(filterCategory = filterCategory, onFilterChange = onFilterChange)
        SortChip(sortOrder = sortOrder, onSortChange = onSortChange)
    }
}

@Composable
private fun FilterChips(
    filterCategory: GroceryCategory?,
    onFilterChange: (GroceryCategory?) -> Unit
) {
    FilterChip(
        selected = filterCategory == null,
        onClick = { onFilterChange(null) },
        label = { Text(stringResource(R.string.filter_all)) }
    )
    GroceryCategory.entries.forEach { cat ->
        FilterChip(
            selected = filterCategory == cat,
            onClick = { onFilterChange(if (filterCategory == cat) null else cat) },
            label = { Text(stringResource(categoryLabelRes(cat)).take(6)) }
        )
    }
}

@Composable
private fun SortChip(
    sortOrder: SortOrder,
    onSortChange: (SortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val sortLabel = stringResource(sortOrderLabelRes(sortOrder))

    Box {
        FilterChip(
            selected = false,
            onClick = { expanded = true },
            label = { Text(stringResource(R.string.sort_chip_label, sortLabel)) }
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SortOrder.entries.forEach { order ->
                DropdownMenuItem(
                    text = {
                        Text(stringResource(sortOrderLabelRes(order)))
                    },
                    onClick = {
                        onSortChange(order)
                        expanded = false
                    }
                )
            }
        }
    }
}
