package com.example.musicbrainz.config.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.mocks.mockDetailVm
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailVm
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class InstrumentedVmFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        getVmMap()[modelClass]?.get() as T
}

fun getVmMap(): MutableMap<Class<out ViewModel>, Provider<ViewModel>> {
    val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>> = mutableMapOf()
    viewModels[DetailVm::class.java] = Provider { mockDetailVm }
    return viewModels
}