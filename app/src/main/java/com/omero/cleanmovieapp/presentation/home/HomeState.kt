package com.omero.cleanmovieapp.presentation.home

import com.omero.cleanmovieapp.common.AppError
import com.omero.cleanmovieapp.domain.model.Movie

data class HomeState(
    val search: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val lastQuery: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 0,
    val hasNext: Boolean = false,
    val hasPrevious: Boolean = false
)
