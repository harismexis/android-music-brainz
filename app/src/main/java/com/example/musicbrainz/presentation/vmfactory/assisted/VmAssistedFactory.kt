package com.example.musicbrainz.presentation.vmfactory.assisted

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface VmAssistedFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}