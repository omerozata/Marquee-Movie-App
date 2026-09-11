package com.omero.cleanmovieapp.presentation.detail

sealed interface DetailEvent {

    data object Retry  : DetailEvent

}