package com.example.moviesapp.theme

import androidx.compose.runtime.staticCompositionLocalOf

val LocalRadiusProvider = staticCompositionLocalOf { Radius() }

val LocalTypographyProvider = staticCompositionLocalOf { MovieTypography() }

val LocalSpacingProvider = staticCompositionLocalOf { Spacing() }