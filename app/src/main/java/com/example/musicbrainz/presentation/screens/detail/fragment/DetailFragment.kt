package com.example.musicbrainz.presentation.screens.detail.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicbrainz.R
import com.example.musicbrainz.databinding.FragmentDetailBinding
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.base.BaseFragment
import com.example.musicbrainz.framework.util.extensions.setDivider
import com.example.musicbrainz.framework.util.extensions.showToast
import com.example.musicbrainz.framework.util.extensions.toArtist
import com.example.musicbrainz.framework.util.parcelable.ArtistParcel
import com.example.musicbrainz.presentation.screens.detail.adapter.DetailAdapter
import com.example.musicbrainz.presentation.screens.detail.adapter.DetailModel
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailViewModel
import com.example.musicbrainz.util.result.AlbumsResult

class DetailFragment : BaseFragment() {

    companion object {
        private const val ARG_SELECTED_ARTIST = "selected_artist"
    }

    private val viewModel: DetailViewModel by viewModels { viewModelFactory }
    private var binding: FragmentDetailBinding? = null
    private lateinit var adapter: DetailAdapter
    private var detailModels: MutableList<DetailModel> = mutableListOf()

    override fun initialiseViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun initialiseView() {
        setupToolbar()
    }

    override fun onViewCreated() {
        retrieveSelectedArtist()
        if (viewModel.hasSelectedArtist()) {
            initialiseRecycler()
            observeLiveData()
            viewModel.fetchAlbums()
        }
    }

    private fun initialiseRecycler() {
        detailModels.clear()
        adapter = DetailAdapter(detailModels)
        adapter.setHasStableIds(true)
        binding?.detailList?.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.setDivider(R.drawable.divider)
        }
    }

    override fun observeLiveData() {
        viewModel.albumsResult.observe(viewLifecycleOwner, {
            when (it) {
                is AlbumsResult.Success -> populate(it.items)
                is AlbumsResult.Error -> populateError(it.error)
            }
        })
    }

    private fun populate(items: List<Album>) {
        binding?.let {
            it.progressBar.visibility = View.GONE
        }
        updateModels(items)
        adapter.notifyDataSetChanged()
    }

    private fun updateModels(items: List<Album>) {
        detailModels.clear()
        detailModels.add(DetailModel.ArtistHeaderModel(viewModel.selectedArtist))
        detailModels.add(DetailModel.TextModel(getString(R.string.albums_title_header)))
        detailModels.addAll(items.map { DetailModel.AlbumModel(it) })
    }

    private fun populateError(error: String) {
        // we call populate with empty album list to
        // show artist details only
        populate(ArrayList())
        requireContext().showToast(error)
    }

    private fun retrieveSelectedArtist() {
        val artistParcel: ArtistParcel? = arguments?.getParcelable(ARG_SELECTED_ARTIST)
        artistParcel?.let {
            viewModel.selectedArtist = it.toArtist()
        }
    }

    private fun setupToolbar() {
        val navController = findNavController()
        val appBarConf = AppBarConfiguration(navController.graph)
        binding?.toolbar?.let {
            it.setupWithNavController(navController, appBarConf)
            it.setNavigationIcon(R.drawable.ic_arrow_left_white_rounded_24dp)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun getRootView() = binding?.root
}