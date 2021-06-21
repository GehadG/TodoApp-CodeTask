package com.nordpass.todo.di

import android.content.Context
import com.nordpass.todo.BASE_URL
import com.nordpass.todo.data.TodoDao
import com.nordpass.todo.data.TodoDatabase
import com.nordpass.todo.data.dao.PaginationKeyDao
import com.nordpass.todo.networking.TodoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideTodoService(
    ): TodoService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoService::class.java)
    }

    @Provides
    @Singleton
    fun providesDB(@ApplicationContext context: Context): TodoDatabase {
        return TodoDatabase.invoke(context)
    }

    @Singleton
    @Provides
    fun providesKeysDao(appDataBase: TodoDatabase): PaginationKeyDao = appDataBase.paginationKeyDao

    @Singleton
    @Provides
    fun providesDao(appDataBase: TodoDatabase): TodoDao = appDataBase.todoDao
}