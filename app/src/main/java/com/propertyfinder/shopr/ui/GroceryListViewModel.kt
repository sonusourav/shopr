package com.propertyfinder.shopr.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.propertyfinder.shopr.data.GroceryCategory
import com.propertyfinder.shopr.data.GroceryItem
import com.propertyfinder.shopr.data.GroceryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MVI: Single state flow updated only by (1) intents and (2) repository emissions.
 * No combine — the model is one source of truth; [GroceryListUiState.items] is derived there.
 */
class GroceryListViewModel(private val repository: GroceryRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(GroceryListUiState())

    /** Single state stream. Items are derived in [GroceryListUiState.items]. */
    val uiState: StateFlow<GroceryListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allItems.collect { items ->
                _uiState.update { it.copy(rawItems = items) }
            }
        }
    }

    fun dispatch(intent: GroceryListIntent) {
        when (intent) {
            is GroceryListIntent.SetItemNameInput ->
                _uiState.update { it.copy(itemNameInput = intent.value, error = null) }
            is GroceryListIntent.SetSelectedCategory ->
                _uiState.update { it.copy(selectedCategory = intent.category) }
            is GroceryListIntent.SetFilterCategory ->
                _uiState.update { it.copy(filterCategory = intent.category) }
            is GroceryListIntent.SetSortOrder ->
                _uiState.update { it.copy(sortOrder = intent.order) }
            GroceryListIntent.AddItem -> addItem()
            is GroceryListIntent.ToggleCompleted -> toggleCompleted(intent.item)
            is GroceryListIntent.DeleteItem -> deleteItem(intent.item)
            is GroceryListIntent.StartEdit ->
                _uiState.update {
                    it.copy(
                        itemBeingEdited = intent.item,
                        editName = intent.item.name,
                        editCategory = intent.item.category
                    )
                }
            is GroceryListIntent.SetEditName ->
                _uiState.update { it.copy(editName = intent.value) }
            is GroceryListIntent.SetEditCategory ->
                _uiState.update { it.copy(editCategory = intent.category) }
            GroceryListIntent.SaveEdit -> saveEdit()
            GroceryListIntent.CancelEdit ->
                _uiState.update {
                    it.copy(
                        itemBeingEdited = null,
                        editName = "",
                        editCategory = GroceryCategory.MILK,
                        error = null
                    )
                }
            GroceryListIntent.ClearError ->
                _uiState.update { it.copy(error = null) }
        }
    }

    private fun addItem() {
        viewModelScope.launch {
            val state = _uiState.value
            val result = repository.addItem(state.itemNameInput, state.selectedCategory)
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            itemNameInput = "",
                            selectedCategory = GroceryCategory.MILK,
                            error = null
                        )
                    }
                },
                onFailure = {
                    _uiState.update { it.copy(error = GroceryListError.ADD_ITEM_FAILED) }
                }
            )
        }
    }

    private fun toggleCompleted(item: GroceryItem) {
        viewModelScope.launch {
            repository.toggleCompleted(item)
        }
    }

    private fun deleteItem(item: GroceryItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
            if (_uiState.value.itemBeingEdited?.id == item.id) {
                _uiState.update { it.copy(itemBeingEdited = null) }
            }
        }
    }

    private fun saveEdit() {
        val state = _uiState.value
        val item = state.itemBeingEdited ?: return
        viewModelScope.launch {
            val result = repository.updateItem(
                item.copy(name = state.editName.trim(), category = state.editCategory)
            )
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            itemBeingEdited = null,
                            editName = "",
                            editCategory = GroceryCategory.MILK,
                            error = null
                        )
                    }
                },
                onFailure = {
                    _uiState.update { it.copy(error = GroceryListError.UPDATE_ITEM_FAILED) }
                }
            )
        }
    }
}
