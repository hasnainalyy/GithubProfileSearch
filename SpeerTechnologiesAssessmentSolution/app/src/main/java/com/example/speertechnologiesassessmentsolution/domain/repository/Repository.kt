package com.example.speertechnologiesassessmentsolution.domain.repository

import com.example.speertechnologiesassessmentsolution.domain.model.UserModel
import com.example.speertechnologiesassessmentsolution.network.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ApiService
){

    suspend fun getUser(username: String): UserModel {
        return api.getUser(username)
    }

    suspend fun getFollowers(username:String): ArrayList<UserModel> {
        return api.getFollowers(username)
    }

    suspend fun getFollowing(username:String): ArrayList<UserModel> {
        return api.getFollowing(username)
    }


}

