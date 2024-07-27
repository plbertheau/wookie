package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.repository.WookieMovieRepository
import com.plbertheau.domain.usecase.GetMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WookieMovieDetailViewModelTest {

    @JvmField
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: WookieMovieRepository

    private lateinit var getMovieUseCase: GetMovieUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getMovieUseCase = GetMovieUseCase(repository)
    }

    @Test
    fun `test_movieResponse_emits_Loading_state`() = runTest {
        // Given
        val id = "ddjfkkells"
        val expectedResponse = Result.Loading(movieResponse)


        val mockFlow: Flow<Result<MovieResponse>> = flowOf(expectedResponse)
        val mockSavedStateHandle = mock<SavedStateHandle>()
        whenever(mockSavedStateHandle.get<String>("id")).thenReturn(id)
        whenever(getMovieUseCase.invoke(id)).thenReturn(mockFlow)
        val viewModel = WookieMovieDetailViewModel(mockSavedStateHandle, getMovieUseCase)
        // When
        val actualDataAfterUserResponse = viewModel.movieResponse
            .filterIsInstance<Result<MovieResponse>>()
            .take(2)
            .toList()
        // Then
        Assert.assertEquals(expectedResponse, actualDataAfterUserResponse[1])
    }


    @Test
    fun `test_movieResponse_emits_Success_state`() = runTest {
        // Given
        val id = "ddjfkkells"
        val movieResponse = movieResponse
        val expectedResponse = Result.Success(movieResponse)
        // Given

        val mockFlow: Flow<Result<MovieResponse>> = flowOf(expectedResponse)
        val mockSavedStateHandle = mock<SavedStateHandle>()
        whenever(mockSavedStateHandle.get<String>(any())).thenReturn(id)
        whenever(getMovieUseCase.invoke(id)).thenReturn(mockFlow)
        val viewModel = WookieMovieDetailViewModel(mockSavedStateHandle, getMovieUseCase)
        // When
        val actualDataAfterUserResponse = viewModel.movieResponse
            .filterIsInstance<Result<MovieResponse>>()
            .take(2)
            .toList()
        // Then
        Assert.assertEquals(expectedResponse, actualDataAfterUserResponse[1])
    }

    @Test
    fun `test_movieResponse_emits_Error_state`() = runTest {
        // Given
        val id = "test@example.com"
        val errorMessage = "An error occurred"
        val expectedResponse = Result.Error(errorMessage, movieResponse)

        val mockFlow: Flow<Result<MovieResponse>> = flowOf(expectedResponse)
        val mockSavedStateHandle = mock<SavedStateHandle>()
        whenever(mockSavedStateHandle.get<String>(any())).thenReturn(id)
        whenever(getMovieUseCase.invoke(id)).thenReturn(mockFlow)
        val viewModel = WookieMovieDetailViewModel(mockSavedStateHandle, getMovieUseCase)
        // When
        val actualDataAfterUserResponse = viewModel.movieResponse
            .filterIsInstance<Result<MovieResponse>>()
            .take(2)
            .toList()
        // Then
        Assert.assertEquals(expectedResponse, actualDataAfterUserResponse[1])
    }
}
private val movieResponse =
    MovieResponse(
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