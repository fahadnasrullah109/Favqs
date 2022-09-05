package com.newstore.favqs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newstore.favqs.common.Converters
import com.newstore.favqs.data.local.AppDatabase.Companion.DATABASE_VERSION
import com.newstore.favqs.data.models.response.Quote
import com.newstore.favqs.data.models.response.User

@Database(
    entities = [User::class, Quote::class],
    version = DATABASE_VERSION, exportSchema = false,
)
@TypeConverters(
    Converters::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun quoteDao(): QuoteDao

    companion object {

        const val DB_NAME = "Favqs.db"
        const val DATABASE_VERSION = 1

        // to void duplicate instances of DB
        @Volatile
        private var instance: AppDatabase? = null
        fun init(context: Context, useInMemoryDb: Boolean = false): AppDatabase {
            if (instance != null && !useInMemoryDb) {
                return instance!!
            }
            synchronized(this) {
                instance = if (useInMemoryDb) {
                    Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                } else {
                    Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                }.fallbackToDestructiveMigration()
                    .build()
                return instance!!
            }
        }
    }
}