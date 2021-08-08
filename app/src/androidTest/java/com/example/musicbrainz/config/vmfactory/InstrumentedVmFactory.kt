package com.example.musicbrainz.config.vmfactory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailVm
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.presentation.vmfactory.assisted.VmAssistedFactory
import io.mockk.mockk
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

val mockHomeVm: HomeVm = mockk(relaxed = true)
val mockDetailVm: DetailVm = mockk(relaxed = true)

@Singleton
class InstrumentedVmFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        getMockVmsMap()[modelClass]?.get() as T
}

@Singleton
class InstrumentedHomeVmFactory @Inject constructor() :
    VmAssistedFactory<HomeVm> {
    override fun create(handle: SavedStateHandle): HomeVm {
        return mockHomeVm
    }
}

fun getMockVmsMap(): MutableMap<Class<out ViewModel>, Provider<ViewModel>> {
    val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>> = mutableMapOf()
    viewModels[DetailVm::class.java] = Provider { mockDetailVm }
    return viewModels
}