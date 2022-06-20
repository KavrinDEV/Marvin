package com.kavrin.marvin.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.ui.theme.*



@Composable
fun MovieCarousel(
	trendingItems: LazyPagingItems<MovieAndTrending>,
	onItemClicked: (Int) -> Unit,
	onMenuIconClicked: (Int) -> Unit,
) {
	val pagerState = rememberPagerState(
		initialPage = 1
	)

	Column(
		modifier = Modifier
			.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		HorizontalPager(
			count = trendingItems.itemCount,
			state = pagerState,
			contentPadding = PaddingValues(
				horizontal = CAROUSEL_HORIZONTAL_PADDING,
				vertical = CAROUSEL_VERTICAL_PADDING
			),
		) { page ->
			val item = trendingItems[page]

			val scale = remember {
				Animatable(
					initialValue = 1f
				)
			}
			LaunchedEffect(key1 = pagerState.currentPage) {
				if (page == pagerState.currentPage) {
					scale.animateTo(
						targetValue = 1.1f,
						animationSpec = tween(durationMillis = 700)
					)
				} else {
					scale.animateTo(
						targetValue = 1f,
						animationSpec = tween(durationMillis = 700)
					)
				}
			}

			CarouselItem(
				posterPath = item?.movie?.posterPath,
				rating = item?.movie?.voteAverage,
				voteCount = item?.movie?.voteCount,
				itemId = item?.movie?.movieId,
				scale = scale.value,
				onItemClicked = onItemClicked,
				onMenuIconClicked = onMenuIconClicked
			)
		}

		Spacer(modifier = Modifier.height(LARGE_PADDING))

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(48.dp)
				.padding(horizontal = LARGE_PADDING),
			horizontalArrangement = Arrangement.Center
		) {
			AnimatedVisibility(
				visible = !pagerState.isScrollInProgress,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				trendingItems[pagerState.currentPage]?.movie?.title?.let {
					Text(
						text = it,
						fontFamily = fonts,
						fontSize = MaterialTheme.typography.h6.fontSize,
						fontWeight = FontWeight.Bold,
						textAlign = TextAlign.Center
					)
				}

			}
		}

		Spacer(modifier = Modifier.height(LARGE_PADDING))

		HorizontalPagerIndicator(
			pagerState = pagerState,
			activeColor = BrightMaroon,
			inactiveColor = LightGray,
		)
	}
}


@Preview
@Composable
fun CarouselItemPreview() {
	CarouselItem(
		posterPath = "",
		rating = 6.6,
		voteCount = 12356,
		itemId = 4,
		scale = 1f,
		onItemClicked = {},
		onMenuIconClicked = {}
	)
}


















