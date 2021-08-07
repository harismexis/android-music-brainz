package com.example.musicbrainz.config.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.config.vmfactory.InstrumentedViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class InstrumentedViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: InstrumentedViewModelFactory)
            : ViewModelProvider.Factory
}