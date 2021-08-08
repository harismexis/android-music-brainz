package com.example.musicbrainz.config.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.config.vmfactory.InstrumentedHomeVmFactory
import com.example.musicbrainz.config.vmfactory.InstrumentedVmFactory
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.presentation.vmfactory.assisted.VmAssistedFactory
import dagger.Binds
import dagger.Module

@Module
abstract class InstrumentedVmModule {

    @Binds
    internal abstract fun bindVmFactory(factory: InstrumentedVmFactory)
            : ViewModelProvider.Factory

    @Binds
    internal abstract fun bindHomeVmFactory(factory: InstrumentedHomeVmFactory)
            : VmAssistedFactory<HomeVm>
}