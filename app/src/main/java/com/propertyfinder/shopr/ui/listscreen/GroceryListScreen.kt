package com.propertyfinder.shopr.ui.listscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.utils.showToast
import com.propertyfinder.shopr.ui.component.AddNewItemCard
import com.propertyfinder.shopr.ui.component.DeleteConfirmDialog
import com.propertyfinder.shopr.ui.component.EmptyState
import com.propertyfinder.shopr.ui.component.FilterAndSortBar
import com.propertyfinder.shopr.ui.component.GroceryItemsList
import com.propertyfinder.shopr.ui.component.GroceryListHeader
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListIntent
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListSideEffect
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroceryListScreen(
    modifier: Modifier = Modifier,
    viewModel: GroceryListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var itemToDelete by remember { mutableStateOf<GroceryItem?>(null) }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is GroceryListSideEffect.ItemAddedToast ->
                    context.showToast(R.string.toast_item_added, effect.name)
                is GroceryListSideEffect.ItemRemovedToast ->
                    context.showToast(R.string.toast_item_deleted, effect.name)
                is GroceryListSideEffect.ItemMarkedPurchasedToast ->
                    context.showToast(R.string.toast_item_marked_purchased, effect.name)
                is GroceryListSideEffect.AddItemFailedToast ->
                    context.showToast(R.string.error_add_item, effect.name)
                is GroceryListSideEffect.UpdateItemFailedToast ->
                    context.showToast(R.string.error_update_item, effect.name)
                is GroceryListSideEffect.ItemUpdatedToast ->
                    context.showToast(R.string.toast_item_updated, effect.name)
            }
        }
    }

    LaunchedEffect(uiState.filterCategory, uiState.filterStatus) {
        if (uiState.items.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

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
            onItemNameChange = { viewModel.dispatch(GroceryListIntent.SetItemNameInput(it)) },
            onCategorySelect = { viewModel.dispatch(GroceryListIntent.SetSelectedCategory(it)) },
            primaryButtonLabel = if (uiState.itemBeingEdited != null) stringResource(R.string.update_item) else stringResource(R.string.add_item),
            onPrimaryClick = if (uiState.itemBeingEdited != null) ({ viewModel.dispatch(GroceryListIntent.SaveEdit) }) else ({ viewModel.dispatch(GroceryListIntent.AddItem) })
        )
        Spacer(modifier = Modifier.height(8.dp))
        FilterAndSortBar(
            filterCategory = uiState.filterCategory,
            filterStatus = uiState.filterStatus,
            sortOrder = uiState.sortOrder,
            onFilterChange = { viewModel.dispatch(GroceryListIntent.SetFilterCategory(it)) },
            onFilterStatusChange = { viewModel.dispatch(GroceryListIntent.SetFilterStatus(it)) },
            onSortChange = { viewModel.dispatch(GroceryListIntent.SetSortOrder(it)) }
        )
        if (uiState.items.isEmpty()) {
            EmptyState(modifier = Modifier.weight(1f))
        } else {
            GroceryItemsList(
                modifier = Modifier.weight(1f),
                items = uiState.items,
                listState = listState,
                itemBeingEdited = uiState.itemBeingEdited,
                onToggleComplete = { viewModel.dispatch(GroceryListIntent.ToggleCompleted(it)) },
                onEdit = { viewModel.dispatch(GroceryListIntent.StartEdit(it)) },
                onDelete = { itemToDelete = it },
                onCancelEdit = { viewModel.dispatch(GroceryListIntent.CancelEdit) }
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
