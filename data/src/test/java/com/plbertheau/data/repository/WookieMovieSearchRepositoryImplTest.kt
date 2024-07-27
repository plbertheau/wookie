package com.plbertheau.data.repository

import com.google.common.truth.Truth.assertThat
import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.model.ResultResponse
import com.plbertheau.data.service.WookieMovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class WookieMovieSearchRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val api: WookieMovieApi = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchMovie should return Success with movies when API call is successful`() = runTest {
        val query = "star"
        val movieResponse = listOf(movie1)
        val response = ResultResponse(movies = listOf(movie1), page = 1)
        whenever(api.searchMovie(query)).doReturn(response)

        val repository = WookieMovieSearchRepositoryImpl(api)
        val result = repository.searchMovie(query)

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isEqualTo(movieResponse)
    }

    @Test
    fun `searchMovie should return Error when API call throws an exception`() = runTest {val query = "error"
        whenever(api.searchMovie(query)).doThrow(RuntimeException("Network error"))

        val repository = WookieMovieSearchRepositoryImpl(api)
        val result = repository.searchMovie(query)

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isEqualTo("Unable to fetch movie search results")
    }
}

private val movie1 = MovieResponse(
    backdrop =
    "https://wookie.codesubmit.io/static/backdrops/57a1aec1-e950-46b0-bbb2-2897b8c5233e.jpg",
    cast = listOf(
        "Bruce Willis",
        "Haley Joel Osment",
        "Toni Collette"
    ),
    classification = "13+",
    genres = listOf(
        "Drama",
        "Mystery",
        "Thriller"
    ),
    id = "57a1aec1-e950-46b0-bbb2-2897b8c5233e",
    imdb_rating = 8.1,
    length = "1h 47min",
    overview = "A psychological thriller about an eight year old boy named Cole Sear who believes he can see into the world of the dead. A child psychologist named Malcolm Crowe comes to Cole to help him deal with his problem, learning that he really can see ghosts of dead people.",
    poster =
    "https://wookie.codesubmit.io/static/posters/57a1aec1-e950-46b0-bbb2-2897b8c5233e.jpg",
    released_on = "1999-08-06T00:00:00",
    title = "The Sixth Sense"
)