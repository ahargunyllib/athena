package com.ahargunyllib.athena.features.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.presentation.screen.home.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: UserEntity? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            val user = userRepository.getUser()

            _userState.update {
                it.copy(isLoading = false, data = user)
            }

        }
    }

    fun logout(){
        viewModelScope.launch {
            userRepository.deleteUser()
        }
    }

}