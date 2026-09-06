package com.omero.cleanmovieapp.data.remote.dto



import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesDTO(
    @SerialName("Response")
    val response: String? = null,
    @SerialName("Search")
    val search: List<SearchItemDTO>? = null,
    @SerialName("totalResults")
    val totalResults: String? = null,
    @SerialName("Error")
    val error: String? = null
)

@Serializable
data class SearchItemDTO(
    @SerialName("imdbID")
    val imdbID: String? = null,
    @SerialName("Poster")
    val poster: String? = null,
    @SerialName("Title")
    val title: String? = null,
    @SerialName("Type")
    val type: String? = null,
    @SerialName("Year")
    val year: String? = null
)



