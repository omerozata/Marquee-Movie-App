package com.omero.cleanmovieapp.presentation.components

import androidx.compose.runtime.Composable
import com.omero.cleanmovieapp.common.AppError


@Composable
fun AppError.asMessage(): String = when (this) {
    is AppError.NoConnection -> "İnternet Bağlantısı Bulunamadı"
    is AppError.Api -> message
    is AppError.Http -> "Sunucuya Ulaşılamadı"
    is AppError.Serialization -> "Bilinmeyen Bir Hata Oluştu"
    is AppError.Unknown -> "Bilinmeyen Bir Hata Oluştu"
}