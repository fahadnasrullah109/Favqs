package com.newstore.favqs.data.usecases

import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.request.RegisterRequest
import com.newstore.favqs.data.models.request.RegisterUser
import com.newstore.favqs.data.models.response.RegisterResponse
import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher

class RegisterUsecase(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher
) : FlowUseCase<RegisterRequest, DataResource<RegisterResponse>>(dispatcher) {

    override fun execute(parameters: RegisterRequest) = usersRepository.registerUser(parameters)
}