package com.newstore.favqs.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newstore.favqs.TestingCoroutineRule
import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.local.UserDao
import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.remote.RetroApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserRepositoryTest {

    // Subject under Test
    private lateinit var repository: UsersRepository

    @MockK
    private lateinit var apiService: RetroApiService

    @MockK
    private lateinit var userDao: UserDao

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    private fun getDummyUser() =
        User(userId = 0, name = "dummyUser", email = "dummyEmail", token = "dummyToken")

    private fun getDummyLoginRequest() = LoginRequest(
        com.newstore.favqs.data.models.request.User(
            login = "dummy@xyz.com",
            password = "dummyPass"
        )
    )

    @Test
    fun `test login, gives Success`() = runBlocking {
        // Given
        repository = UsersRepositoryImpl(apiService, userDao, coroutinesRule.testDispatcher)

        // When
        coEvery { apiService.login(any()) }
            .returns(getDummyUser())

        // Invoke
        val apiResponseFlow = repository.loginUser(getDummyLoginRequest())

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val response = apiResponseFlow.last()
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            response,
            CoreMatchers.instanceOf(DataResource.Success::class.java)
        )

        coVerify(exactly = 1) { apiService.login(any()) }
        confirmVerified(apiService)
    }
}