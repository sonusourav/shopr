package com.propertyfinder.shopr.data

import com.propertyfinder.shopr.data.model.GroceryCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GroceryRepositoryTest {

    private lateinit var dao: FakeGroceryDao
    private lateinit var repository: GroceryRepository

    @Before
    fun setup() {
        dao = FakeGroceryDao()
        repository = GroceryRepository(dao)
    }

    @Test
    fun addItem_withValidName_returnsIdAndClearsInput() = runTest {
        val result = repository.addItem("Milk", GroceryCategory.MILK)
        assertTrue(result.isSuccess)
        assertEquals(1L, result.getOrNull())
        val items = repository.allItems.first()
        assertEquals(1, items.size)
        assertEquals("Milk", items[0].name)
        assertEquals(GroceryCategory.MILK, items[0].category)
        assertFalse(items[0].isCompleted)
    }

    @Test
    fun addItem_withBlankName_fails() = runTest {
        val result = repository.addItem("   ", GroceryCategory.FRUITS)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun addItem_withEmptyName_fails() = runTest {
        val result = repository.addItem("", GroceryCategory.BREADS)
        assertTrue(result.isFailure)
    }

    @Test
    fun updateItem_withValidName_succeeds() = runTest {
        repository.addItem("Apple", GroceryCategory.FRUITS)
        val items = repository.allItems.first()
        val item = items.first()
        val result = repository.updateItem(item.copy(name = "Green Apple"))
        assertTrue(result.isSuccess)
        val updated = repository.allItems.first()
        assertEquals("Green Apple", updated.first().name)
    }

    @Test
    fun updateItem_withBlankName_fails() = runTest {
        repository.addItem("Bread", GroceryCategory.BREADS)
        val items = repository.allItems.first()
        val item = items.first()
        val result = repository.updateItem(item.copy(name = "  "))
        assertTrue(result.isFailure)
    }

    @Test
    fun toggleCompleted_flipsCompletionState() = runTest {
        repository.addItem("Eggs", GroceryCategory.MILK)
        val items = repository.allItems.first()
        val item = items.first()
        assertFalse(item.isCompleted)
        repository.toggleCompleted(item)
        val after = repository.allItems.first()
        assertTrue(after.first().isCompleted)
        repository.toggleCompleted(after.first())
        val afterAgain = repository.allItems.first()
        assertFalse(afterAgain.first().isCompleted)
    }

    @Test
    fun deleteItem_removesFromList() = runTest {
        repository.addItem("Item1", GroceryCategory.VEGETABLES)
        repository.addItem("Item2", GroceryCategory.MEATS)
        var items = repository.allItems.first()
        assertEquals(2, items.size)
        repository.deleteItem(items.first())
        items = repository.allItems.first()
        assertEquals(1, items.size)
        assertEquals("Item2", items.first().name)
    }
}
