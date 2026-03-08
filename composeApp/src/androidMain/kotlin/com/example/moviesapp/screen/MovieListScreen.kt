@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviesapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.components.LoaderIndicator
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.state.MovieUiState
import com.example.moviesapp.presentation.viewmodel.MovieListViewModel
import com.example.moviesapp.theme.LocalRadiusProvider
import com.example.moviesapp.theme.LocalSpacingProvider
import com.example.moviesapp.theme.LocalTypographyProvider
import com.example.moviesapp.theme.MovieAppTheme
import com.example.moviesapp.theme.bg_strong_color
import com.example.moviesapp.theme.white
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = koinViewModel(),
    onNavigateToDetails: (movieId: Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value
    MovieList(uiState = uiState, onNavigateToDetails = onNavigateToDetails)
}

@Composable
private fun MovieList(
    uiState: MovieUiState,
    onNavigateToDetails: (movieId: Int) -> Unit,
) {
    val spacingProvider = LocalSpacingProvider.current
    val typographyProvider = LocalTypographyProvider.current

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        containerColor = white,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Movies",
                        style = typographyProvider.titleMedium,
                        color = white
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bg_strong_color
                )
            )
        }
    ) { padding ->

        when {
            uiState.isLoading -> {
                LoaderIndicator(
                    color = bg_strong_color
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(all = spacingProvider.spacing_4),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(space = spacingProvider.spacing_4)
                ) {
                    items(
                        items = uiState.movies,
                        key = { "${it.id}_${it.title}" }
                    ) { movie ->
                        MovieItem(movie = movie, onNavigateToDetails = onNavigateToDetails)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onNavigateToDetails: (movieId: Int) -> Unit,
) {

    val spacingProvider = LocalSpacingProvider.current
    val typographyProvider = LocalTypographyProvider.current
    val radiusProvider = LocalRadiusProvider.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetails(movie.id) },
        shape = RoundedCornerShape(radiusProvider.large),
        colors = CardDefaults.cardColors(containerColor = white),
        elevation = CardDefaults.cardElevation(defaultElevation = spacingProvider.spacing_4)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

            val (img, title, description) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .width(110.dp)
                    .height(150.dp)
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                model = movie.image,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_placeholder)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = spacingProvider.spacing_2)
                        start.linkTo(img.end, margin = spacingProvider.spacing_2)
                        end.linkTo(parent.end, margin = spacingProvider.spacing_2)
                        width = Dimension.fillToConstraints
                    },
                text = movie.title,
                style = typographyProvider.titleSmall,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(description) {
                        top.linkTo(title.bottom, margin = spacingProvider.spacing_2)
                        bottom.linkTo(img.bottom)
                        start.linkTo(img.end, margin = spacingProvider.spacing_2)
                        end.linkTo(parent.end, margin = spacingProvider.spacing_2)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                text = movie.description,
                style = typographyProvider.bodyMedium,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview() {

    MovieAppTheme {
        MovieList(
            uiState = MovieUiState(
                movies = listOf(
                    Movie(
                        id = 12344,
                        title = "Shelter",
                        lang = "en",
                        description = "A man living in self-imposed exile on a remote island rescues a young girl from a violent storm, setting off a chain of events that forces him out of seclusion to protect her from enemies tied to his past.",
                        image = "",
                        releaseDate = "2026-01-28"
                    ),
                    Movie(
                        id = 12344,
                        title = "History of the World: Part I",
                        lang = "en",
                        description = "An uproarious version of history that proves nothing is sacred – not even the Roman Empire, the French Revolution and the Spanish Inquisition.",
                        image = "",
                        releaseDate = "1981-06-12"
                    )
                )
            ),
            onNavigateToDetails = { }
        )
    }
}