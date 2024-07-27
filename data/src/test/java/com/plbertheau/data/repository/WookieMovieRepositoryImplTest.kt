package com.plbertheau.data.repository

import com.google.common.truth.Truth.assertThat
import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.repository.WookieMovieRepositoryImpl
import com.plbertheau.data.room.MovieDao
import com.plbertheau.data.room.MovieLocalDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WookieMovieRepositoryImplTest {

    @Test
    fun `getMovie should return Success with movie when movie exists in DB`() = runTest {
        val movieId = "1"
        val movieResponse = movie1
        val movieDao = mock<MovieDao>()
        val movieLocalDB = mock<MovieLocalDB>()
        whenever(movieLocalDB.getMovieDao()).doReturn(movieDao)
        whenever(movieDao.getById(movieId)).doReturn(movieResponse)

        val repository = WookieMovieRepositoryImpl(movieLocalDB)
        val result = repository.getMovie(movieId).first()

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isEqualTo(movieResponse)
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