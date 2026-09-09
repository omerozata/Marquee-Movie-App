package com.omero.cleanmovieapp.presentation.home

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

private val PlaceHolderColors = listOf(
    Color(0xFF3B4A6B),
    Color(0xFF5C3B4A),
    Color(0xFF3B5C4A),
    Color(0xFF5C523B),
    Color(0xFF4A3B5C),
    Color(0xFF3B525C)
)

fun placeHolderColorFor(text: String) : Color =
    PlaceHolderColors[abs(text.hashCode()) % PlaceHolderColors.size]