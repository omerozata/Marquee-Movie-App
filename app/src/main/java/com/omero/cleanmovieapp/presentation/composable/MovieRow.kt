package com.omero.cleanmovieapp.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.omero.cleanmovieapp.domain.model.Movie

@Composable
fun MovieRow(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {

           MoviePoster(
               posterUrl = movie.poster,
               title = movie.title,
               modifier = Modifier.size(width = 100.dp, height = 150.dp)
           )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                movie.year?.let {year ->
                    Text(
                        text = year,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

}