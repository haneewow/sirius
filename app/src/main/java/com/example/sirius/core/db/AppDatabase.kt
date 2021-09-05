package com.example.sirius.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sirius.core.db.AppDatabase.Companion.DB_V1
import com.example.sirius.domain.model.data.DeveloperNote

@Database(
    version = DB_V1, exportSchema = false,
    entities = [
        DeveloperNote::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        const val DB_NAME = "app_database"
        const val DB_V1 = 1
    }
}