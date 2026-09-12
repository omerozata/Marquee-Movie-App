package com.omero.cleanmovieapp.domain.usecase

import com.omero.cleanmovieapp.common.Resource
import com.omero.cleanmovieapp.common.asResource
import com.omero.cleanmovieapp.domain.model.MoviePage
import com.omero.cleanmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(search: String, page: Int) : Flow<Resource<MoviePage>> =
        flow { emit(repository.getMovies(search, page)) }.asResource()
}