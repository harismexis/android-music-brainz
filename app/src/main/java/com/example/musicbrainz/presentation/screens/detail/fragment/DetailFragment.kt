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
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.util.extensions.setDivider
import com.example.musicbrainz.framework.util.extensions.showSnackBar
import com.example.musicbrainz.framework.util.observer.EventObserver
import com.example.musicbrainz.presentation.base.BaseInjectedFragment
import com.example.musicbrainz.presentation.screens.detail.adapter.DetailAdapter
import com.example.musicbrainz.presentation.screens.detail.adapter.DetailModel
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailVm
import com.example.musicbrainz.util.result.AlbumsResult

class DetailFragment : BaseInjectedFragment() {

    companion object {
        private const val ARG_SELECTED_ARTIST = "selected_artist"
    }

    private val viewModel: DetailVm by viewModels { vmFactory }
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
        retrieveArtistFromArgs()
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
        viewModel.albums.observe(viewLifecycleOwner, {
            when (it) {
                is AlbumsResult.Success -> populate(it.items)
                is AlbumsResult.Error -> populateError(it.error)
            }
        })

        viewModel.showMsg.observe(viewLifecycleOwner, EventObserver {
            binding?.root?.showSnackBar(it)
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

    private fun populateError(error: Exception) {
        // we call populate with empty album list to
        // show artist details only
        populate(ArrayList())
    }

    private fun retrieveArtistFromArgs() {
        val artist: Artist? = arguments?.getSerializable(ARG_SELECTED_ARTIST) as? Artist
        artist?.let {
            viewModel.selectedArtist = it
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