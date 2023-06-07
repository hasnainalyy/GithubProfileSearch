package com.example.speertechnologiesassessmentsolution.domain.use_case


import com.example.speertechnologiesassessmentsolution.domain.model.ApiResponse
import com.example.speertechnologiesassessmentsolution.domain.model.UserModel
import com.example.speertechnologiesassessmentsolution.domain.repository.Repository
import com.example.speertechnologiesassessmentsolution.network.error_handling.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserUseCase @Inject constructor(private val repository: Repository) {


    operator fun invoke(username:String): Flow<Resource<UserModel>> = flow {
        try {
            emit(Resource.Loading<UserModel>())
            emit(Resource.Success<UserModel>(repository.getUser(username)))
        } catch(e: HttpException) {


            val gson = Gson()
            val type = object : TypeToken<ApiResponse?>() {}.type
            val errorResponse: ApiResponse = gson.fromJson(e.response()!!.errorBody()!!.charStream(), type)

            when (errorResponse.message) {
                else -> {
                    emit(Resource.Error<UserModel>(errorResponse.message ?: "An unexpected error occurred"))
                }
            }

        } catch(e: IOException) {
            emit(Resource.Error<UserModel>("Couldn't reach server. Check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)




}