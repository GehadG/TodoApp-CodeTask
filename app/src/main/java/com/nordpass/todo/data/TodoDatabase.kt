package com.nordpass.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nordpass.todo.data.dao.PaginationKeyDao
import com.nordpass.todo.networking.models.ToDoResponse

@Database(
    entities = [ToDoResponse.Data::class,PaginationKey::class],
    version = 1, exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao
    abstract val paginationKeyDao: PaginationKeyDao
    companion object {
        @Volatile
        private var instance: TodoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                    context
                ).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "app_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}