package com.plbertheau.data

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.room.MovieDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieDaoTest {

    private val movieDao: MovieDao = mock()

    @Test
    fun `insertAll should call insertAll on the DAO`() = runTest {
        val movies = listOf(movie1)
        movieDao.insertAll(movies)

        verify(movieDao).insertAll(movies)
    }

    @Test
    fun `getAll should return PagingSource from the DAO`() = runTest {
        val pagingSource = mock<PagingSource<Int, MovieResponse>>()
        whenever(movieDao.getAll()).doReturn(pagingSource)

        val result = movieDao.getAll()

        assertThat(result).isEqualTo(pagingSource)
    }

    @Test
    fun `clear should call clear on the DAO`() = runTest {
        movieDao.clear()

        verify(movieDao).clear()
    }

    @Test
    fun `getById should call getById on the DAO and return the result`() = runTest {
        val movieId = "1"
        val movie = movie1
        whenever(movieDao.getById(movieId)).doReturn(movie)

        val result = movieDao.getById(movieId)

        assertThat(result).isEqualTo(movie)
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