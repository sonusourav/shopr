package com.propertyfinder.shopr.di

import com.propertyfinder.shopr.data.local.AppDatabase
import com.propertyfinder.shopr.utils.DatabaseConstants
import com.propertyfinder.shopr.data.local.GroceryDao
import com.propertyfinder.shopr.data.GroceryRepository
import com.propertyfinder.shopr.ui.listscreen.viewmodel.GroceryListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import androidx.room.Room

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DatabaseConstants.NAME
        ).build()
    }

    single<GroceryDao> { get<AppDatabase>().groceryDao() }

    single { GroceryRepository(get()) }

    viewModel { GroceryListViewModel(get()) }
}
