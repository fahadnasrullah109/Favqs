package com.newstore.favqs.data.usecases

import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher

class SaveUserUsecase(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher
) : FlowUseCase<User, Long>(dispatcher) {

    override fun execute(parameters: User) = usersRepository.saveUser(parameters)
}