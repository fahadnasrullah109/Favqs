package com.newstore.favqs.di

import com.newstore.favqs.data.repositories.QuoteRepository
import com.newstore.favqs.data.repositories.UsersRepository
import com.newstore.favqs.data.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideLoginUsecase(
        repository: UsersRepository
    ): LoginUsecase {
        return LoginUsecase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideLoggedInUserUsecase(
        repository: UsersRepository
    ): GetLoggedInUserUsecase {
        return GetLoggedInUserUsecase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideLogoutUserUsecase(repository: UsersRepository): LogoutUsecase {
        return LogoutUsecase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideRegisterUserUsecase(
        repository: UsersRepository
    ): RegisterUsecase {
        return RegisterUsecase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideSaveUserUsecase(
        repository: UsersRepository
    ): SaveUserUsecase {
        return SaveUserUsecase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideGetQuotesUsecase(
        repository: QuoteRepository
    ): GetQuotesUsecase {
        return GetQuotesUsecase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideGetRandomQuoteUsecase(
        repository: QuoteRepository
    ): GetRandomQuoteUsecase {
        return GetRandomQuoteUsecase(repository, Dispatchers.IO)
    }
}