package com.example.musicbrainz.setup.di.component

import com.example.musicbrainz.framework.di.ui.FragmentBindingsModule
import com.example.musicbrainz.setup.application.InstrumentedApplication
import com.example.musicbrainz.setup.viewmodel.factory.InstrumentedViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FragmentBindingsModule::class,
        InstrumentedViewModelModule::class,
    ]
)
interface InstrumentedComponent : AndroidInjector<InstrumentedApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: InstrumentedApplication)
                : InstrumentedComponent
    }

}