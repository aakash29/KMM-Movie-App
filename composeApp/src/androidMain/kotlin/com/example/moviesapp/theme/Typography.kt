package com.example.moviesapp.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moviesapp.R

class MovieTypography(

    val largeTitle: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val mediumTitle: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp,
        lineHeight = 46.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val titleLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val titleMedium: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val titleSmall: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val headLine: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val subHeadLine: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val bodyLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val bodyMedium: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val label: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = MovieFont.googleSans(),
        color = text_strong_color
    ),

    val caption: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = MovieFont.googleSans(),
        letterSpacing = 0.28.sp,
        color = text_strong_color
    ),
)

private object MovieFont {

    fun googleSans() = FontFamily(
        Font(
            resId = R.font.google_sans_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.google_sans_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.google_sans_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.google_sans_semibold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.google_sans_italic,
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        )
    )
}