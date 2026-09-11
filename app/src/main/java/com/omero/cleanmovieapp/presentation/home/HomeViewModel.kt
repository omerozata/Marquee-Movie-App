package com.omero.cleanmovieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omero.cleanmovieapp.common.Resource
import com.omero.cleanmovieapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var job: Job? = null

    private companion object {
        const val DEFAULT_QUERY = "Harry Potter"
    }

    init {
        getMovies(DEFAULT_QUERY)
    }

    private fun getMovies(search: String) {
        job?.cancel()

        job = getMoviesUseCase(search).onEach { resource ->
            when(resource) {

                is Resource.Loading -> _state.update {
                    it.copy(isLoading = true, error = null, lastQuery = search)
                }

                is Resource.Success -> _state.update {
                    it.copy(isLoading = false, movies = resource.data, error = null)
                }

                is Resource.Error -> _state.update {
                    it.copy(isLoading = false, error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when(event) {

            is HomeEvent.SearchQueryChanged -> {
                _state.update {
                    it.copy(search = event.query)
                }
            }

            is HomeEvent.SearchSubmitted -> {
                val query = _state.value.search.trim()
                if (query.isNotBlank()) getMovies(query)
            }

            is HomeEvent.Retry -> {
                val query = _state.value.lastQuery ?: _state.value.search
                if (query.isNotBlank()) getMovies(query)
            }

            is HomeEvent.MessageShown -> _state.update {
                it.copy(error = null)
            }

        }
    }

}