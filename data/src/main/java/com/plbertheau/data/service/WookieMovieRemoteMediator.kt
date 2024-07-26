package com.plbertheau.data.service

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.plbertheau.data.Constants.DEFAULT_PAGE
import com.plbertheau.data.entity.RemoteKeys
import com.plbertheau.data.room.MovieLocalDB
import com.plbertheau.domain.model.MovieResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Remote mediator to handle pagination and network requests
 * The Paging library uses the database as a source of truth for the data that needs to be displayed in the UI.
 * Whenever we don't have any more data in the database, we need to request more from the network.
 * To help with this, Paging 3 defines the RemoteMediator abstract class,
 * with one method that needs to be implemented: load().
 * @param database: MovieLocalDB - the local database
 * @param api: WookieMovieApi - the remote API
 * This class returns a MediatorResult object, that can either be:
 *
 * Error - if we got an error while requesting data from the network.
 * Success - If we successfully got data from the network.
 *      Here, we also need to pass in a signal that tells whether more data can be loaded or not.
 *      For example, if the network response was successful but we got an empty list of repositories,
 *      it means that there is no more data to be loaded.
 *
 * source - https://developer.android.com/topic/libraries/architecture/paging/v3-network-db
 */
@OptIn(ExperimentalPagingApi::class)
class WookieMovieRemoteMediator @Inject constructor(
    private val database: MovieLocalDB,
    private val api: WookieMovieApi,
) : RemoteMediator<Int, MovieResponse>() {

    val movieDao = database.getMovieDao()
    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.SKIP_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieResponse>
    ): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.id
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = api.getMoviesList()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clear()
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                movieDao.insertAll(response.movies)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.page == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}