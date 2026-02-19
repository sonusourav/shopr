package com.propertyfinder.shopr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.propertyfinder.shopr.data.model.GroceryItem

@Database(entities = [GroceryItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groceryDao(): GroceryDao
}
