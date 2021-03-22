package com.example.musicbrainz.framework.di

import com.example.musicbrainz.framework.application.MainApplication
import com.example.musicbrainz.framework.di.api.MusicBrainzApiModule
import com.example.musicbrainz.framework.di.application.ApplicationModule
import com.example.musicbrainz.framework.di.interactor.InteractorModule
import com.example.musicbrainz.framework.di.ui.FragmentBindingsModule
import com.example.musicbrainz.framework.viewmodelfactory.ViewModelModule
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
        ViewModelModule::class,
        ApplicationModule::class,
        MusicBrainzApiModule::class,
        InteractorModule::class
    ]
)
interface MainComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MainApplication): MainComponent
    }

}