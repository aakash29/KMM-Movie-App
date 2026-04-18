//
// Created by Aakash Mangukiya on 15/03/26.
//

import Foundation
import Shared

@MainActor
class MovieDetailsViewModelWrapper: ObservableObject {

    let movieDetailsViewModel: MovieDetailsViewModel
    @Published var movieDetailsUiState: MovieDetailsUiState
    private var loadedMovieId: Int32?

    init() {
        let koin = KoinKt.getKoinInstance()
        self.movieDetailsViewModel = koin.getMovieDetailsViewModel()
        self.movieDetailsUiState = movieDetailsViewModel.uiState.value

        Task {
            let sequence = SkieSwiftFlow(movieDetailsViewModel.uiState)
            for await state in sequence {
                self.movieDetailsUiState = state
            }
        }
    }

    func onEvent(_ event: MovieDetailsEvent) {
        movieDetailsViewModel.handleEvent(event: event)
    }

    func loadMovieDetailsIfNeeded(movieId: Int32) {
        guard loadedMovieId != movieId else { return }
        loadedMovieId = movieId
        onEvent(MovieDetailsEvent.LoadMovieDetails(movieId: movieId))
    }

    func reloadMovieDetails(movieId: Int32) {
        loadedMovieId = movieId
        onEvent(MovieDetailsEvent.LoadMovieDetails(movieId: movieId))
    }
}
