package com.omero.cleanmovieapp.presentation.detail

import com.omero.cleanmovieapp.common.AppError
import com.omero.cleanmovieapp.domain.model.MovieDetail

data class DetailState(
    val movie: MovieDetail? = null,
    val isLoading: Boolean = false,
    val error: AppError? = null
)
