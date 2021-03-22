package com.example.musicbrainz.presentation.screens.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhArtistDetailBinding
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.extensions.setTextAndVisibility
import com.example.musicbrainz.framework.extensions.setTextOrUnknown

class ArtistDetailViewHolder(
    private val binding: VhArtistDetailBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Artist
    ) {
        binding.apply {
            txtName.setTextOrUnknown(item.name)
            txtType.setTextOrUnknown(item.type)
            txtCountry.setTextOrUnknown(item.country)
            txtScore.setTextOrUnknown(item.score?.toString())

            txtArea.setTextAndVisibility(item.area, txtAreaLabel)
            txtBeginDate.setTextAndVisibility(item.beginDate, txtBeginDateLabel)
            txtEndDate.setTextAndVisibility(item.endDate, txtEndDateLabel)
            txtTags.setTextAndVisibility(item.tags?.joinToString(", "), txtTagsLabel)

        }
    }

}