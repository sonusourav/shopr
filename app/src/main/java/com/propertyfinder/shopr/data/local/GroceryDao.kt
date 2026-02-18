package com.propertyfinder.shopr.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.propertyfinder.shopr.data.model.GroceryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {

    @Query("SELECT * FROM grocery_items ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<GroceryItem>>

    @Query("SELECT * FROM grocery_items WHERE id = :id")
    suspend fun getItemById(id: Long): GroceryItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItem): Long

    @Update
    suspend fun update(item: GroceryItem)

    @Delete
    suspend fun delete(item: GroceryItem)

    @Query("DELETE FROM grocery_items WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE grocery_items SET isCompleted = :completed WHERE id = :id")
    suspend fun setCompleted(id: Long, completed: Boolean)
}
