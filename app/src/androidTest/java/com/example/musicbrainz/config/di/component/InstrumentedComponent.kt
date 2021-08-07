package com.example.musicbrainz.config.di.component

import com.example.musicbrainz.config.application.InstrumentedApplication
import com.example.musicbrainz.config.di.module.InstrumentedVmModule
import com.example.musicbrainz.framework.di.module.FragmentBindingsModule
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
        InstrumentedVmModule::class,
    ]
)
interface InstrumentedComponent : AndroidInjector<InstrumentedApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: InstrumentedApplication)
                : InstrumentedComponent
    }

}