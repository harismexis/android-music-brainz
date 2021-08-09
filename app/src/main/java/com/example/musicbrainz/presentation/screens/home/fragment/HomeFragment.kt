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
import com.example.musicbrainz.presentation.base.BaseDIFragment
import com.example.musicbrainz.presentation.screens.home.adapter.ArtistAdapter
import com.example.musicbrainz.presentation.screens.home.viewholder.ArtistViewHolder
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.presentation.vmfactory.assisted.StateVmFactory
import com.example.musicbrainz.presentation.vmfactory.assisted.VmAssistedFactory
import com.example.musicbrainz.util.result.ArtistsResult
import javax.inject.Inject

class HomeFragment : BaseDIFragment(),
    ArtistViewHolder.ArtistClickListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    @Inject
    internal lateinit var vmFactory: VmAssistedFactory<HomeVm>

    private val viewModel: HomeVm by viewModels {
        StateVmFactory(vmFactory, this)
    }

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
        initSearchView()
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

    private fun initSearchView() {
        binding?.searchView?.setOnQueryTextListener(this)
    }

    override fun onViewCreated() {
        observeLiveData()
        submitQueryFromSavedSate()
    }

    private fun submitQueryFromSavedSate() {
        viewModel.getSearchQuery()?.let {
            binding?.searchView?.setQuery(it, true)
        }
    }

    override fun observeLiveData() {
        viewModel.artists.observe(viewLifecycleOwner, {
            when (it) {
                is ArtistsResult.Success -> populate(it.items)
                is ArtistsResult.Error -> populateError(it.error)
            }
        })

        viewModel.showMsgEvent.observe(viewLifecycleOwner, EventObserver {
            binding?.root?.showSnackBar(it)
        })
    }

    private fun populate(items: List<Artist>) {
        showProgress(false)
        binding?.let {
            val hasItems = items.isNotEmpty()
            val recyclerVisib = if (hasItems) View.VISIBLE else View.GONE
            val emptyViewVisib = if (hasItems) View.GONE else View.VISIBLE
            it.artistList.visibility = recyclerVisib
            it.noResults.visibility = emptyViewVisib
        }
        uiModels.clear()
        uiModels.addAll(items)
        adapter.notifyDataSetChanged()
        binding?.artistList?.scrollToPosition(0)
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

    private fun doBeforeSearch() {
        binding?.noResults?.visibility = View.GONE
        showProgress(true)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            doBeforeSearch()
            viewModel.search(query)
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }

    private fun showProgress(show: Boolean) {
        binding?.progressBar?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun getRootView() = binding?.root
}