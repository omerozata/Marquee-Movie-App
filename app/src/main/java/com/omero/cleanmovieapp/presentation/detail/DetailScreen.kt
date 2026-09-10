package com.omero.cleanmovieapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omero.cleanmovieapp.domain.model.MovieDetail
import com.omero.cleanmovieapp.presentation.components.asMessage
import com.omero.cleanmovieapp.presentation.components.placeHolderColorFor

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    DetailContent(
        state = state,
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
private fun DetailContent(
    state: DetailState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        topBar = { DetailTopBar(title = state.movie?.title, onBack = onBack) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = state.error.asMessage(),
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center).padding(32.dp)
                    )


                }

                state.movie != null -> {

                }
            }
        }
    }
   }

@Composable
private fun MovieDetailBody(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(placeHolderColorFor(movie.title)),
            contentAlignment = Alignment.Center
        ) {
            if (movie.poster != null) {

            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    title: String?,
    onBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title.orEmpty(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri"
                )
            }
        }
    )



}