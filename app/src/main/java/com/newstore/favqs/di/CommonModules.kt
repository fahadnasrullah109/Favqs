package com.newstore.favqs.di

import com.newstore.favqs.common.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModules {
    @Singleton
    @Provides
    fun providesValidator() = Validator()
}