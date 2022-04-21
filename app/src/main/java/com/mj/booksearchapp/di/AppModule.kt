package com.mj.booksearchapp.di

import android.content.Context
import com.mj.booksearchapp.data.database.dao.BookmarkDao
import com.mj.booksearchapp.data.network.KakaoApiService
import com.mj.booksearchapp.util.provider.DefaultResourcesProvider
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


//    @Singleton
//    @Provides
//    fun provideBookRepository(
//        kakaoApiService: KakaoApiService,
//        ioDispatcher: CoroutineDispatcher,
//        bookmarkDaoDao: BookmarkDao
//    ): ImageRepository = DefaultImageRepository(kakaoApiService, ioDispatcher, bookmarkDaoDao)

    @Singleton
    @Provides
    fun provideResourcesProvider(
        @ApplicationContext appContext: Context
    ): ResourcesProvider = DefaultResourcesProvider(appContext)





}