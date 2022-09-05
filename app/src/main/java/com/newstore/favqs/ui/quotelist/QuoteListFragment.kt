package com.newstore.favqs.ui.quotelist

import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newstore.favqs.R
import com.newstore.favqs.base.BaseFragment
import com.newstore.favqs.common.*
import com.newstore.favqs.data.models.response.Quote
import com.newstore.favqs.databinding.FragmentQuoteListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteListFragment :
    BaseFragment<FragmentQuoteListBinding>(FragmentQuoteListBinding::inflate) {
    private val viewModel: QuoteListVM by viewModels()
    private var recyclerAdapter: QuotesAdapter? = null

    override fun setupView() {
        recyclerAdapter = QuotesAdapter(::onItemClick)
        binding?.quoteRv?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.vertical_space)))
            adapter = recyclerAdapter
        }
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    closeKeyboard(context, binding?.searchView)
                    viewModel.loadQuotes(true, it)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
        viewModel.loadQuotes(false)
    }

    override fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding?.progressBar?.hide()
            when (uiState) {
                QuoteListUIState.LoadingState -> {
                    binding?.progressBar?.show()
                }
                is QuoteListUIState.SuccessState -> {
                    recyclerAdapter?.replaceList(uiState.quotes)
                }
                is QuoteListUIState.ErrorState -> {
                    showToast(uiState.message ?: getString(R.string.generic_error))
                }
            }
        }
    }

    private fun onItemClick(item: Quote) {
        val bundle = bundleOf(Constants.quoteKey to item)
        findNavController().navigate(R.id.action_quoteListingFragment_to_detailFragment, bundle)
    }
}