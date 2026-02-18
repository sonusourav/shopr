package com.propertyfinder.shopr.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.propertyfinder.shopr.data.model.GroceryItem
import com.propertyfinder.shopr.utils.DatabaseConstants

@Database(entities = [GroceryItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groceryDao(): GroceryDao
}

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DatabaseConstants.NAME
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
