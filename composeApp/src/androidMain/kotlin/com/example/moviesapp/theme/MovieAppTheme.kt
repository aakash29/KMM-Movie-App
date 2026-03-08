package com.example.moviesapp.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MovieAppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalRadiusProvider provides Radius(),
        LocalTypographyProvider provides MovieTypography(),
        LocalSpacingProvider provides Spacing()
    ) {
        content()
    }
}