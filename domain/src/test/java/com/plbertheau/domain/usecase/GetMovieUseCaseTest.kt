package com.plbertheau.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.repository.WookieMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetMovieUseCaseTest {

    @Test
    fun `invoke should return Result from repository`() = runTest {
        val repository = mock<WookieMovieRepository>()
        val movie = movie1
        val result = Result.Success(movie)
        whenever(repository.getMovie("1")).doReturn(flowOf(result))

        val useCase = GetMovieUseCase(repository)
        val resultFlow = useCase("1")
        val actualResult = resultFlow.first()

        assertThat(actualResult).isEqualTo(result)
        verify(repository).getMovie("1")
    }

    @Test
    fun `invoke should use Dispatchers Default`() = runTest {
        val repository = mock<WookieMovieRepository>()
        val movie = movie1
        val result = Result.Success(movie)
        whenever(repository.getMovie("1")).doReturn(flowOf(result))

        val useCase = GetMovieUseCase(repository)
        useCase("1")

        verify(repository).getMovie("1") // Verify interaction
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