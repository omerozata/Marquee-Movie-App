package com.omero.cleanmovieapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.omero.cleanmovieapp.common.Resource
import com.omero.cleanmovieapp.domain.usecase.GetMovieDetailsUseCase
import com.omero.cleanmovieapp.presentation.navigation.DetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val route = savedStateHandle.toRoute<DetailRoute>()

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()


    init {
        getMovieDetail(route.movieId)
    }

    private fun getMovieDetail(id: String) {
        getMovieDetailsUseCase(id).onEach {resource ->
            when(resource) {
                is Resource.Loading -> _state.update {
                    it.copy(isLoading = true, error = null)
                }
                is Resource.Success -> _state.update {
                    it.copy(isLoading = false, movie = resource.data, error = null)
                }
                is Resource.Error -> _state.update {
                    it.copy(isLoading = false, error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.Retry -> getMovieDetail(route.movieId)
        }
    }


}