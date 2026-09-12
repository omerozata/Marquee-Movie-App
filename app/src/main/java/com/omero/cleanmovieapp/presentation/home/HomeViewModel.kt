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
        val STARTER_QUERIES = listOf(
            "Matrix", "Inception", "Interstellar", "Godfather", "Pulp Fiction",
            "Fight Club", "Gladiator", "Titanic", "Avatar", "Joker",
            "Batman", "Alien", "Blade Runner", "Terminator", "Jurassic Park",
            "Star Wars", "Indiana Jones", "Rocky", "Casino", "Goodfellas"
        )
    }

    init {
        getMovies(STARTER_QUERIES.random())
    }

    private fun getMovies(search: String, page: Int = 1) {
        job?.cancel()

        job = getMoviesUseCase(search, page).onEach { resource ->
            when(resource) {

                is Resource.Loading -> _state.update {
                    it.copy(isLoading = true, error = null, lastQuery = search)
                }

                is Resource.Success -> {
                    val result = resource.data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            movies = result.movies,
                            error = null,
                            currentPage = result.currentPage,
                            totalPages = result.totalPages,
                            hasNext = result.hasNext,
                            hasPrevious = result.hasPrevious
                        )
                    }
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
                if (query.isNotBlank()) getMovies(query, page = 1)
            }

            is HomeEvent.Retry -> {
                val query = _state.value.lastQuery ?: _state.value.search
                if (query.isNotBlank()) getMovies(query, page = _state.value.currentPage)
            }

            is HomeEvent.NextPage -> {
                val query = _state.value.lastQuery ?: return
                if (_state.value.hasNext) getMovies(query, _state.value.currentPage +1)
            }

            is HomeEvent.PreviousPage -> {
                val query = _state.value.lastQuery ?: return
                if (_state.value.hasPrevious) getMovies(query, _state.value.currentPage -1)
            }


        }
    }

}