package com.propertyfinder.shopr.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem

@Composable
fun GroceryItemsList(
    items: List<GroceryItem>,
    itemBeingEdited: GroceryItem?,
    editName: String,
    editCategory: GroceryCategory,
    onEditNameChange: (String) -> Unit,
    onEditCategoryChange: (GroceryCategory) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onToggleComplete: (GroceryItem) -> Unit,
    onEdit: (GroceryItem) -> Unit,
    onDelete: (GroceryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { item ->
            if (itemBeingEdited?.id == item.id) {
                EditItemCard(
                    editName = editName,
                    editCategory = editCategory,
                    onEditNameChange = onEditNameChange,
                    onEditCategoryChange = onEditCategoryChange,
                    onSave = onSave,
                    onCancel = onCancel
                )
            } else {
                GroceryItemRow(
                    item = item,
                    onToggleComplete = { onToggleComplete(item) },
                    onEdit = { onEdit(item) },
                    onDelete = { onDelete(item) }
                )
            }
        }
    }
}
