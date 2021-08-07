package com.example.musicbrainz.presentation.screens.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhArtistBinding
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.util.extensions.setTextOrUnknown

class ArtistViewHolder(
    private val binding: VhArtistBinding,
    private val itemClick: ArtistClickListener
) : RecyclerView.ViewHolder(binding.root) {

    interface ArtistClickListener {
        fun onArtistClick(item: Artist, position: Int)
    }

    fun bind(
        item: Artist,
        position: Int
    ) {
        binding.apply {
            txtName.setTextOrUnknown(item.name)
            txtType.setTextOrUnknown(item.type)
            txtCountry.setTextOrUnknown(item.country)
            txtScore.setTextOrUnknown(item.score?.toString())
            itemView.setOnClickListener {
                itemClick.onArtistClick(item, position)
            }
        }
    }

}