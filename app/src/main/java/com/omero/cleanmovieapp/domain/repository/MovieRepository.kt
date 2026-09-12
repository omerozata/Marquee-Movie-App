package com.omero.cleanmovieapp.domain.repository

import com.omero.cleanmovieapp.domain.model.MovieDetail
import com.omero.cleanmovieapp.domain.model.MoviePage


interface MovieRepository {

    suspend fun getMovies(search: String, page: Int) : MoviePage
    suspend fun getMovieDetails(id: String) : MovieDetail
}