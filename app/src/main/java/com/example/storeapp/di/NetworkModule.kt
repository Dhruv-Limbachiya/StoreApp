package com.example.storeapp.di

import android.util.Log
import com.example.storeapp.BuildConfig
import com.example.storeapp.data.remote.StoreAPI
import com.example.storeapp.util.Constants.NETWORK_READ_TIME_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("Store App Logger", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            readTimeout(NETWORK_READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(NETWORK_READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    fun provideStoreApi(okHttpClient: OkHttpClient): StoreAPI {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
            baseUrl(BuildConfig.STORE_BASE_URL)
        }.build().create(StoreAPI::class.java)
    }
}