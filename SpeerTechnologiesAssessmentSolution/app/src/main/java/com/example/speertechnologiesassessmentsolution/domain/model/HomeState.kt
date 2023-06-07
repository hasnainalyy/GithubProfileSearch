package com.example.speertechnologiesassessmentsolution.domain.model

import com.example.speertechnologiesassessmentsolution.domain.model.UserModel

data class UserDataState(
        val isLoading: Boolean = false,
        val data: UserModel?=null,
        val message: String = "",
        val error: String = ""
    )

data class ListState(
    val isLoading: Boolean = false,
    val data:  ArrayList<UserModel>?=null,
    val message: String = "",
    val error: String = ""
)