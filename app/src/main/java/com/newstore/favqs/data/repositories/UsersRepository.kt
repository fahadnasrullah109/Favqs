package com.newstore.favqs.data.repositories

import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.callApi
import com.newstore.favqs.data.local.UserDao
import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.request.RegisterRequest
import com.newstore.favqs.data.models.response.RegisterResponse
import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.remote.RetroApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface UsersRepository {
    fun loginUser(request: LoginRequest): Flow<DataResource<User>>
    fun registerUser(request: RegisterRequest): Flow<DataResource<RegisterResponse>>
    fun getLoggedInUser(): Flow<User?>
    fun logoutUser(): Flow<DataResource<Boolean>>
    fun saveUser(user: User): Flow<Long>
}

class UsersRepositoryImpl(
    private val apiService: RetroApiService,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UsersRepository {

    override fun loginUser(request: LoginRequest): Flow<DataResource<User>> = flow {
        emit(DataResource.Loading)
        val result = callApi {
            apiService.login(request)
        }
        emit(result)
    }.flowOn(dispatcher)

    override fun registerUser(request: RegisterRequest): Flow<DataResource<RegisterResponse>> =
        flow {
            emit(DataResource.Loading)
            val result = callApi {
                apiService.register(request)
            }
            emit(result)
        }.flowOn(dispatcher)

    override fun getLoggedInUser(): Flow<User?> = userDao.getLoggedInUserAsync().flowOn(dispatcher)

    override fun logoutUser(): Flow<DataResource<Boolean>> = flow {
        emit(DataResource.Loading)
        userDao.clear()
        emit(DataResource.Success(true))
    }.flowOn(dispatcher)

    override fun saveUser(user: User) = flow {
        emit(userDao.insert(user))
    }.flowOn(dispatcher)
}