package com.example.musicbrainz.presentation.screens.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.R
import com.example.musicbrainz.databinding.VhAlbumBinding
import com.example.musicbrainz.databinding.VhArtistDetailBinding
import com.example.musicbrainz.databinding.VhTextBinding
import com.example.musicbrainz.presentation.screens.detail.viewholder.AlbumViewHolder
import com.example.musicbrainz.presentation.screens.detail.viewholder.ArtistDetailViewHolder
import com.example.musicbrainz.presentation.screens.detail.viewholder.TextViewHolder

class DetailAdapter(
    private val models: List<DetailModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val NUM_OF_HEADERS = 2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.vh_artist_detail -> ArtistDetailViewHolder(
                VhArtistDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.vh_text -> TextViewHolder(
                VhTextBinding.inflate(
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
        when (val item = models[position]) {
            is DetailModel.ArtistHeaderModel -> (viewHolder as ArtistDetailViewHolder).bind(item.artist)
            is DetailModel.TextModel -> { (viewHolder as TextViewHolder).bind(item.title) }
            is DetailModel.AlbumModel -> (viewHolder as AlbumViewHolder).bind(item.album)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.vh_artist_detail
            1 -> R.layout.vh_text
            else -> R.layout.vh_album
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}