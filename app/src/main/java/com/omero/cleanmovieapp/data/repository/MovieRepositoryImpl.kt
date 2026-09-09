package com.omero.cleanmovieapp.data.repository

import com.omero.cleanmovieapp.common.ApiException
import com.omero.cleanmovieapp.data.remote.MoviesAPI
import com.omero.cleanmovieapp.data.remote.dto.toMovieDetailOrNull
import com.omero.cleanmovieapp.data.remote.dto.toMovieList
import com.omero.cleanmovieapp.domain.model.Movie
import com.omero.cleanmovieapp.domain.model.MovieDetail
import com.omero.cleanmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MoviesAPI
) : MovieRepository {

    override suspend fun getMovies(search: String): List<Movie> {
        val dto = api.getMovies(search)

        if (dto.response != "True") {
            throw ApiException(dto.error ?: "Film bulunamadı")
        }
        return dto.toMovieList()
    }

    override suspend fun getMovieDetails(id: String): MovieDetail {
        val dto = api.getMovieDetails(id)

        if (dto.response != "True") {
            throw ApiException(dto.error ?: "Film bilgisi alınamadı")
        }
        return dto.toMovieDetailOrNull() ?: throw ApiException("Film bilgisi eksik")
    }
}