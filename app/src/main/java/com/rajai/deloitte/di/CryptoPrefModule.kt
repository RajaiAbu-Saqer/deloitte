package com.rajai.deloitte.di

import android.content.Context
import android.content.ContextWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CryptoPrefModule {
    @Singleton
    @Provides
    fun provideContextWrapper(@ApplicationContext context: Context) = ContextWrapper(context)
}