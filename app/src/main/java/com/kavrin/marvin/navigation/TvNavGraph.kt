package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kavrin.marvin.presentation.screens.tv.TvScreen
import com.kavrin.marvin.util.Constants

fun NavGraphBuilder.tvNavGraph(navHostController: NavHostController) {

    navigation(
        startDestination = TvScreen.Tv.route,
        route = Graph.Tv.route,
        arguments = listOf(
            navArgument(Constants.ARGUMENT_KEY_ID) {
                type = NavType.IntType
            }
        )

    ) {

        //// Tv Screen ////
        composable(
            route = TvScreen.Tv.route,
            arguments = listOf(
                navArgument(Constants.ARGUMENT_KEY_ID) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 500, delayMillis = 150)
                )
            },
            exitTransition = {
                fadeOut(
                    tween(durationMillis = 100, delayMillis = 2000)
                )
            },
            popEnterTransition = {
                fadeIn(
                    tween(durationMillis = 50)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(durationMillis = 700)
                )
            }
        ) {
            TvScreen(navHostController = navHostController)
        }


        ///// Season Screen /////


    }
}

sealed class TvScreen(val route: String) {

    object Tv : TvScreen("tv_screen/{id}") {
        fun passId(id: Int): String {
            return "tv_screen/${id}"
        }
    }

    object Season : TvScreen("season_screen/{id}") {
        fun passId(id: Int): String {
            return "season_screen/${id}"
        }
    }

}