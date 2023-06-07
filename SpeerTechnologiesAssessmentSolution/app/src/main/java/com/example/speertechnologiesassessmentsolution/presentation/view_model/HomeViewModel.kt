package com.example.speertechnologiesassessmentsolution.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speertechnologiesassessmentsolution.domain.use_case.FollowersUseCase
import com.example.speertechnologiesassessmentsolution.domain.use_case.FollowingUseCase
import com.example.speertechnologiesassessmentsolution.domain.use_case.UserUseCase
import com.example.speertechnologiesassessmentsolution.network.error_handling.Resource
import com.example.speertechnologiesassessmentsolution.domain.model.ListState
import com.example.speertechnologiesassessmentsolution.domain.model.UserDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase,
                                        private val followersUseCase: FollowersUseCase,
                                        private val followingUseCase: FollowingUseCase)
    : ViewModel() {

    private val _user = MutableSharedFlow<UserDataState>()
    val user: SharedFlow<UserDataState>
        get() = _user.asSharedFlow()

    private val _followers = MutableSharedFlow<ListState>()
    val followers: SharedFlow<ListState>
        get() = _followers.asSharedFlow()

    private val _following = MutableSharedFlow<ListState>()
    val following: SharedFlow<ListState>
        get() = _following.asSharedFlow()

    fun getUser(username:String) = viewModelScope.launch {

        userUseCase(username).collectLatest { result ->
            when (result) {
                is Resource.Success -> {
                    _user.emit(UserDataState( data = result.data, message = ""))
                }
                is Resource.Error -> {
                    _user.emit(UserDataState(error = result.message ?: "An unexpected error occurred"))
                }
                is Resource.Loading -> {
                    _user.emit(UserDataState(isLoading = true))

                }
            }
        }
    }


    fun getFollowers(username:String) = viewModelScope.launch {

        followersUseCase(username).collectLatest { result ->
            when (result) {
                is Resource.Success -> {
                    _followers.emit(ListState( data = result.data, message = ""))
                }
                is Resource.Error -> {
                    _followers.emit(ListState(error = result.message ?: "An unexpected error occurred"))
                }
                is Resource.Loading -> {
                    _followers.emit(ListState(isLoading = true))

                }
            }
        }
    }


    fun getFollowing(username:String) = viewModelScope.launch {

        followingUseCase(username).collectLatest { result ->
            when (result) {
                is Resource.Success -> {
                    _following.emit(ListState( data = result.data, message = ""))
                }
                is Resource.Error -> {
                    _following.emit(ListState(error = result.message ?: "An unexpected error occurred"))
                }
                is Resource.Loading -> {
                    _following.emit(ListState(isLoading = true))

                }
            }
        }
    }
}
