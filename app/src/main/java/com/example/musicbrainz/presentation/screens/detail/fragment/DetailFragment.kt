package com.example.musicbrainz.presentation.screens.detail.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicbrainz.R
import com.example.musicbrainz.databinding.FragmentDetailBinding
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.base.BaseFragment
import com.example.musicbrainz.framework.extensions.setDivider
import com.example.musicbrainz.framework.extensions.showToast
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.screens.detail.adapter.DetailAdapter
import com.example.musicbrainz.presentation.viewmodel.SharedViewModel

class DetailFragment : BaseFragment() {

    private var binding: FragmentDetailBinding? = null
    private lateinit var viewModel: SharedViewModel
    private lateinit var adapter: DetailAdapter
    private var albums: MutableList<Album> = mutableListOf()

    override fun onViewCreated() {
        if (viewModel.hasSelectedArtist()) {
            observeLiveData()
            viewModel.fetchAlbums()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun initialiseViewModel() {
        viewModel = ViewModelProviders.of(
            requireActivity(),
            viewModelFactory
        )[SharedViewModel::class.java]
    }

    override fun initialiseViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun getRootView() = binding?.root

    override fun observeLiveData() {
        viewModel.albumsResult.observe(viewLifecycleOwner, {
            when (it) {
                is AlbumsResult.AlbumsSuccess -> populate(it.items)
                is AlbumsResult.AlbumsError -> populateError(it.error)
            }
        })
    }

    override fun initialiseView() {
        setupToolbar()
        if (viewModel.hasSelectedArtist()) {
            initialiseRecycler()
        }
    }

    private fun initialiseRecycler() {
        adapter = DetailAdapter(viewModel.selectedArtist!!, albums)
        adapter.setHasStableIds(true)
        binding?.albumList?.let {
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = adapter
            it.setDivider(R.drawable.divider)
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

    private fun populate(items: List<Album>) {
        binding?.let {
            it.progressBar.visibility = View.GONE
        }
        binding?.albumList?.visibility = View.VISIBLE
        albums.clear()
        albums.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun populateError(error: String) {
        binding?.let {
            it.progressBar.visibility = View.GONE
        }
        requireContext().showToast(error)
    }

}