package com.example.musicbrainz.config.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailViewModel
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeViewModel
import io.mockk.mockk
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

val mockHomeViewModel: HomeViewModel = mockk(relaxed = true)
val mockDetailViewModel: DetailViewModel = mockk(relaxed = true)

@Singleton
class InstrumentedViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        getMockViewModelsMap()[modelClass]?.get() as T
}

fun getMockViewModelsMap(): MutableMap<Class<out ViewModel>, Provider<ViewModel>> {
    val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>> = mutableMapOf()
    viewModels[HomeViewModel::class.java] =
        Provider { mockHomeViewModel }
    viewModels[DetailViewModel::class.java] =
        Provider { mockDetailViewModel }
    return viewModels
}