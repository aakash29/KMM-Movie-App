//
//  MovieListViewModelWrapper.swift
//  iosApp
//
//  Created by Aakash Mangukiya on 08/03/26.
//

import SwiftUI
import Shared

@MainActor
class MovieListViewModelWrapper: ObservableObject {
    
    let movieListViewModel: MovieListViewModel
    @Published var movieUiState: MovieUiState
    private var hasRequestedInitialLoad = false
    private var lastPaginationTriggerMovieId: Int32?
    
    init() {
        let koin = KoinKt.getKoinInstance()
        self.movieListViewModel = koin.getMovieListViewModel()
        self.movieUiState = MovieUiState(isLoading: false, movies: [], nowPlayingMovies: [], error: nil, currentPage: 1, isEndReached: false)
        
        Task {
            let sequence = SkieSwiftFlow(movieListViewModel.uiState)
            for await state in sequence {
                self.movieUiState = state
            }
        }
    }
    
    func onEvent(_ event: MovieListEvent) {
        movieListViewModel.handleEvent(event: event)
    }

    func loadMoviesIfNeeded() {
        guard !hasRequestedInitialLoad, movieUiState.movies.isEmpty else { return }
        hasRequestedInitialLoad = true
        onEvent(MovieListEvent.LoadMovies())
    }

    func reloadMovies() {
        hasRequestedInitialLoad = true
        lastPaginationTriggerMovieId = nil
        onEvent(MovieListEvent.LoadMovies())
    }

    func loadNextPageIfNeeded(currentMovieId: Int32) {
        guard currentMovieId == movieUiState.movies.last?.id else { return }
        guard !movieUiState.isLoading, !movieUiState.isEndReached else { return }
        guard lastPaginationTriggerMovieId != currentMovieId else { return }

        lastPaginationTriggerMovieId = currentMovieId
        onEvent(MovieListEvent.LoadNextPage())
    }
}
