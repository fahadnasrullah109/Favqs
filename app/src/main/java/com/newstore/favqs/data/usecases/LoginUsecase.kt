package com.newstore.favqs.data.usecases

import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoginUsecase(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher
) : FlowUseCase<LoginRequest, DataResource<User>>(dispatcher) {

    override fun execute(parameters: LoginRequest) = usersRepository.loginUser(parameters)
}