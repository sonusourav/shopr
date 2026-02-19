package com.propertyfinder.shopr.ui

import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.GroceryRepository
import com.propertyfinder.shopr.data.FakeGroceryDao
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListIntent
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListSideEffect
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListViewModel
import com.propertyfinder.shopr.ui.listscreen.SortOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalCoroutinesApi::class)
class GroceryListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: GroceryRepository
    private lateinit var viewModel: GroceryListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = GroceryRepository(FakeGroceryDao())
        viewModel = GroceryListViewModel(repository, testDispatcher)
    }

    @Test
    fun setItemNameInput_updatesState() = runTest(testDispatcher) {
        viewModel.dispatch(GroceryListIntent.SetItemNameInput("Test"))
        advanceUntilIdle()
        assertEquals("Test", viewModel.uiState.value.itemNameInput)
    }

    @Test
    fun setSelectedCategory_updatesState() = runTest(testDispatcher) {
        viewModel.dispatch(GroceryListIntent.SetSelectedCategory(GroceryCategory.FRUITS))
        advanceUntilIdle()
        assertEquals(GroceryCategory.FRUITS, viewModel.uiState.value.selectedCategory)
    }

    @Test
    fun setFilterCategory_filtersItems() = runTest(testDispatcher) {
        repository.addItem("Milk", GroceryCategory.MILK)
        repository.addItem("Apple", GroceryCategory.FRUITS)
        repository.addItem("Carrot", GroceryCategory.VEGETABLES)
        advanceUntilIdle()
        viewModel.dispatch(GroceryListIntent.SetFilterCategory(GroceryCategory.FRUITS))
        advanceUntilIdle()
        assertEquals(1, viewModel.uiState.value.items.size)
        assertEquals("Apple", viewModel.uiState.value.items.first().name)
    }

    @Test
    fun setSortOrder_sortsItemsByCategory() = runTest(testDispatcher) {
        repository.addItem("Zebra", GroceryCategory.MEATS)
        repository.addItem("Apple", GroceryCategory.FRUITS)
        advanceUntilIdle()
        viewModel.dispatch(GroceryListIntent.SetSortOrder(SortOrder.CATEGORY))
        advanceUntilIdle()
        assertEquals("Apple", viewModel.uiState.value.items.first().name)
    }

    @Test
    fun addItem_withValidName_clearsInputAndAddsItem() = runTest(testDispatcher) {
        viewModel.dispatch(GroceryListIntent.SetItemNameInput("Bread"))
        viewModel.dispatch(GroceryListIntent.SetSelectedCategory(GroceryCategory.BREADS))
        viewModel.dispatch(GroceryListIntent.AddItem)
        advanceUntilIdle()
        assertEquals("", viewModel.uiState.value.itemNameInput)
        assertEquals(1, viewModel.uiState.value.items.size)
        assertEquals("Bread", viewModel.uiState.value.items.first().name)
    }

    @Test
    fun addItem_withValidName_emitsItemAddedToast() = runTest(testDispatcher) {
        val effects = mutableListOf<GroceryListSideEffect>()
        val collectJob = launch(testDispatcher) { viewModel.sideEffects.collect { effects.add(it) } }
        viewModel.dispatch(GroceryListIntent.SetItemNameInput("Bread"))
        viewModel.dispatch(GroceryListIntent.SetSelectedCategory(GroceryCategory.BREADS))
        viewModel.dispatch(GroceryListIntent.AddItem)
        advanceUntilIdle()
        assertTrue(effects.size >= 1)
        assertTrue(effects.any { it is GroceryListSideEffect.ItemAddedToast && it.name == "Bread" })
        collectJob.cancel()
    }

    @Test
    fun addItem_withBlankName_emitsAddItemFailedToast() = runTest(testDispatcher) {
        val effects = mutableListOf<GroceryListSideEffect>()
        val collectJob = launch(testDispatcher) { viewModel.sideEffects.collect { effects.add(it) } }
        viewModel.dispatch(GroceryListIntent.SetItemNameInput("   "))
        viewModel.dispatch(GroceryListIntent.AddItem)
        advanceUntilIdle()
        assertTrue(effects.any { it is GroceryListSideEffect.AddItemFailedToast })
        collectJob.cancel()
    }

    @Test
    fun saveEdit_success_emitsItemUpdatedToast() = runTest(testDispatcher) {
        repository.addItem("Apple", GroceryCategory.FRUITS)
        advanceUntilIdle()
        val item = viewModel.uiState.value.items.first()
        val effects = mutableListOf<GroceryListSideEffect>()
        val collectJob = launch(testDispatcher) { viewModel.sideEffects.collect { effects.add(it) } }
        viewModel.dispatch(GroceryListIntent.StartEdit(item))
        viewModel.dispatch(GroceryListIntent.SetItemNameInput("Green Apple"))
        viewModel.dispatch(GroceryListIntent.SaveEdit)
        advanceUntilIdle()
        assertTrue(effects.any { it is GroceryListSideEffect.ItemUpdatedToast && it.name == "Green Apple" })
        collectJob.cancel()
    }

    @Test
    fun deleteItem_emitsItemRemovedToast() = runTest(testDispatcher) {
        repository.addItem("Milk", GroceryCategory.MILK)
        advanceUntilIdle()
        val item = viewModel.uiState.value.items.first()
        val effects = mutableListOf<GroceryListSideEffect>()
        val collectJob = launch(testDispatcher) { viewModel.sideEffects.collect { effects.add(it) } }
        viewModel.dispatch(GroceryListIntent.DeleteItem(item))
        advanceUntilIdle()
        assertTrue(effects.any { it is GroceryListSideEffect.ItemRemovedToast && it.name == "Milk" })
        collectJob.cancel()
    }

    @Test
    fun toggleCompleted_emitsItemMarkedPurchasedToast() = runTest(testDispatcher) {
        repository.addItem("Eggs", GroceryCategory.MILK)
        advanceUntilIdle()
        val item = viewModel.uiState.value.items.first()
        val effects = mutableListOf<GroceryListSideEffect>()
        val collectJob = launch(testDispatcher) { viewModel.sideEffects.collect { effects.add(it) } }
        viewModel.dispatch(GroceryListIntent.ToggleCompleted(item))
        advanceUntilIdle()
        assertTrue(effects.any { it is GroceryListSideEffect.ItemMarkedPurchasedToast && it.name == "Eggs" })
        collectJob.cancel()
    }
}
