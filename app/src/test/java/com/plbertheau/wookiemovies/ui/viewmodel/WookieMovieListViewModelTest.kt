package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.domain.usecase.GetMovieListUseCase
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WookieMovieListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val getMovieListUseCase: GetMovieListUseCase = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `items should emit PagingData from use case`() = runTest {
        val pagingData = PagingData.from(moviesList)
        whenever(getMovieListUseCase()).doReturn(flowOf(pagingData))

        val viewModel = WookieMovieListViewModel(getMovieListUseCase)

        viewModel.items.test {
            var expected : MovieResponse? = null
            var actual : MovieResponse? = null
            awaitItem().map { expected = it }
            pagingData.map { actual = it }
            assertThat(expected).isEqualTo(actual)
            cancelAndIgnoreRemainingEvents()
        }
    }
}


private val moviesList =
    listOf(
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
        ),
        MovieResponse(
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
        ),
        MovieResponse(
            backdrop =
            "https://wookie.codesubmit.io/static/backdrops/be4b37a6-a8c3-476c-996f-3b9d2aaabfd5.jpg",
            cast = listOf(
                "Al Pacino",
                "Michelle Pfeiffer",
                "Steven Bauer"
            ),
            classification = "18+",
            genres = listOf(
                "Crime",
                "Drama"
            ),
            id = "be4b37a6-a8c3-476c-996f-3b9d2aaabfd5",
            imdb_rating = 8.3,
            length = "2h 50min",
            overview = "After getting a green card in exchange for assassinating a Cuban government official, Tony Montana stakes a claim on the drug trade in Miami. Viciously murdering anyone who stands in his way, Tony eventually becomes the biggest drug lord in the state, controlling nearly all the cocaine that comes through Miami. But increased pressure from the police, wars with Colombian drug cartels and his own drug-fueled paranoia serve to fuel the flames of his eventual downfall.",
            poster = "https://wookie.codesubmit.io/static/posters/be4b37a6-a8c3-476c-996f-3b9d2aaabfd5.jpg",
            released_on = "1983-12-08T00:00:00",
            title = "Scarface"
        )
    )
