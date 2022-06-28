package com.kavrin.marvin.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kavrin.marvin.R
import com.kavrin.marvin.navigation.Screen
import com.kavrin.marvin.ui.theme.AntiqueBrass
import com.kavrin.marvin.ui.theme.BrightMaroon
import com.kavrin.marvin.ui.theme.SilverPink
import com.kavrin.marvin.ui.theme.splashFont

@Composable
fun SplashScreen(
	navController: NavHostController,
	splashViewModel: SplashViewModel = hiltViewModel()
) {

	val onBoardingState = splashViewModel.onBoardingCompleted.collectAsState()

	val animAlpha = remember { Animatable(initialValue = 0f) }
	LaunchedEffect(key1 = true) {
		animAlpha.animateTo(
			targetValue = 1f,
			animationSpec = tween(
				durationMillis = 1000,
				delayMillis = 200
			)
		)
		splashViewModel.saveGenres()

		navController.popBackStack()
		if (onBoardingState.value)
			navController.navigate(route = Screen.Home.route)
		else
			navController.navigate(route = Screen.Welcome.route)

	}

	Splash(animAlpha = animAlpha.value)
}

@Composable
fun Splash(animAlpha: Float = 1f) {

	if (isSystemInDarkTheme()) {
		//// Text Container ////
		Box(
			modifier = Modifier
				.background(color = Color.Black)
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			//// Text ////
			Text(
				modifier = Modifier
					.alpha(alpha = animAlpha),
				color = Color.White,
				text = stringResource(R.string.marvin),
				fontFamily = splashFont,
				fontWeight = FontWeight.Normal,
				fontSize = MaterialTheme.typography.h3.fontSize
			)
		}
	} else {

		//// Text Container ////
		Box(
			modifier = Modifier
				.background(
					brush = Brush.verticalGradient(colors = listOf(SilverPink, AntiqueBrass))
				)
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {

			//// Text ////
			Text(
				modifier = Modifier
					.alpha(alpha = animAlpha),
				color = BrightMaroon,
				text = stringResource(R.string.marvin),
				fontFamily = splashFont,
				fontWeight = FontWeight.Normal,
				fontSize = MaterialTheme.typography.h3.fontSize
			)
		}
	}

}

@Preview
@Composable
fun SplashPrev() {
	Splash()
}