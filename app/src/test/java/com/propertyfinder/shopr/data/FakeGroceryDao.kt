package com.propertyfinder.shopr.data

import com.propertyfinder.shopr.data.local.GroceryDao
import com.propertyfinder.shopr.data.model.GroceryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeGroceryDao : GroceryDao {

    private val _items = MutableStateFlow<List<GroceryItem>>(emptyList())
    private var nextId = 1L

    override fun getAllItems(): Flow<List<GroceryItem>> = _items.asStateFlow()

    override suspend fun getItemById(id: Long): GroceryItem? = _items.value.find { it.id == id }

    override suspend fun insert(item: GroceryItem): Long {
        val newItem = item.copy(id = nextId++)
        _items.value = _items.value + newItem
        return newItem.id
    }

    override suspend fun update(item: GroceryItem) {
        _items.value = _items.value.map { if (it.id == item.id) item else it }
    }

    override suspend fun delete(item: GroceryItem) {
        _items.value = _items.value.filter { it.id != item.id }
    }

    override suspend fun deleteById(id: Long) {
        _items.value = _items.value.filter { it.id != id }
    }

    override suspend fun setCompleted(id: Long, completed: Boolean) {
        _items.value = _items.value.map {
            if (it.id == id) it.copy(isCompleted = completed) else it
        }
    }
}
