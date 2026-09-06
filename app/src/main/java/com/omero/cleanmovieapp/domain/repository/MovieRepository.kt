package com.omero.cleanmovieapp.domain.repository

import com.omero.cleanmovieapp.domain.model.Movie
import com.omero.cleanmovieapp.domain.model.MovieDetail


interface MovieRepository {

    suspend fun getMovies(search: String) : List<Movie>
    suspend fun getMovieDetails(id: String) : MovieDetail
}