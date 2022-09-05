package com.newstore.favqs.ui.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newstore.favqs.R
import com.newstore.favqs.base.BaseFragment
import com.newstore.favqs.common.hide
import com.newstore.favqs.common.show
import com.newstore.favqs.common.showToast
import com.newstore.favqs.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginVM by viewModels()

    override fun setupView() {
        binding?.loginBtn?.setOnClickListener {
            viewModel.login(binding?.emailEt?.text.toString(), binding?.passwordEt?.text.toString())
        }
        binding?.signupBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_RegisterFragment)
        }
    }

    override fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding?.progressBar?.hide()
            when (uiState) {
                LoginUIState.LoadingState -> {
                    closeKeyboard(context, binding?.passwordEt)
                    binding?.progressBar?.show()
                }
                LoginUIState.InvalidEmailState -> {
                    binding?.emailInputLayout?.error = getString(R.string.invalid_email_address)
                }
                LoginUIState.InvalidPasswordState -> {
                    binding?.passwordInputLayout?.error =
                        getString(R.string.invalid_password_address)
                }
                LoginUIState.SuccessState -> {
                    findNavController().popBackStack(R.id.loginFragment, true)
                    findNavController().navigate(R.id.homeFragment)
                }
                is LoginUIState.ErrorState -> {
                    showToast(uiState.message ?: getString(R.string.generic_error))
                }
            }
        }
    }

}