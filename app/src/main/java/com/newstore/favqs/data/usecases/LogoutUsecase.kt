package com.newstore.favqs.data.usecases

import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher

class LogoutUsecase(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<Boolean>>(dispatcher) {

    override fun execute(parameters: Unit) = usersRepository.logoutUser()
}