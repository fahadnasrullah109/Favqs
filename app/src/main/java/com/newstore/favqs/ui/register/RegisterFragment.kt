package com.newstore.favqs.ui.register

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newstore.favqs.R
import com.newstore.favqs.base.BaseFragment
import com.newstore.favqs.common.hide
import com.newstore.favqs.common.show
import com.newstore.favqs.common.showToast
import com.newstore.favqs.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterVM by viewModels()

    override fun setupView() {
        binding?.registerBtn?.setOnClickListener {
            viewModel.register(
                binding?.usernameEt?.text.toString(),
                binding?.emailEt?.text.toString(),
                binding?.passwordEt?.text.toString()
            )
        }
    }

    override fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding?.progressBar?.hide()
            when (uiState) {
                RegisterUIState.InvalidUsernameState -> {
                    binding?.usernameInputLayout?.error = getString(R.string.invalid_username)
                }
                RegisterUIState.InvalidEmailState -> {
                    binding?.emailInputLayout?.error = getString(R.string.invalid_email_address)
                }
                RegisterUIState.InvalidPasswordState -> {
                    binding?.passwordInputLayout?.error =
                        getString(R.string.invalid_password_address)
                }
                RegisterUIState.LoadingState -> {
                    closeKeyboard(context, binding?.passwordEt)
                    binding?.progressBar?.show()
                }
                RegisterUIState.SuccessState -> {
                    findNavController().popBackStack(R.id.loginFragment, false)
                }
                is RegisterUIState.ErrorState -> {
                    showToast(uiState.message ?: getString(R.string.generic_error))
                }
            }
        }
    }

}