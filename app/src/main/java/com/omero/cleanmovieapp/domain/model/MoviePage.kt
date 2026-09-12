package com.omero.cleanmovieapp.domain.model

import kotlin.math.ceil

data class MoviePage(
    val movies : List<Movie>,
    val totalResult: Int,
    val currentPage: Int
) {

    val totalPages: Int
        get() = if (totalResult == 0) 0 else ceil(totalResult / PAGE_SIZE.toDouble()).toInt()

    val hasNext: Boolean get() = currentPage < totalPages

    val hasPrevious: Boolean get() = currentPage > 1





    companion object {
        const val PAGE_SIZE = 10
    }
}
