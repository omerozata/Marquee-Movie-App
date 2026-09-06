package com.omero.cleanmovieapp.domain.usecase

import com.omero.cleanmovieapp.common.Resource
import com.omero.cleanmovieapp.common.asResource
import com.omero.cleanmovieapp.domain.model.MovieDetail
import com.omero.cleanmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(id: String) : Flow<Resource<MovieDetail>> =
        flow { emit(repository.getMovieDetails(id)) }.asResource()
}