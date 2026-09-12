package com.omero.cleanmovieapp.presentation.home



sealed interface HomeEvent {
    data class SearchQueryChanged(val query: String) : HomeEvent
    data object SearchSubmitted : HomeEvent
    data object Retry : HomeEvent
    data object NextPage: HomeEvent
    data object PreviousPage: HomeEvent
}