package com.omero.cleanmovieapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class DetailRoute(val movieId: String)