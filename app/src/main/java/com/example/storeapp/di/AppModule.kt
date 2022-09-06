package com.example.storeapp.di

import android.content.Context
import androidx.room.Room
import com.example.storeapp.data.cache.db.StoreAppDatabase
import com.example.storeapp.data.remote.StoreAPI
import com.example.storeapp.repository.StoreAppRepository
import com.example.storeapp.repository.StoreAppRepositoryImpl
import com.example.storeapp.ui.adapter.ProductsAdapter
import com.example.storeapp.util.Constants.DATABASE_NAME
import com.example.storeapp.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context?): Context? {
        return context
    }

    @Singleton
    @Provides
    fun provideStoreAppDatabase(
        @ApplicationContext context: Context,
    ): StoreAppDatabase {
        return Room.databaseBuilder(
            context,
            StoreAppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideStoreAppRepository(
        storeAPI: StoreAPI,
        database: StoreAppDatabase,
        networkHelper: NetworkHelper
    ): StoreAppRepository {
        return StoreAppRepositoryImpl(
            storeAPI,
            database,
            networkHelper
        )
    }

    @Singleton
    @Provides
    fun provideProductsAdapter() = ProductsAdapter()
}