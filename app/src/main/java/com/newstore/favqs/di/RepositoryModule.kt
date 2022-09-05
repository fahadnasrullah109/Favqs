package com.newstore.favqs.di

import com.newstore.favqs.data.local.QuoteDao
import com.newstore.favqs.data.local.UserDao
import com.newstore.favqs.data.remote.RetroApiService
import com.newstore.favqs.data.repositories.QuoteRepository
import com.newstore.favqs.data.repositories.QuoteRepositoryImpl
import com.newstore.favqs.data.repositories.UsersRepository
import com.newstore.favqs.data.repositories.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideUserRepository(
        apiService: RetroApiService,
        userDao: UserDao
    ): UsersRepository {
        return UsersRepositoryImpl(
            apiService,
            userDao,
            Dispatchers.IO
        )
    }

    @ViewModelScoped
    @Provides
    fun provideQuoteRepository(
        apiService: RetroApiService,
        quoteDao: QuoteDao,
    ): QuoteRepository {
        return QuoteRepositoryImpl(
            apiService,
            quoteDao,
            Dispatchers.IO
        )
    }
}