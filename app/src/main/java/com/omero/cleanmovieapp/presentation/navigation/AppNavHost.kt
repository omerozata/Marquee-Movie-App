package com.omero.cleanmovieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omero.cleanmovieapp.presentation.detail.DetailScreen
import com.omero.cleanmovieapp.presentation.home.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onMovieClick = {movieId ->
                    navController.navigate(DetailRoute(movieId))
                }
            )
        }


        composable<DetailRoute> {
            DetailScreen(
                onBack = {navController.navigateUp()}
            )
        }

    }
}