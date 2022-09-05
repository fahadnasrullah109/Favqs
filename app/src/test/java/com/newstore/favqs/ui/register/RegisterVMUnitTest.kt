package com.newstore.favqs.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.newstore.favqs.TestingCoroutineRule
import com.newstore.favqs.common.Validator
import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.models.request.RegisterRequest
import com.newstore.favqs.data.models.request.RegisterUser
import com.newstore.favqs.data.models.response.RegisterResponse
import com.newstore.favqs.data.usecases.RegisterUsecase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegisterVMUnitTest {

    // Subject under Test
    private lateinit var viewModel: RegisterVM

    @MockK
    private lateinit var registerUsecase: RegisterUsecase

    @MockK
    private lateinit var validator: Validator

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when registerUsecase invoked on RegisterVM, do register on server`() = runBlocking {
        // Given
        val response = getDummyResponse()
        val uiObserver = mockk<Observer<RegisterUIState>>(relaxed = true)

        // When
        coEvery { validator.isValidEmail(any()) }.returns(true)
        coEvery { validator.isValidPassword(any()) }.returns(true)
        coEvery { registerUsecase.invoke(any()) }
            .returns(flowOf(DataResource.Success(response)))

        // Invoke
        viewModel = RegisterVM(validator, registerUsecase)
        viewModel.register(
            username = getDummyUsername(),
            email = getDummyEmail(),
            password = getDummyPassword()
        )
        viewModel.uiState.observeForever(uiObserver)

        // Then
        coVerify(exactly = 1) { registerUsecase.invoke(any()) }
        verify { uiObserver.onChanged(match { it == RegisterUIState.SuccessState }) }
    }

    private fun getDummyRequest() = RegisterRequest(
        RegisterUser(
            login = "dummyUser",
            email = "dummyEmail",
            password = "dummyPassword"
        )
    )

    private fun getDummyResponse() = RegisterResponse(name = "dummyName", token = "dummyToken")

    private fun getDummyEmail() = "dummyEmail@dummy.com"

    private fun getDummyPassword() = "dummyPassword"

    private fun getDummyUsername() = "dummyUsername"

}