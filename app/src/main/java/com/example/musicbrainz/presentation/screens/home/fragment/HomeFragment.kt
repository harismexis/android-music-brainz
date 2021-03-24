package com.example.musicbrainz.presentation.screens.home.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicbrainz.R
import com.example.musicbrainz.databinding.FragmentHomeBinding
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.base.BaseFragment
import com.example.musicbrainz.framework.extensions.setDivider
import com.example.musicbrainz.framework.extensions.showToast
import com.example.musicbrainz.framework.extensions.toParcel
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.home.adapter.ArtistAdapter
import com.example.musicbrainz.presentation.screens.home.viewholder.ArtistViewHolder
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeViewModel

class HomeFragment : BaseFragment(), ArtistViewHolder.ArtistClickListener,
    android.widget.SearchView.OnQueryTextListener {

    private lateinit var viewModel: HomeViewModel
    private var binding: FragmentHomeBinding? = null
    private lateinit var adapter: ArtistAdapter
    private var uiModels: MutableList<Artist> = mutableListOf()

    override fun onViewCreated() {
        observeLiveData()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun initialiseViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        )[HomeViewModel::class.java]
    }

    override fun initialiseViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun getRootView() = binding?.root

    override fun initialiseView() {
        initialiseRecycler()
        initialiseSearchView()
    }

    override fun observeLiveData() {
        viewModel.artistsResult.observe(viewLifecycleOwner, {
            when (it) {
                is ArtistsResult.ArtistsSuccess -> populate(it.items)
                is ArtistsResult.ArtistsError -> populateError(it.error)
            }
        })
    }

    override fun onArtistClick(
        item: Artist,
        position: Int
    ) {
        binding?.searchView?.clearFocus()
        val action = HomeFragmentDirections.actionHomeDestToDetailDest(item.toParcel())
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchQuery = query
        showProgress(true)
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }

    private fun initialiseRecycler() {
        adapter = ArtistAdapter(uiModels, this)
        adapter.setHasStableIds(true)
        binding?.artistList?.let {
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = adapter
            it.setDivider(R.drawable.divider)
        }
    }

    private fun initialiseSearchView() {
        binding?.searchView?.setOnQueryTextListener(this)
    }

    private fun populate(models: List<Artist>) {
        showProgress(false)
        uiModels.clear()
        uiModels.addAll(models)
        adapter.notifyDataSetChanged()
        binding?.artistList?.scrollToPosition(0)
    }

    private fun populateError(error: String) {
        showProgress(false)
        requireContext().showToast(error)
    }

    private fun showProgress(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        binding?.let {
            it.progressBar.visibility = visibility
        }
    }

}