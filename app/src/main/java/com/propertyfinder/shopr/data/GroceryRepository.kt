package com.propertyfinder.shopr.data

import com.propertyfinder.shopr.data.local.GroceryDao
import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.utils.ValidationMessages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroceryRepository(private val dao: GroceryDao) {

    val allItems: Flow<List<GroceryItem>> = dao.getAllItems()

    fun getItemById(id: Long): Flow<GroceryItem?> = dao.getAllItems().map { list ->
        list.find { it.id == id }
    }

    suspend fun addItem(name: String, category: GroceryCategory): Result<Long> = runCatching {
        val trimmed = name.trim()
        if (trimmed.isBlank()) throw IllegalArgumentException(ValidationMessages.ITEM_NAME_EMPTY)
        dao.insert(GroceryItem(name = trimmed, category = category))
    }

    suspend fun updateItem(item: GroceryItem): Result<Unit> = runCatching {
        val trimmed = item.name.trim()
        if (trimmed.isBlank()) throw IllegalArgumentException(ValidationMessages.ITEM_NAME_EMPTY)
        dao.update(item.copy(name = trimmed))
    }

    suspend fun deleteItem(item: GroceryItem) {
        dao.delete(item)
    }

    suspend fun deleteItemById(id: Long) {
        dao.deleteById(id)
    }

    suspend fun setCompleted(id: Long, completed: Boolean) {
        dao.setCompleted(id, completed)
    }

    suspend fun toggleCompleted(item: GroceryItem) {
        dao.setCompleted(item.id, !item.isCompleted)
    }
}
