package com.example.musicbrainz.presentation.screens.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhAlbumBinding
import com.example.musicbrainz.databinding.VhAlbumsTitleBinding
import com.example.musicbrainz.databinding.VhArtistDetailBinding
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.presentation.screens.detail.viewholder.AlbumViewHolder
import com.example.musicbrainz.presentation.screens.detail.viewholder.AlbumsTitleViewHolder
import com.example.musicbrainz.presentation.screens.detail.viewholder.ArtistDetailViewHolder

class DetailAdapter(
    private val artist: Artist,
    private val albums: List<Album>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val NUM_OF_HEADERS = 2
        const val VIEW_TYPE_ARTIST_DETAIL = 0
        const val VIEW_TYPE_ALBUMS_TITLE = 1
        const val VIEW_TYPE_ALBUM = 2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ARTIST_DETAIL -> ArtistDetailViewHolder(
                VhArtistDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_TYPE_ALBUMS_TITLE -> AlbumsTitleViewHolder(
                VhAlbumsTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> AlbumViewHolder(
                VhAlbumBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when {
            position == 0 -> (viewHolder as ArtistDetailViewHolder).bind(artist)
            position > 1 -> (viewHolder as AlbumViewHolder).bind(albums[position-NUM_OF_HEADERS])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_ARTIST_DETAIL
            1 -> VIEW_TYPE_ALBUMS_TITLE
            else -> VIEW_TYPE_ALBUM
        }
    }

    override fun getItemCount(): Int {
        return albums.size + NUM_OF_HEADERS
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}