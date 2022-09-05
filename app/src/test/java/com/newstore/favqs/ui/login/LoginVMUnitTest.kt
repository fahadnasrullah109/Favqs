package com.newstore.favqs.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.newstore.favqs.TestingCoroutineRule
import com.newstore.favqs.common.Validator
import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.usecases.LoginUsecase
import com.newstore.favqs.data.usecases.SaveUserUsecase
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
class LoginVMUnitTest {

    // Subject under Test
    private lateinit var viewModel: LoginVM

    @MockK
    private lateinit var loginUsecase: LoginUsecase

    @MockK
    private lateinit var saveUserUsecase: SaveUserUsecase

    @MockK
    private lateinit var validator : Validator

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
    fun `test when loginUsecase invoked on LoginVM, do login from server`() = runBlocking {
        // Given
        val user = getDummyUser()
        val uiObserver = mockk<Observer<LoginUIState>>(relaxed = true)

        // When
        coEvery { validator.isValidEmail(any()) }.returns(true)
        coEvery { validator.isValidPassword(any()) }.returns(true)
        coEvery { loginUsecase.invoke(any()) }
            .returns(flowOf(DataResource.Success(user)))
        coEvery { saveUserUsecase.invoke(any()) }
            .returns(flowOf(1))

        // Invoke
        viewModel = LoginVM(validator, loginUsecase, saveUserUsecase)
        viewModel.login(email = getDummyEmail(), password = getDummyPassword())
        viewModel.uiState.observeForever(uiObserver)

        // Then
        coVerify(exactly = 1) { loginUsecase.invoke(any()) }
        verify { uiObserver.onChanged(match { it == LoginUIState.SuccessState }) }
    }

    private fun getDummyUser() =
        User(userId = 0, name = "dummyUser", email = "dummyEmail", token = "dummyToken")

    private fun getDummyEmail() = "dummyEmail@dummy.com"

    private fun getDummyPassword() = "dummyPassword"

}