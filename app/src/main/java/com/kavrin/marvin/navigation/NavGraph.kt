package com.kavrin.marvin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kavrin.marvin.presentation.screens.splash.SplashScreen
import com.kavrin.marvin.presentation.screens.welcome.WelcomeScreen
import com.kavrin.marvin.util.Constants.DETAILS_ARGUMENT_KEY
import com.kavrin.marvin.util.Constants.LIST_ARGUMENT_KEY

@Composable
fun SetupNavGraph(navHostController: NavHostController) {

	NavHost(
		navController = navHostController,
		startDestination = Screen.Welcome.route
	) {

		//// Splash Screen ////
		composable(route = Screen.Splash.route) {

			SplashScreen(navController = navHostController)

		}

		//// OnBoarding Screen ////
		composable(route = Screen.Welcome.route) {
			WelcomeScreen(navController = navHostController)
		}

		//// Home Screen ////
		composable(route = Screen.Home.route) {

		}

		//// Detail Screen ////
		composable(
			route = Screen.Detail.route,
			arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
				type = NavType.IntType
			})
		) {

		}

		//// Search Screen ////
		composable(route = Screen.Search.route) {

		}

		//// Library Screen ////
		composable(route = Screen.Library.route) {

		}

		//// List Screen ////
		composable(
			route = Screen.List.route,
			arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
				type = NavType.IntType
			})
		) {

		}


	}
}