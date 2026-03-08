//
//  MovieDetailsScreen.swift
//  iosApp
//
//  Created by Aakash Mangukiya on 08/03/26.
//

import SwiftUI
import Shared

extension MovieDetailsScreen {
    
    @MainActor
    class MovieDetailsViewModelWrapper: ObservableObject {
        
        let movieDetailsViewModel: MovieDetailsViewModel
        @Published var movieDetailsUiState: MovieDetailsUiState
        
        init() {
            let koin = KoinKt.getKoinInstance()
            self.movieDetailsViewModel = koin.getMovieDetailsViewModel()
            self.movieDetailsUiState = movieDetailsViewModel.uiState.value
            
            Task {
                for await state in movieDetailsViewModel.uiState {
                    self.movieDetailsUiState = state
                }
            }
        }
        
        func onEvent(_ event: MovieDetailsEvent) {
            movieDetailsViewModel.handleEvent(event: event)
        }
    }
}

struct MovieDetailsScreen: View {
    
    @StateObject private var viewModel = MovieDetailsViewModelWrapper()
    
    let movie: Movie_
    
    var body: some View {
        
        Group {
            if viewModel.movieDetailsUiState.isLoading {
                ProgressView("Loading...")
            } else if let movieDetails = viewModel.movieDetailsUiState.movieDetails {
                ScrollView {
                    
                    VStack(alignment: .leading, spacing: 16) {
                        
                        AsyncImage(url: URL(string: movieDetails.image)) { image in
                            image
                                .resizable()
                                .scaledToFill()
                        } placeholder: {
                            ProgressView()
                        }
                        .frame(maxWidth: .infinity, maxHeight: 250)
                        .clipped()
                        
                        VStack(alignment: .leading, spacing: 12) {
                            
                            Text(movieDetails.title)
                                .font(.title)
                                .fontWeight(.bold)
                            
                            Text(movieDetails.tagLine)
                                .italic()
                                .foregroundColor(.gray)
                            
                            Text(movieDetails.description_)
                                .font(.body)
                            
                            HStack {
                                
                                VStack(alignment: .leading) {
                                    Text("Director")
                                        .font(.headline)
                                    Text(movieDetails.director)
                                }
                                
                                Spacer()
                                
                                VStack(alignment: .leading) {
                                    Text("Writer")
                                        .font(.headline)
                                    Text(movieDetails.writer)
                                }
                            }
                            
                            Text("Cast:").font(.headline)
                            ScrollView(.horizontal, showsIndicators: false) {
                                HStack(spacing: 16) {
                                    ForEach(movieDetails.cast, id: \.self) { cast in
                                        CastCardView(cast: cast)
                                    }
                                }
                            }
                            
                            HStack {
                                
                                InfoRowView(
                                    title: "Rate",
                                    value: "\(movieDetails.voteAverage) / 10"
                                )
                                
                                Spacer()
                                
                                InfoRowView(
                                    title: "Status",
                                    value: movieDetails.status
                                )
                            }
                            
                            HStack {
                                
                                InfoRowView(
                                    title: "Revenue",
                                    value: "\(movieDetails.revenue)"
                                )
                                
                                Spacer()
                                
                                InfoRowView(
                                    title: "Budget",
                                    value: "\(movieDetails.budget)"
                                )
                            }
                        }
                        .padding()
                    }
                }
            } else if let error = viewModel.movieDetailsUiState.error {
                Text(error).foregroundColor(.red)
            }
        }
        .navigationTitle(movie.title)
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            viewModel.onEvent(MovieDetailsEvent.LoadMovieDetails(movieId: movie.id))
        }
    }
}

struct CastCardView: View {
    
    let cast: Cast_
    
    var body: some View {
        
        VStack(alignment: .leading) {
            
            AsyncImage(url: URL(string: cast.profilePath)) { image in
                image
                    .resizable()
                    .scaledToFill()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 140, height: 150)
            .clipped()
            .cornerRadius(10)
            
            Text(cast.name)
                .font(.headline)
                .lineLimit(1)
            
            Text(cast.character)
                .font(.subheadline)
                .foregroundColor(.gray)
        }
        .frame(width: 140)
        .padding(8)
        .background(Color.white)
        .cornerRadius(12)
        .shadow(radius: 3)
    }
}

struct InfoRowView: View {
    
    let title: String
    let value: String
    
    var body: some View {
        
        VStack(alignment: .leading, spacing: 4) {
            
            Text(value)
                .font(.headline)
            
            Text(title)
                .font(.subheadline)
                .foregroundColor(.gray)
        }
    }
}
