package com.omero.cleanmovieapp.domain.model

data class MovieDetail(
    val id: String,
    val title: String,
    val actors: String?,
    val awards: String?,
    val boxOffice: String?,
    val director: String?,
    val genre: String?,
    val imdbRating: String?,
    val imdbVotes: String?,
    val plot: String?,
    val poster: String?,
    val released: String?,
    val runtime: String?,
    val writer: String?,
    val year: String?
)
