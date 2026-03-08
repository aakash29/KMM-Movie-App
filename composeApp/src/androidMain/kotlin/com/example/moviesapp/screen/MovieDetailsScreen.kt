@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviesapp.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.components.LoaderIndicator
import com.example.moviesapp.domain.model.MovieDetails
import com.example.moviesapp.presentation.events.MovieDetailsEvent
import com.example.moviesapp.presentation.state.MovieDetailsUiState
import com.example.moviesapp.presentation.viewmodel.MovieDetailsViewModel
import com.example.moviesapp.theme.LocalRadiusProvider
import com.example.moviesapp.theme.LocalSpacingProvider
import com.example.moviesapp.theme.LocalTypographyProvider
import com.example.moviesapp.theme.MovieAppTheme
import com.example.moviesapp.theme.bg_strong_color
import com.example.moviesapp.theme.white
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onNavigationBack: () -> Unit,
    viewModel: MovieDetailsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(movieId) {
        viewModel.handleEvent(
            event = MovieDetailsEvent.LoadMovieDetails(movieId = movieId)
        )
    }
    MovieDetails(
        uiState = uiState,
        onNavigationBack = onNavigationBack
    )
}

@Composable
private fun MovieDetails(
    uiState: MovieDetailsUiState,
    onNavigationBack: () -> Unit,
) {

    val spacingProvider = LocalSpacingProvider.current
    val typographyProvider = LocalTypographyProvider.current
    val radiusProvider = LocalRadiusProvider.current

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        containerColor = white,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Movies Details",
                        style = typographyProvider.titleMedium,
                        color = white
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bg_strong_color
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigationBack) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                            tint = white,
                            contentDescription = ""
                        )
                    }
                }
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
                ConstraintLayout(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                ) {

                    val (img, title, description) = createRefs()

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .constrainAs(img) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        model = uiState.movieDetails?.image,
                        contentDescription = uiState.movieDetails?.title,
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.ic_placeholder)
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(title) {
                                top.linkTo(img.bottom, margin = spacingProvider.spacing_2)
                                start.linkTo(parent.start, margin = spacingProvider.spacing_2)
                                end.linkTo(parent.end, margin = spacingProvider.spacing_2)
                                width = Dimension.fillToConstraints
                            },
                        text = uiState.movieDetails?.title.orEmpty(),
                        style = typographyProvider.titleLarge,
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(description) {
                                top.linkTo(title.bottom, margin = spacingProvider.spacing_2)
                                start.linkTo(parent.start, margin = spacingProvider.spacing_2)
                                end.linkTo(parent.end, margin = spacingProvider.spacing_2)
                                width = Dimension.fillToConstraints
                            },
                        text = uiState.movieDetails?.description.orEmpty(),
                        style = typographyProvider.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMovieDetailScreen() {
    MovieAppTheme {
        MovieDetails(
            uiState = MovieDetailsUiState(
                movieDetails = MovieDetails(
                    id = 1234,
                    title = "Shelter",
                    description = "A man living in self-imposed exile on a remote island rescues a young girl from a violent storm, setting off a chain of events that forces him out of retirement to protect her from enemies tied to his past.",
                    image = "",
                    director = "John Doe",
                    writer = "Jane Smith",
                    cast = "Actor 1, Actor 2",
                    genres = "Action, Drama",
                    budget = 1000000,
                    revenue = 2000000,
                    releaseDate = "2023-05-15",
                    status = "Released",
                    runtime = 102,
                    voteAverage = 7.5,
                    tagLine = "A Tagline",
                )
            ),
            onNavigationBack = { }
        )
    }
}
