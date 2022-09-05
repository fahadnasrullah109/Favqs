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
class GetLoggedInUserUsecaseTest {

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

    @Test
    fun `test invoking GetLoggedInUserUsecase invoke getLoggedInUser on Repository, return Success`() = runBlocking {
        // Given
        val useCase = GetLoggedInUserUsecase(repository, coroutinesRule.testDispatcher)
        val user = getDummyUser()

        // When
        coEvery { repository.getLoggedInUser() }
            .returns(flowOf(user))

        // Invoke
        val response = useCase(Unit).last()

        // Then
        coVerify(exactly = 1) { repository.getLoggedInUser() }
        confirmVerified(repository)
        MatcherAssert.assertThat(response, CoreMatchers.instanceOf(User::class.java))
    }
}