package com.example.musicbrainz.presentation.screens.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhAlbumBinding
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.util.extensions.setTextOrUnknown

class AlbumViewHolder(
    private val binding: VhAlbumBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Album) {
        binding.apply {
            txtTitle.setTextOrUnknown(item.title)
            txtStatus.setTextOrUnknown(item.status)
            txtTrackcount.setTextOrUnknown(item.trackCount.toString())
            txtDate.setTextOrUnknown(item.date)
            txtCountry.setTextOrUnknown(item.country)
            txtDisambiguation.setTextOrUnknown(item.disambiguation)
            txtScore.setTextOrUnknown(item.score.toString())
            txtPackaging.setTextOrUnknown(item.packaging)
            txtBarcode.setTextOrUnknown(item.barcode)
        }
    }

}