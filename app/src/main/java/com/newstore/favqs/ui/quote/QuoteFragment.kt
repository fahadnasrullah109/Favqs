package com.newstore.favqs.ui.quote

import androidx.fragment.app.viewModels
import com.newstore.favqs.R
import com.newstore.favqs.base.BaseFragment
import com.newstore.favqs.common.hide
import com.newstore.favqs.common.show
import com.newstore.favqs.common.showToast
import com.newstore.favqs.databinding.FragmentQuoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteFragment : BaseFragment<FragmentQuoteBinding>(FragmentQuoteBinding::inflate) {
    private val viewModel: QuoteVM by viewModels()

    override fun setupView() {
    }

    override fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding?.progressBar?.hide()
            when (uiState) {
                QuoteUIState.LoadingState -> {
                    binding?.progressBar?.show()
                }
                is QuoteUIState.SuccessState -> {
                    binding?.quoteTv?.text = uiState.quote.body
                }
                is QuoteUIState.ErrorState -> {
                    showToast(uiState.message ?: getString(R.string.generic_error))
                }
            }
        }
    }
}