package com.rajendra.dailypulsekmp.di

import com.rajendra.dailypulsekmp.data.network.NewsApiService
import com.rajendra.dailypulsekmp.presentation.viewmodel.NewsViewModel
import org.koin.dsl.module

val newsAppModule = module {
    single<NewsApiService> { NewsApiService(get()) }
    single<NewsViewModel> { NewsViewModel(get()) }
}