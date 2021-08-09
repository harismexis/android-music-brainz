package com.example.musicbrainz.config.vmfactory

import androidx.lifecycle.SavedStateHandle
import com.example.musicbrainz.mocks.mockHomeVm
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.presentation.vmfactory.assisted.VmAssistedFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstrumentedHomeVmFactory @Inject constructor() :
    VmAssistedFactory<HomeVm> {
    override fun create(handle: SavedStateHandle): HomeVm {
        return mockHomeVm
    }
}