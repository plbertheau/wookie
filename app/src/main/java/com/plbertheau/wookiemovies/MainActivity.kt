package com.plbertheau.wookiemovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plbertheau.wookiemovies.ui.screen.BottomNavItem
import com.plbertheau.wookiemovies.ui.screen.BottomNavigationBar
import com.plbertheau.wookiemovies.ui.screen.DetailScreen
import com.plbertheau.wookiemovies.ui.screen.MovieListScreen
import com.plbertheau.wookiemovies.ui.screen.SearchScreen
import com.plbertheau.wookiemovies.ui.theme.WookieMoviesTheme
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieDetailViewModel
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieListViewModel
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WookieMoviesTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(bottomBar = { BottomNavigationBar(navController) }) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = BottomNavItem.Home.route,
                    ) {
                        composable(BottomNavItem.Home.route) {
                            val viewModel = hiltViewModel<WookieMovieListViewModel>()
                            MovieListScreen(viewModel = viewModel) { id ->
                                navController.navigate("detail/$id")
                            }
                        }
                        composable(BottomNavItem.Search.route) {
                            val viewModel = hiltViewModel<WookieMovieSearchViewModel>()
                            SearchScreen(viewModel = viewModel){ id ->
                                navController.navigate("detail/$id")
                            }
                        }
                        composable(
                            route = "detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType }),
                        ) {
                            val viewModel = hiltViewModel<WookieMovieDetailViewModel>()
                            DetailScreen(viewModel = viewModel, snackbarHostState = snackbarHostState)
                        }
                    }
                }
            }
        }
    }
}