package com.omero.cleanmovieapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.omero.cleanmovieapp.presentation.components.placeHolderColorFor


@Composable
fun MoviePoster(
    posterUrl: String?,
    title: String,
    modifier: Modifier = Modifier,
    fallbackTextStyle: TextStyle = MaterialTheme.typography.displaySmall,
    cornerRadius: Dp = 8.dp
) {
    val fallback = @Composable {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title.take(1).uppercase(),
                style = fallbackTextStyle,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }


    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(placeHolderColorFor(title))
    ) {
        if (posterUrl == null) {
            fallback()
        } else {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                error = {fallback()}
            )
        }
    }
}