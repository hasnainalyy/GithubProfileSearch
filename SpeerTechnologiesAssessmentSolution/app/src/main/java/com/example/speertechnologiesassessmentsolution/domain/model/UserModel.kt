package com.example.speertechnologiesassessmentsolution.domain.model

data class UserModel(
    val avatar_url: String,
    val bio: String?,
    val followers: Int?,
    val followers_url: String,
    val following: Int?,
    val following_url: String,
    val id: Int,
    val login: String,
    val name: String?
)


