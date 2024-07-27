package com.plbertheau.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.google.common.truth.Truth.assertThat
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.repository.WookieMovieListRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class WookieMovieListRepositoryImplTest {

    @Test
    fun `wookieMoviesPagingSource should map the PagingData`() = runTest {
        val moviePager = mock<Pager<Int, MovieResponse>>()
        val movie1 = movie1
        val movie2 = movie2
        val originalPagingData = PagingData.from(listOf(movie1, movie2))
        whenever(moviePager.flow).doReturn(flowOf(originalPagingData))

        val repository = WookieMovieListRepositoryImpl(moviePager)
        val result: PagingData<MovieResponse> = repository.wookieMoviesPagingSource().first()
        var expected : MovieResponse? = null
        var actual : MovieResponse? = null
        result.map { expected = it }
        originalPagingData.map { actual = it }
        assertThat(expected).isEqualTo(actual) // Since the map lambda is identity
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

private val movie2 = MovieResponse(
    backdrop =
    "https://wookie.codesubmit.io/static/backdrops/529c7379-ccfe-4003-a2ba-6c0a2ffd6704.jpg",
    cast = listOf(
        "Colin Firth",
        "Geoffrey Rush",
        "Helena Bonham Carter"
    ),
    classification = "18+",
    genres = listOf(
        "Biography",
        "Drama",
        "History"
    ),
    id = "529c7379-ccfe-4003-a2ba-6c0a2ffd6704",
    imdb_rating = 8.0,
    length = "1h 58min",
    overview =
    "The King's Speech tells the story of the man who became King George VI, the father of Queen Elizabeth II. After his brother abdicates, George ('Bertie') reluctantly assumes the throne. Plagued by a dreaded stutter and considered unfit to be king, Bertie engages the help of an unorthodox speech therapist named Lionel Logue. Through a set of unexpected techniques, and as a result of an unlikely friendship, Bertie is able to find his voice and boldly lead the country into war.",
    poster =
    "https://wookie.codesubmit.io/static/posters/529c7379-ccfe-4003-a2ba-6c0a2ffd6704.jpg",
    released_on = "2010-09-06T00:00:00",
    title = "The King's Speech"
)