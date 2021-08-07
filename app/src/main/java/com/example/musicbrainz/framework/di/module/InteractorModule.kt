package com.example.musicbrainz.framework.di.module

import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import com.example.musicbrainz.framework.data.data.MusicBrainzRemoteDataSource
import com.example.musicbrainz.usecases.InteractorGetAlbums
import com.example.musicbrainz.usecases.InteractorSearchArtists
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideInteractorSearchArtists(
        dataSource: MusicBrainzRemoteDataSource
    ): InteractorSearchArtists {
        return InteractorSearchArtists(MusicBrainzRemoteRepository(dataSource))
    }

    @Provides
    fun provideInteractorGetAlbums(
        dataSource: MusicBrainzRemoteDataSource
    ): InteractorGetAlbums {
        return InteractorGetAlbums(MusicBrainzRemoteRepository(dataSource))
    }

}