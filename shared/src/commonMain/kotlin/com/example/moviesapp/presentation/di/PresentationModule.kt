package com.example.moviesapp.presentation.di

import com.example.moviesapp.presentation.interactor.MovieDetailInteractor
import com.example.moviesapp.presentation.interactor.MovieListInteractor
import com.example.moviesapp.presentation.state.MovieDetailsStateHandler
import com.example.moviesapp.presentation.state.MovieListStateHandler
import org.koin.dsl.module

val presentationModule = module {
    single { MovieListStateHandler() }
    factory { MovieDetailsStateHandler() }
    single { MovieListInteractor(get(), get()) }
    factory { MovieDetailInteractor(get(), get()) }
}