package com.example.speertechnologiesassessmentsolution.network

import com.example.speertechnologiesassessmentsolution.domain.model.UserModel
import com.example.speertechnologiesassessmentsolution.utils.AppConstant
import retrofit2.http.*


interface ApiService {

    @GET(AppConstant.ServerAPICalls.USERS)
    suspend fun getUser(@Path("username") username: String?): UserModel

    @GET(AppConstant.ServerAPICalls.FOLLOWERS)
    suspend fun getFollowers(@Path("username") username: String?): ArrayList<UserModel>

    @GET(AppConstant.ServerAPICalls.FOLLOWING)
    suspend fun getFollowing(@Path("username") username: String?): ArrayList<UserModel>


}