package com.propertyfinder.shopr.ui.listscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.data.GroceryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroceryListViewModel(
    private val repository: GroceryRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(GroceryListUiState())

    private val _sideEffects = MutableSharedFlow<GroceryListSideEffect>(extraBufferCapacity = 1)

    val uiState: StateFlow<GroceryListUiState> = _uiState.asStateFlow()
    val sideEffects: SharedFlow<GroceryListSideEffect> = _sideEffects.asSharedFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            repository.allItems.collect { items ->
                _uiState.update { it.copy(rawItems = items) }
            }
        }
    }

    fun dispatch(intent: GroceryListIntent) {
        when (intent) {
            is GroceryListIntent.SetItemNameInput ->
                _uiState.update { it.copy(itemNameInput = intent.value) }
            is GroceryListIntent.SetSelectedCategory ->
                _uiState.update { it.copy(selectedCategory = intent.category) }
            is GroceryListIntent.SetFilterCategory ->
                _uiState.update { it.copy(filterCategory = intent.category) }
            is GroceryListIntent.SetFilterStatus ->
                _uiState.update { it.copy(filterStatus = intent.status) }
            is GroceryListIntent.SetSortOrder ->
                _uiState.update { it.copy(sortOrder = intent.order) }
            GroceryListIntent.AddItem -> addItem()
            is GroceryListIntent.ToggleCompleted -> toggleCompleted(intent.item)
            is GroceryListIntent.DeleteItem -> deleteItem(intent.item)
            is GroceryListIntent.StartEdit ->
                _uiState.update {
                    it.copy(
                        itemBeingEdited = intent.item,
                        itemNameInput = intent.item.name,
                        selectedCategory = intent.item.category
                    )
                }
            GroceryListIntent.SaveEdit -> saveEdit()
            GroceryListIntent.CancelEdit ->
                _uiState.update {
                    it.copy(
                        itemBeingEdited = null,
                        itemNameInput = "",
                        selectedCategory = GroceryCategory.MILK
                    )
                }
        }
    }

    private fun addItem() {
        viewModelScope.launch(ioDispatcher) {
            val state = _uiState.value
            val result = repository.addItem(state.itemNameInput, state.selectedCategory)
            val name = state.itemNameInput.trim().ifEmpty { "Item" }
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            itemNameInput = "",
                            selectedCategory = GroceryCategory.MILK,
                        )
                    }
                    _sideEffects.tryEmit(GroceryListSideEffect.ItemAddedToast(name))
                },
                onFailure = {
                    _sideEffects.tryEmit(GroceryListSideEffect.AddItemFailedToast(name))
                }
            )
        }
    }

    private fun toggleCompleted(item: GroceryItem) {
        viewModelScope.launch(ioDispatcher) {
            repository.toggleCompleted(item)
            _sideEffects.tryEmit(GroceryListSideEffect.ItemMarkedPurchasedToast(item.name))
        }
    }

    private fun deleteItem(item: GroceryItem) {
        viewModelScope.launch(ioDispatcher) {
            repository.deleteItem(item)
            _uiState.update { state ->
                var next = state
                if (state.itemBeingEdited?.id == item.id) {
                    next = next.copy(itemBeingEdited = null)
                }
                next
            }
            _sideEffects.tryEmit(GroceryListSideEffect.ItemRemovedToast(item.name))
        }
    }

    private fun saveEdit() {
        val state = _uiState.value
        val item = state.itemBeingEdited ?: return
        viewModelScope.launch(ioDispatcher) {
            val result = repository.updateItem(
                item.copy(name = state.itemNameInput.trim(), category = state.selectedCategory)
            )
            val updatedName = state.itemNameInput.trim()
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            itemBeingEdited = null,
                            itemNameInput = "",
                            selectedCategory = GroceryCategory.MILK
                        )
                    }
                    _sideEffects.tryEmit(GroceryListSideEffect.ItemUpdatedToast(updatedName))
                },
                onFailure = {
                    _sideEffects.tryEmit(GroceryListSideEffect.UpdateItemFailedToast(item.name))
                }
            )
        }
    }
}
