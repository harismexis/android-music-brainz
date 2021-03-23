package com.example.musicbrainz.presentation.screens.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhTextBinding

class TextViewHolder(
    private val binding: VhTextBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: String) {
        binding.txtAlbumsTitleHeader.text = value
    }
}