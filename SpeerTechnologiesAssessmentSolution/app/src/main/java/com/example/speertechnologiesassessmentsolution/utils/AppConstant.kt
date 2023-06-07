package com.example.speertechnologiesassessmentsolution.utils

class AppConstant {

    object ServerAPICalls {

        // staging
        const val BASE_URL = "https://api.github.com/"

        //fetch user data by username
        const val USERS = "users/{username}"
        const val FOLLOWERS = "$USERS/followers"
        const val FOLLOWING = "$USERS/following"



    }

}