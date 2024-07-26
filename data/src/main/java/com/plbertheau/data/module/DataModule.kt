package com.plbertheau.data.module

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.plbertheau.data.Constants
import com.plbertheau.data.Constants.BASE_URL
import com.plbertheau.data.repository.WookieMovieListRepositoryImpl
import com.plbertheau.data.repository.WookieMovieRepositoryImpl
import com.plbertheau.data.repository.WookieMovieSearchRepositoryImpl
import com.plbertheau.data.room.MovieLocalDB
import com.plbertheau.data.service.WookieMovieApi
import com.plbertheau.data.service.WookieMovieRemoteMediator
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieListRepository
import com.plbertheau.domain.repository.WookieMovieRepository
import com.plbertheau.domain.repository.WookieMovieSearchRepository
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
        moviePager: Pager<Int, MovieResponse>
    ): WookieMovieListRepository {
        return WookieMovieListRepositoryImpl(
            moviePager = moviePager
        )
    }

    @Provides
    @Singleton
    fun provideWookieMovieSearchRepository(api: WookieMovieApi
    ): WookieMovieSearchRepository {
        return WookieMovieSearchRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideWookieMovieRepository(
        movieLocalDB: MovieLocalDB,
    ): WookieMovieRepository {
        return WookieMovieRepositoryImpl(
            movieLocalDB = movieLocalDB
        )
    }


    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieLocalDB {
        return Room.databaseBuilder(
            context,
            MovieLocalDB::class.java,
            Constants.DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()
    }


    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMoviePager(
        movieDatabase: MovieLocalDB,
        api: WookieMovieApi,
    ): Pager<Int, MovieResponse> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = 10
            ),
            remoteMediator = WookieMovieRemoteMediator(
                database = movieDatabase,
                api = api,
            ),
            pagingSourceFactory = { movieDatabase.getMovieDao().getAll() },
        )
    }
}