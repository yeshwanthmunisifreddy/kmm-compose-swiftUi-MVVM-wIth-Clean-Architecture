package com.thesubgraph.notable.di

import com.thesubgraph.notable.data.ApiService
import com.thesubgraph.notable.data.common.httpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule =
    module {
        single { provideApiService(get()) }
        single { provideHttpClient() }
    }

fun provideHttpClient(): HttpClient = httpClient

fun provideApiService(httpClient: HttpClient): ApiService = ApiService(httpClient)
