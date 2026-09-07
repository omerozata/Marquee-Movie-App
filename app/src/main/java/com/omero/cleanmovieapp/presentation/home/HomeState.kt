package com.omero.cleanmovieapp.presentation.home

import com.omero.cleanmovieapp.common.AppError
import com.omero.cleanmovieapp.domain.model.Movie

data class HomeState(
    val search: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: AppError? = null
)
