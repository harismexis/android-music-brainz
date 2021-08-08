package com.example.musicbrainz.framework.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailVm
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.presentation.vmfactory.VmFactory
import com.example.musicbrainz.presentation.vmfactory.assisted.HomeVmFactory
import com.example.musicbrainz.presentation.vmfactory.assisted.VmAssistedFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindVmFactory(factory: VmFactory): ViewModelProvider.Factory

    @Binds
    internal abstract fun bindHomeVmFactory(factory: HomeVmFactory): VmAssistedFactory<HomeVm>

    @Binds
    @IntoMap
    @ViewModelKey(DetailVm::class)
    internal abstract fun detailViewModel(viewModel: DetailVm): ViewModel
}