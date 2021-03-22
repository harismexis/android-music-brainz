package com.example.musicbrainz.presentation.screens.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhAlbumBinding
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.extensions.setTextAndVisibility
import com.example.musicbrainz.framework.extensions.setTextOrUnknown

class AlbumViewHolder(
    private val binding: VhAlbumBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Album
    ) {
        binding.apply {
            txtTitle.setTextOrUnknown(item.title)
            txtStatus.setTextAndVisibility(item.status, txtStatusLabel)
            txtTrackcount.setTextAndVisibility(item.trackCount.toString(), txtTrackcountLabel)
            txtDate.setTextAndVisibility(item.date, txtDateLabel)
            txtCountry.setTextAndVisibility(item.country, txtCountryLabel)
            txtDisambiguation.setTextAndVisibility(item.disambiguation, txtDisambiguationLabel)
            txtScore.setTextAndVisibility(item.score.toString(), txtScoreLabel)
            txtPackaging.setTextAndVisibility(item.packaging, txtPackagingLabel)
            txtBarcode.setTextAndVisibility(item.barcode, txtBarcodeLabel)
        }
    }

    fun unbind() {
        // Release resources, unsubscribe etc
    }

}