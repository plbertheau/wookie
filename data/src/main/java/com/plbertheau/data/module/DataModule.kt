package com.plbertheau.data.module

import android.content.Context
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.plbertheau.data.Constants.BASE_URL
import com.plbertheau.data.repository.WookieMovieListRepositoryImpl
import com.plbertheau.data.service.WookieMovieApi
import com.plbertheau.domain.repository.WookieMovieListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(OkHttpProfilerInterceptor())
        return builder
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideWookieMovieApi(okHttpClient: OkHttpClient): WookieMovieApi {
        val api: WookieMovieApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(WookieMovieApi::class.java)
        }
        return api
    }

    @Provides
    @Singleton
    fun provideWookieMovieListRepository(
        wookieMovieApi: WookieMovieApi,
    ): WookieMovieListRepository {
        return WookieMovieListRepositoryImpl(
            wookieMovieApi = wookieMovieApi
        )
    }
}