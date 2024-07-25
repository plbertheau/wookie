package com.plbertheau.data.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.model.ResultResponse
import retrofit2.HttpException
import java.io.IOException

class WookieMoviesListPagingSource(private val api: WookieMovieApi) :
    PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {

            val nextPage = params.key ?: 1
            val videoList: ResultResponse =
                api.getMoviesList()
            LoadResult.Page(
                data = videoList.movies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = videoList.page?.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }
}