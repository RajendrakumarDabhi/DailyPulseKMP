package com.rajendra.dailypulsekmp.di

import com.rajendra.dailypulsekmp.data.network.createHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { createHttpClient() }
}