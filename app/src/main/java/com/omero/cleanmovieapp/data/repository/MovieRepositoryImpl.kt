package com.omero.cleanmovieapp.data.repository

import com.omero.cleanmovieapp.common.ApiException
import com.omero.cleanmovieapp.data.remote.MoviesAPI
import com.omero.cleanmovieapp.data.remote.dto.toMovieDetailOrNull
import com.omero.cleanmovieapp.data.remote.dto.toMovieList
import com.omero.cleanmovieapp.domain.model.Movie
import com.omero.cleanmovieapp.domain.model.MovieDetail
import com.omero.cleanmovieapp.domain.model.MoviePage
import com.omero.cleanmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MoviesAPI
) : MovieRepository {

    private val EMPTY_RESULT_MESSAGES = listOf(
        "Movie not found",
        "Too many results",
        "Series not found",
        "Incorrect IMDb ID"
    )
    //OMDB Api daha düzgün bir yapı sunsaydı onu kullanmayı tercih ederdim
    //Eldeki şartlarla bu çözümü üretebildim

    override suspend fun getMovies(search: String, page: Int): MoviePage {
        val dto = api.getMovies(search, page)

        if (dto.response != "True") {
            val message = dto.error.orEmpty()
            val isEmptyResult = EMPTY_RESULT_MESSAGES.any {message.startsWith(it, ignoreCase = true)}

            if (isEmptyResult) {
                return MoviePage(movies = emptyList(), totalResult = 0, currentPage = page)
            }
            throw ApiException(message.ifBlank { "Beklenmeyen Bir Hata Oluştu" })
        }
        return MoviePage(
            movies = dto.toMovieList(),
            totalResult = dto.totalResults?.toIntOrNull() ?: 0,
            currentPage = page
        )
    }

    override suspend fun getMovieDetails(id: String): MovieDetail {
        val dto = api.getMovieDetails(id)

        if (dto.response != "True") {
            throw ApiException(dto.error ?: "Film bilgisi alınamadı")
        }
        return dto.toMovieDetailOrNull() ?: throw ApiException("Film bilgisi eksik")
    }
}