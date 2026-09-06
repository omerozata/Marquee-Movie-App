package com.omero.cleanmovieapp.data.remote.dto

import com.omero.cleanmovieapp.domain.model.Movie
import com.omero.cleanmovieapp.domain.model.MovieDetail

private val MEANINGLESS = setOf("N/A", "NA", "null", "-", "")

fun String?.sanitize() : String? =
    this?.trim()?.takeIf { it !in MEANINGLESS }


fun MoviesDTO.toMovieList() : List<Movie> =
    search.orEmpty().mapNotNull { it.toMovieOrNull() }

private fun SearchItemDTO.toMovieOrNull() : Movie? {
    val id = imdbID.sanitize() ?: return null
    val title = title.sanitize() ?: return null

    return Movie(
        id = id,
        title = title,
        poster = poster.sanitize(),
        year = year.sanitize()
    )
}

fun MovieDetailDTO.toMovieDetailOrNull() : MovieDetail? {
    val id = imdbID.sanitize() ?: return null
    val title = title.sanitize() ?: return null

    return MovieDetail(
        id = id,
        title = title,
        actors = actors.sanitize(),
        awards = awards.sanitize(),
        boxOffice = boxOffice.sanitize(),
        director = director.sanitize(),
        genre = genre.sanitize(),
        imdbRating = imdbRating.sanitize(),
        imdbVotes = imdbVotes.sanitize(),
        plot = plot.sanitize(),
        poster = poster.sanitize(),
        released = released.sanitize(),
        runtime = runtime.sanitize(),
        writer = writer.sanitize(),
        year = year.sanitize()
    )
}



