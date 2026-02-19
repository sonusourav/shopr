package com.propertyfinder.shopr.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.ui.listscreen.SortOrder
import com.propertyfinder.shopr.ui.listscreen.viewmodel.FilterStatus
import com.propertyfinder.shopr.ui.res.categoryLabelRes
import com.propertyfinder.shopr.ui.res.sortOrderLabelRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterAndSortBar(
    filterCategory: GroceryCategory?,
    filterStatus: FilterStatus,
    sortOrder: SortOrder,
    onFilterChange: (GroceryCategory?) -> Unit,
    onFilterStatusChange: (FilterStatus) -> Unit,
    onSortChange: (SortOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SortIconButton(sortOrder = sortOrder, onSortChange = onSortChange)
        StatusSpinner(filterStatus = filterStatus, onFilterStatusChange = onFilterStatusChange)
        CategoryChips(filterCategory = filterCategory, onFilterChange = onFilterChange)
    }
}

@Composable
private fun CategoryChips(
    filterCategory: GroceryCategory?,
    onFilterChange: (GroceryCategory?) -> Unit
) {
    GroceryCategory.entries.forEach { cat ->
        FilterChip(
            selected = filterCategory == cat,
            onClick = { onFilterChange(if (filterCategory == cat) null else cat) },
            label = { Text(stringResource(categoryLabelRes(cat))) }
        )
    }
}

/** Spinner: one chip showing current status; tap to pick All / Completed / Pending. */
@Composable
private fun StatusSpinner(
    filterStatus: FilterStatus,
    onFilterStatusChange: (FilterStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val label = stringResource(
        when (filterStatus) {
            FilterStatus.ALL -> R.string.filter_all
            FilterStatus.COMPLETED -> R.string.filter_completed
            FilterStatus.PENDING -> R.string.filter_pending
        }
    )

    Box {
        FilterChip(
            selected = true,
            onClick = { expanded = true },
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(label)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            FilterStatus.entries.forEach { status ->
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(
                                when (status) {
                                    FilterStatus.ALL -> R.string.filter_all
                                    FilterStatus.COMPLETED -> R.string.filter_completed
                                    FilterStatus.PENDING -> R.string.filter_pending
                                }
                            )
                        )
                    },
                    onClick = {
                        onFilterStatusChange(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SortIconButton(
    sortOrder: SortOrder,
    onSortChange: (SortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Sort,
                contentDescription = stringResource(R.string.sort_chip_label, stringResource(sortOrderLabelRes(sortOrder))),
                modifier = Modifier.size(24.dp)
            )
        }
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
