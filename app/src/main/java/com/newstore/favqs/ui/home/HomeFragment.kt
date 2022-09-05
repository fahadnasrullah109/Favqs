package com.newstore.favqs.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.newstore.favqs.R
import com.newstore.favqs.base.BaseFragment
import com.newstore.favqs.common.hide
import com.newstore.favqs.common.show
import com.newstore.favqs.common.showToast
import com.newstore.favqs.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeVM by viewModels()
    private lateinit var navController: NavController

    override fun setupView() {
        setupToolbar()
        setupBottomNav()
    }

    override fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                HomeUIState.UserLoggedOutState -> {
                    logUserOut()
                }
                is HomeUIState.ErrorState -> {
                    showToast(uiState.message ?: getString(R.string.generic_error))
                }
            }
        }
    }

    private fun setupToolbar() {
        binding?.toolbar?.inflateMenu(R.menu.menu_main)
        binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_logout -> {
                    showLogoutDialog()
                    true
                }
                else -> false
            }
        }
        updateToolbarTitle(R.string.home)
    }

    private fun setupBottomNav() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding?.bottomNavView!!, navController)

        val appBarConfiguration = AppBarConfiguration(
            getBottomNavigationIds().toSet()
        )
        setupWithNavController(
            binding?.toolbar!!,
            navController,
            appBarConfiguration
        )

        // Setting BottomNavigationView visibility for different screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Screens which show Bottom Navigation
            if (getBottomNavigationIds().contains(destination.id)) {
                binding?.toolbar?.menu?.findItem(R.id.action_logout)?.isVisible = true
                binding?.bottomNavView?.show()
            }
            // Screens which hide Bottom Navigation
            else {
                binding?.toolbar?.menu?.findItem(R.id.action_logout)?.isVisible = false
                binding?.bottomNavView?.hide()
            }
        }
    }

    //bottom navigation items ids
    private fun getBottomNavigationIds() = listOf(
        R.id.quoteFragment,
        R.id.quoteListingFragment
    )

    private fun updateToolbarTitle(resourceId: Int) {
        binding?.toolbar?.title = getString(resourceId)
    }

    private fun showLogoutDialog() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.logout))
                .setMessage(resources.getString(R.string.sure_logout))
                .setNegativeButton(resources.getString(R.string.no)) { _, _ ->

                }
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    viewModel.logUserOut()
                }
                .show()
        }
    }

    private fun logUserOut() {
        findNavController().popBackStack(R.id.homeFragment, true)
        findNavController().navigate(R.id.loginFragment)
    }
}