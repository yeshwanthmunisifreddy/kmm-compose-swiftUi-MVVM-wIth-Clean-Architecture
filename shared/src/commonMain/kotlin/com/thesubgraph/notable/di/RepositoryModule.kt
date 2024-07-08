package com.thesubgraph.notable.di

import com.thesubgraph.notable.data.ApiService
import com.thesubgraph.notable.data.DocsRepositoryImpl
import com.thesubgraph.notable.domain.repository.DocsRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val repositoryModule =
    module {
        single<DocsRepository> { provideDocsRepository(get()) }
    }

fun provideDocsRepository(client: HttpClient): DocsRepositoryImpl = DocsRepositoryImpl(client)
