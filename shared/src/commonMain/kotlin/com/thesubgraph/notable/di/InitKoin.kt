package com.thesubgraph.notable.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.KoinAppDeclaration
import kotlin.reflect.KClass

fun initKoin() = initKoin {}

fun initKoin(
    extraModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(
        networkModule,
        repositoryModule,
        useCaseModule,
        *extraModules.toTypedArray(),
    )
}

fun <T> Koin.getDependency(clazz: KClass<*>): T = get(clazz, null) { parametersOf(clazz.simpleName) } as T
