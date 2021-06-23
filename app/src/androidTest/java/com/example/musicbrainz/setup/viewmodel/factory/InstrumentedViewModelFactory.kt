package com.example.musicbrainz.setup.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailViewModel
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import io.mockk.mockk
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

val mockHomeViewModel: HomeViewModel = mockk(relaxed = true)
val mockDetailViewModel: DetailViewModel = mockk(relaxed = true)

@Singleton
class InstrumentedViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        getMockViewModelsMap()[modelClass]?.get() as T
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class InstrumentedViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: InstrumentedViewModelFactory)
            : ViewModelProvider.Factory
}

fun getMockViewModelsMap(): MutableMap<Class<out ViewModel>, Provider<ViewModel>> {
    val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>> = mutableMapOf()
    viewModels[HomeViewModel::class.java] =
        Provider { mockHomeViewModel }
    viewModels[DetailViewModel::class.java] =
        Provider { mockDetailViewModel }
    return viewModels
}