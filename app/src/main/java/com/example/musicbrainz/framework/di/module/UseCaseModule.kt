package com.example.musicbrainz.framework.di.module

import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import com.example.musicbrainz.usecases.UseCaseGetAlbums
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideUseCaseSearchArtists(
        repository: MusicBrainzRemoteRepository
    ): UseCaseSearchArtists {
        return UseCaseSearchArtists(repository)
    }

    @Singleton
    @Provides
    fun provideUseCaseGetAlbums(
        repository: MusicBrainzRemoteRepository
    ): UseCaseGetAlbums {
        return UseCaseGetAlbums(repository)
    }

}