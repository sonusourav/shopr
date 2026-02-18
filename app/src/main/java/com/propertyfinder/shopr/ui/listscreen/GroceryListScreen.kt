package com.propertyfinder.shopr.ui.listscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.ui.component.AddNewItemCard
import com.propertyfinder.shopr.ui.component.DeleteConfirmDialog
import com.propertyfinder.shopr.ui.component.EmptyState
import com.propertyfinder.shopr.ui.component.FilterAndSortBar
import com.propertyfinder.shopr.ui.component.GroceryItemsList
import com.propertyfinder.shopr.ui.component.GroceryListHeader
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListIntent
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroceryListScreen(
    modifier: Modifier = Modifier,
    viewModel: GroceryListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var itemToDelete by remember { mutableStateOf<GroceryItem?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        GroceryListHeader()
        Spacer(modifier = Modifier.height(16.dp))
        AddNewItemCard(
            itemName = uiState.itemNameInput,
            selectedCategory = uiState.selectedCategory,
            errorMessage = uiState.error?.let { stringResource(it.messageResId) },
            onItemNameChange = { viewModel.dispatch(GroceryListIntent.SetItemNameInput(it)) },
            onCategorySelect = { viewModel.dispatch(GroceryListIntent.SetSelectedCategory(it)) },
            onAddClick = { viewModel.dispatch(GroceryListIntent.AddItem) },
            onErrorDismiss = { viewModel.dispatch(GroceryListIntent.ClearError) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        FilterAndSortBar(
            filterCategory = uiState.filterCategory,
            sortOrder = uiState.sortOrder,
            onFilterChange = { viewModel.dispatch(GroceryListIntent.SetFilterCategory(it)) },
            onSortChange = { viewModel.dispatch(GroceryListIntent.SetSortOrder(it)) }
        )
        if (uiState.items.isEmpty()) {
            EmptyState(modifier = Modifier.weight(1f))
        } else {
            GroceryItemsList(
                modifier = Modifier.weight(1f),
                items = uiState.items,
                itemBeingEdited = uiState.itemBeingEdited,
                editName = uiState.editName,
                editCategory = uiState.editCategory,
                onEditNameChange = { viewModel.dispatch(GroceryListIntent.SetEditName(it)) },
                onEditCategoryChange = { viewModel.dispatch(GroceryListIntent.SetEditCategory(it)) },
                onSave = { viewModel.dispatch(GroceryListIntent.SaveEdit) },
                onCancel = { viewModel.dispatch(GroceryListIntent.CancelEdit) },
                onToggleComplete = { viewModel.dispatch(GroceryListIntent.ToggleCompleted(it)) },
                onEdit = { viewModel.dispatch(GroceryListIntent.StartEdit(it)) },
                onDelete = { itemToDelete = it }
            )
        }
    }

    itemToDelete?.let { item ->
        DeleteConfirmDialog(
            item = item,
            onConfirm = {
                viewModel.dispatch(GroceryListIntent.DeleteItem(item))
                itemToDelete = null
            },
            onDismiss = { itemToDelete = null }
        )
    }
}
