package com.rajendra.dailypulsekmp.android.di

import com.rajendra.dailypulsekmp.presentation.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel { NewsViewModel(get()) }
}