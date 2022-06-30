package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.MENU_ICON_SIZE
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.util.Constants.IMAGE_BASE_URL

@Composable
fun PosterWithIcon(
	posterPath: String?,
	itemId: Int?,
	modifier: Modifier = Modifier,
	onMenuIconClicked: (Int) -> Unit,
) {

	val painter = rememberAsyncImagePainter(
		model = "${IMAGE_BASE_URL}${posterPath}",
		placeholder = painterResource(id = R.drawable.placeholder),
		error = painterResource(id = R.drawable.placeholder)
	)


	Box(
		modifier = modifier,
		contentAlignment = Alignment.BottomCenter
	) {

		Image(
			modifier = Modifier
				.fillMaxSize(),
			painter = painter,
			contentDescription = stringResource(R.string.movie_poster),
			contentScale = ContentScale.Crop
		)

		Box(
			modifier = Modifier
				.background(
					brush = Brush.verticalGradient(
						listOf(
							Color.Black.copy(alpha = 0.03f),
							Color.Black.copy(alpha = ContentAlpha.medium),
						)
					)
				)
				.fillMaxWidth()
				.fillMaxHeight(0.3f)

		) {

			Row(
				modifier = Modifier
					.fillMaxSize()
					.padding(horizontal = SMALL_PADDING),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.End
			) {

				IconButton(
					onClick = {
						itemId?.let {
							onMenuIconClicked(it)
						}
					}
				) {

					Icon(
						modifier = Modifier
							.size(MENU_ICON_SIZE),
						imageVector = Icons.Default.Menu,
						contentDescription = stringResource(R.string.menu_icon),
						tint = Color.White
					)
				}
			}

		}
	}
}

@Preview
@Composable
fun PosterWithRatingPrev() {
	PosterWithIcon(
		posterPath = null,
		itemId = 1,
		onMenuIconClicked = {},
	)
}