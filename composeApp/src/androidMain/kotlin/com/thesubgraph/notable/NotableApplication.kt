package com.thesubgraph.notable

import android.app.Application
import com.thesubgraph.notable.di.initKoin
import com.thesubgraph.notable.viemodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class NotableApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    viewModelOf(::MainViewModel)
                },
            ),
        ) {
            androidContext(this@NotableApplication)
        }

    }
}
