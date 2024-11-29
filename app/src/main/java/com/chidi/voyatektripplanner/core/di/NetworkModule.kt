package com.chidi.voyatektripplanner.core.di

import com.chidi.voyatektripplanner.data.ApiService
import com.chidi.voyatektripplanner.data.RequestAdapterFactory
import com.chidi.voyatektripplanner.data.repository.BaseRepository
import com.chidi.voyatektripplanner.data.repository.MainRepository
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideRepository(repository: MainRepository): BaseRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://chidi-trip-planner.free.beeceptor.com/")
            .addCallAdapterFactory(RequestAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setLenient().create()
                )
            )
            .build().create(ApiService::class.java)
}