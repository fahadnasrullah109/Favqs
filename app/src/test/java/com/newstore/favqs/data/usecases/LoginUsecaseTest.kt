package com.newstore.favqs.data.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newstore.favqs.TestingCoroutineRule
import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.repositories.UsersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginUsecaseTest {

    @MockK
    private lateinit var repository: UsersRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun getDummyUser() =
        User(userId = 0, name = "dummyUser", email = "dummyEmail", token = "dummyToken")

    private fun getDummyLoginRequest() = LoginRequest(com.newstore.favqs.data.models.request.User(login = "dummy@xyz.com", password = "dummyPass"))

    @Test
    fun `test invoking LoginUsecase invoke loginUser on Repository, return Success`() = runBlocking {
        // Given
        val useCase = LoginUsecase(repository, coroutinesRule.testDispatcher)
        val givenUser = getDummyUser()

        // When
        coEvery { repository.loginUser(any()) }
            .returns(flowOf(DataResource.Success(givenUser)))

        // Invoke
        val response = useCase(getDummyLoginRequest()).last()

        // Then
        coVerify(exactly = 1) { repository.loginUser(any()) }
        confirmVerified(repository)
        MatcherAssert.assertThat(response, CoreMatchers.instanceOf(DataResource.Success::class.java))
    }

    @Test
    fun `test invoking LoginUsecase invoke loginUser on Repository, return Error`() = runBlocking {
        // Given
        val useCase = LoginUsecase(repository, coroutinesRule.testDispatcher)

        // When
        coEvery { repository.loginUser(any()) }
            .returns(flowOf(DataResource.Error<Throwable>(RuntimeException())))

        // Invoke
        val response = useCase(getDummyLoginRequest()).last()

        // Then
        coVerify(exactly = 1) { repository.loginUser(any()) }
        confirmVerified(repository)
        MatcherAssert.assertThat(response, CoreMatchers.instanceOf(DataResource.Error::class.java))
    }
}