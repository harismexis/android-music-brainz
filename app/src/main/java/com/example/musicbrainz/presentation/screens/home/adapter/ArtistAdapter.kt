package com.example.musicbrainz.presentation.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicbrainz.databinding.VhArtistBinding
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.presentation.screens.home.viewholder.ArtistViewHolder

class ArtistAdapter(
    private val models: List<Artist>,
    private val clickListener: ArtistViewHolder.ArtistClickListener
) : RecyclerView.Adapter<ArtistViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistViewHolder {
        return ArtistViewHolder(
            VhArtistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(
        viewHolder: ArtistViewHolder,
        position: Int
    ) {
        viewHolder.bind(models[position], position)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onViewDetachedFromWindow(holder: ArtistViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

}