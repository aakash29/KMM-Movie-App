//
//  MovieListScreen.swift
//  iosApp

//  Created by Aakash Mangukiya on 08/03/26.
//

import SwiftUI
import Shared

extension MovieListScreen {
    
    @MainActor
    class MovieListViewModelWrapper: ObservableObject {
        
        let movieListViewModel: MovieListViewModel
        @Published var movieUiState: MovieUiState
        
        init() {
            let koin = KoinKt.getKoinInstance()
            self.movieListViewModel = koin.getMovieListViewModel()
            self.movieUiState = MovieUiState(isLoading: false, movies: [], error: nil, currentPage: 1, isEndReached: false)
            
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
    }
}

struct MovieListScreen: View {
    
    @StateObject private var viewModel = MovieListViewModelWrapper()
    @State private var navigationPath = NavigationPath()
    
    var body: some View {
        
        NavigationStack(path: $navigationPath) {
            
            ScrollView {
                if viewModel.movieUiState.isLoading && viewModel.movieUiState.movies.isEmpty {
                    ProgressView()
                        .frame(maxWidth: .infinity)
                        .padding()
                } else {
                    LazyVStack(alignment: .leading, spacing:16){
                        
                        ForEach(viewModel.movieUiState.movies, id: \.id){ movie in
                            MovieItemView(movie: movie)
                                .onTapGesture {
                                    navigationPath.append(movie)
                                }
                                .onAppear {
                                    if movie == viewModel.movieUiState.movies.last {
                                        if !viewModel.movieUiState.isLoading && !viewModel.movieUiState.isEndReached {
                                            viewModel.onEvent(MovieListEvent.LoadNextPage())
                                        }
                                    }
                                }
                        }
                        
                        if viewModel.movieUiState.isLoading {
                            HStack {
                                Spacer()
                                ProgressView()
                                Spacer()
                            }
                            .padding()
                        }
                    }
                    .padding(.vertical)
                    .navigationTitle("Movies")
                    .navigationBarTitleDisplayMode(.inline)
                    .navigationDestination(for: Movie_.self) { movie in
                        MovieDetailsScreen(movie: movie)
                    }
                }
            }.onAppear {
                if viewModel.movieUiState.movies.isEmpty {
                    viewModel.onEvent(MovieListEvent.LoadMovies())
                }
            }
        }
    }
}
    
struct MovieItemView: View {
    
    var movie: Movie_
    
    var body: some View {
        HStack(alignment: .top){
            AsyncImage(url: URL(string: movie.image)) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 110, height: 150)
            .clipped()
            .cornerRadius(8, corners: [.topLeft, .bottomLeft])
            VStack(alignment: .leading, spacing: 4){
                Text(movie.title)
                    .font(.headline)
                    .fontWeight(.bold)
                Text(movie.overview)
                    .font(.subheadline)
                    .fontWeight(.regular)
                    .lineLimit(5)
            }.padding(8)
        }
        .background(
            RoundedRectangle(cornerRadius: 8).fill(Color.white)
        )
        .shadow(color: Color.black.opacity(0.15), radius: 8, x: 0, y: 4)
        .padding(.horizontal)
    }
}

#Preview {
    MovieListScreen()
}
