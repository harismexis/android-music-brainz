package com.example.musicbrainz.presentation.screens.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhArtistDetailBinding
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.util.extensions.setTextAndVisibility
import com.example.musicbrainz.framework.util.extensions.setTextOrUnknown
import com.example.musicbrainz.framework.util.extensions.tagString

class ArtistDetailViewHolder(
    private val binding: VhArtistDetailBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Artist) {
        binding.apply {
            txtName.setTextOrUnknown(item.name)
            txtType.setTextOrUnknown(item.type)
            txtCountry.setTextOrUnknown(item.country)
            txtScore.setTextOrUnknown(item.score?.toString())
            txtArea.setTextOrUnknown(item.area)
            txtBeginDate.setTextOrUnknown(item.beginDate)
            txtEndDate.setTextAndVisibility(item.endDate, txtEndDateLabel)
            txtTags.setTextAndVisibility(item.tagString(), txtTagsLabel)
        }
    }

}