package com.newstore.favqs.ui.quote_detail

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.newstore.favqs.base.BaseFragment
import com.newstore.favqs.common.Constants
import com.newstore.favqs.data.models.response.Quote
import com.newstore.favqs.databinding.FragmentQuoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteDetailFragment :
    BaseFragment<FragmentQuoteDetailBinding>(FragmentQuoteDetailBinding::inflate) {
    private val viewModel: QuoteDetailVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setQuote(arguments?.getParcelable(Constants.quoteKey) as Quote?)
    }

    override fun setupView() {
        viewModel.getQuote()
    }

    override fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is QuoteDetailUIState.ContentState -> {
                    loadQuoteInUi(uiState.quote)
                }
            }
        }
    }

    private fun loadQuoteInUi(quote: Quote) {
        binding?.authorValueTv?.text = quote.author
        binding?.quoteBodyTv?.text = quote.body
        for (tag in quote.tags) {
            binding?.tagsContainer?.addView(getQuoteView(tag))
        }
    }

    private fun getQuoteView(tag: String) = Chip(context).apply {
        text = tag
    }
}