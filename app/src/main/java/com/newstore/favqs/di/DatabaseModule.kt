package com.newstore.favqs.di

import android.content.Context
import com.newstore.favqs.data.local.AppDatabase
import com.newstore.favqs.data.local.QuoteDao
import com.newstore.favqs.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.init(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(roomDB: AppDatabase): UserDao {
        return roomDB.userDao()
    }

    @Singleton
    @Provides
    fun provideQuoteDao(roomDB: AppDatabase): QuoteDao {
        return roomDB.quoteDao()
    }
}