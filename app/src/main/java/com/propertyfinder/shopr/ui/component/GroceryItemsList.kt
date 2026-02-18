package com.propertyfinder.shopr.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.data.model.GroceryItem

@Composable
fun GroceryItemsList(
    items: List<GroceryItem>,
    listState: LazyListState,
    itemBeingEdited: GroceryItem?,
    onToggleComplete: (GroceryItem) -> Unit,
    onEdit: (GroceryItem) -> Unit,
    onDelete: (GroceryItem) -> Unit,
    onCancelEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { item ->
            GroceryItemRow(
                item = item,
                isEditing = itemBeingEdited?.id == item.id,
                onToggleComplete = { onToggleComplete(item) },
                onEdit = { onEdit(item) },
                onDelete = { onDelete(item) },
                onCancelEdit = onCancelEdit
            )
        }
    }
}
