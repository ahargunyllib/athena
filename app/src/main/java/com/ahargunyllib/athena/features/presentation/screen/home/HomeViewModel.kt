package com.ahargunyllib.athena.features.presentation.screen.home

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.data.remote.response.UserLoginResponse
import com.ahargunyllib.athena.features.domain.repository.AuthRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: UserEntity? = null
)

data class TokenState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private val _tokenState = MutableStateFlow(TokenState())
    val tokenState = _tokenState.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            var user = userRepository.getUser()

            // if there is user, refresh its token
            // after refreshing token, get user again, and update user state
            if (user != null) {
                refreshToken(user.token)
                user = userRepository.getUser()
            }

            _userState.update {
                it.copy(isLoading = false, data = user)
            }

        }
    }

    private fun refreshToken(token: String) {
        viewModelScope.launch {
            authRepository.refreshToken(token).collectLatest {it ->
                when (it) {
                    is Response.Success -> {
                        _tokenState.update {
                            it.copy(isLoading = false, data = it.data)
                        }
                    }
                    is Response.Error -> {
                        _tokenState.update {
                            it.copy(isLoading = false, message = it.message)
                        }
                    }
                    is Response.Loading -> {
                        _tokenState.update {
                            it.copy(isLoading = it.isLoading)
                        }
                    }
                }
            }
        }
    }
}