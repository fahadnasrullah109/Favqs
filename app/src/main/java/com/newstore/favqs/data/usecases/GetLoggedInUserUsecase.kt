package com.newstore.favqs.data.usecases

import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetLoggedInUserUsecase(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, User?>(dispatcher) {

    override fun execute(parameters: Unit) = usersRepository.getLoggedInUser()
}