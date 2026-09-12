package com.omero.cleanmovieapp.data.remote

import com.omero.cleanmovieapp.data.remote.dto.MovieDetailDTO
import com.omero.cleanmovieapp.data.remote.dto.MoviesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET(".")
    suspend fun getMovies(
        @Query("s") search : String,
        @Query("page") page: Int
    ) : MoviesDTO

    @GET(".")
    suspend fun getMovieDetails(
        @Query("i") imdbId : String
    ) : MovieDetailDTO

}