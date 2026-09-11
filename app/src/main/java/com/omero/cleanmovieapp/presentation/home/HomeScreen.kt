package com.omero.cleanmovieapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omero.cleanmovieapp.common.AppError
import com.omero.cleanmovieapp.presentation.components.asMessage
import com.omero.cleanmovieapp.presentation.composable.MovieRow
import com.omero.cleanmovieapp.presentation.composable.SearchBar

@Composable
fun HomeScreen(
    onMovieClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        onEvent = viewModel::onEvent,
        onMovieClick = onMovieClick,
        modifier = modifier
    )

}

@Composable
private fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onMovieClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val apiError = state.error as? AppError.Api

    LaunchedEffect(apiError) {
        if (apiError != null) {
            Toast.makeText(context, apiError.message, Toast.LENGTH_SHORT).show()
            onEvent(HomeEvent.MessageShown)
        }
    }


    Scaffold(modifier = modifier.fillMaxSize()) {padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            SearchBar(
                query = state.search,
                onQueryChange = {onEvent(HomeEvent.SearchQueryChanged(it))},
                onSearch = {onEvent(HomeEvent.SearchSubmitted)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    state.error != null -> {
                        ErrorState(
                            message = state.error.asMessage(),
                            onRetry = { onEvent(HomeEvent.Retry) },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }


                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = state.movies,
                                key = { movie -> movie.id }
                            ) { movie ->
                                MovieRow(
                                    movie = movie,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Tekrar dene")
        }
    }
}

