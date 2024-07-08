package com.thesubgraph.notable.di

import com.thesubgraph.notable.domain.usecase.GetDocsUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        single { GetDocsUseCase(get()) }
    }
