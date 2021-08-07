package com.example.musicbrainz.presentation.screens.home.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicbrainz.R
import com.example.musicbrainz.databinding.FragmentHomeBinding
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.util.extensions.setDivider
import com.example.musicbrainz.framework.util.extensions.showSnackBar
import com.example.musicbrainz.framework.util.observer.EventObserver
import com.example.musicbrainz.presentation.base.BaseInjectedFragment
import com.example.musicbrainz.presentation.screens.home.adapter.ArtistAdapter
import com.example.musicbrainz.presentation.screens.home.viewholder.ArtistViewHolder
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.util.result.ArtistsResult

class HomeFragment : BaseInjectedFragment(),
    ArtistViewHolder.ArtistClickListener,
    android.widget.SearchView.OnQueryTextListener {

    private val viewModel: HomeVm by viewModels { vmFactory }
    private var binding: FragmentHomeBinding? = null
    private lateinit var adapter: ArtistAdapter
    private var uiModels: MutableList<Artist> = mutableListOf()

    override fun initialiseViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initialiseView() {
        initialiseRecycler()
        initialiseSearchView()
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

    override fun onViewCreated() {
        observeLiveData()
    }

    override fun observeLiveData() {
        viewModel.artists.observe(viewLifecycleOwner, {
            when (it) {
                is ArtistsResult.Success -> populate(it.items)
                is ArtistsResult.Error -> populateError(it.error)
            }
        })

        viewModel.showMsg.observe(viewLifecycleOwner, EventObserver {
            binding?.root?.showSnackBar(it)
        })
    }

    private fun populate(models: List<Artist>) {
        showProgress(false)
        uiModels.clear()
        uiModels.addAll(models)
        adapter.notifyDataSetChanged()
    }

    private fun populateError(error: Exception) {
        showProgress(false)
    }

    override fun onArtistClick(
        item: Artist,
        position: Int
    ) {
        binding?.searchView?.clearFocus()
        val action = HomeFragmentDirections.actionHomeDestToDetailDest(item)
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            showProgress(true)
            viewModel.searchQuery = query
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }

    private fun showProgress(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        binding?.let {
            it.progressBar.visibility = visibility
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun getRootView() = binding?.root

}