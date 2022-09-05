package com.newstore.favqs.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newstore.favqs.R
import com.newstore.favqs.base.BaseUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservations()
    }

    private fun initObservations() {
        viewModel.uiState.observe(this) { uiState ->
            findNavController().popBackStack(R.id.mainFragment, true)
            when (uiState) {
                BaseUIState.ContentState -> {
                    findNavController().navigate(R.id.homeFragment)
                }
                is BaseUIState.ErrorState -> {
                    findNavController().navigate(R.id.loginFragment)
                }
                else -> {
                    // Do nothing
                }
            }
        }
    }
}