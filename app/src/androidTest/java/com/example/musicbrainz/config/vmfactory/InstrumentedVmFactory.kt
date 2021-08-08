package com.example.musicbrainz.config.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailVm
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import io.mockk.mockk
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

val mockHomeVm: HomeVm = mockk(relaxed = true)
val mockDetailViewModel: DetailVm = mockk(relaxed = true)

@Singleton
class InstrumentedViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        getMockVmsMap()[modelClass]?.get() as T
}

fun getMockVmsMap(): MutableMap<Class<out ViewModel>, Provider<ViewModel>> {
    val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>> = mutableMapOf()
    viewModels[HomeVm::class.java] = Provider { mockHomeVm }
    viewModels[DetailVm::class.java] = Provider { mockDetailViewModel }
    return viewModels
}