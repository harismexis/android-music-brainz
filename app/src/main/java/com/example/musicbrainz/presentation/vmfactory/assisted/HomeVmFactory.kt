package com.example.musicbrainz.presentation.vmfactory.assisted

import androidx.lifecycle.SavedStateHandle
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import javax.inject.Inject

class HomeVmFactory @Inject constructor(
    private val searchArtists: UseCaseSearchArtists,
    private val resProvider: ResourceProvider
) : VmAssistedFactory<HomeVm> {
    override fun create(handle: SavedStateHandle): HomeVm =
        HomeVm(searchArtists, resProvider, handle)
}
