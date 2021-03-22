package com.example.musicbrainz.presentation.screens.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhArtistBinding
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.extensions.setTextOrUnknown

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
        binding.txtName.text = item.name
        binding.txtType.setTextOrUnknown(item.type)
        binding.txtCountry.setTextOrUnknown(item.country)
        binding.txtScore.setTextOrUnknown(item.score?.toString())
        itemView.setOnClickListener {
            itemClick.onArtistClick(item, position)
        }
    }

    fun unbind() {
        // Release resources, unsubscribe etc
    }

}