package com.example.moviesapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.domain.model.Cast
import com.example.moviesapp.theme.LocalColorProvider
import com.example.moviesapp.theme.LocalRadiusProvider
import com.example.moviesapp.theme.LocalSpacingProvider
import com.example.moviesapp.theme.LocalTypographyProvider

@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    cast: Cast
) {

    val spacingProvider = LocalSpacingProvider.current
    val typographyProvider = LocalTypographyProvider.current
    val radiusProvider = LocalRadiusProvider.current
    val colorProvider = LocalColorProvider.current

    Card(
        modifier = modifier.width(120.dp),
        shape = RoundedCornerShape(radiusProvider.large),
        colors = CardDefaults.cardColors(containerColor = colorProvider.backgroundColor.bg_white),
        elevation = CardDefaults.cardElevation(defaultElevation = spacingProvider.spacing_1)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                model = cast.profilePath,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_person)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacingProvider.spacing_1)
                    .padding(horizontal = spacingProvider.spacing_1),
                text = cast.name,
                style = typographyProvider.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacingProvider.spacing_1)
                    .padding(horizontal = spacingProvider.spacing_1),
                text = cast.character,
                style = typographyProvider.subHeadLine,
                color = colorProvider.textColor.text_medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastItemPreview() {
    CastItem(
        cast = Cast(
            name = "Jason Statham",
            profilePath = "/pXGSq2UpcDE2NMF8LR56QZf5U1q.jpg",
            character = "Mason"
        )
    )
}

