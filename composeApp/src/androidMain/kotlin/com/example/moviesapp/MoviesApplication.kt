package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@MoviesApplication)
        }
    }
}