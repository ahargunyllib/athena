package com.ahargunyllib.athena.features.presentation.screen.profile.editCredentials

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.domain.model.CredentialsModel
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.presentation.screen.auth.register.RegisterState
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCredentialsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _editCredentialsState = MutableStateFlow(RegisterState())
    val editCredentialsState = _editCredentialsState.asStateFlow()

    fun updateUser(credentialsModel: CredentialsModel) {
        viewModelScope.launch {
            _editCredentialsState.update { state ->
                state.copy(isLoading = true)
            }

            // validate email
            if (credentialsModel.email.isEmpty()) {
                _editCredentialsState.update {
                    it.copy(isLoading = false, message = "Email is required")
                }
                return@launch
            } else if (!Patterns.EMAIL_ADDRESS.matcher(credentialsModel.email).matches()) {
                _editCredentialsState.update {
                    it.copy(isLoading = false, message = "Invalid email address")
                }
                return@launch
            }

            // validate password
            if (credentialsModel.password.isEmpty()) {
                _editCredentialsState.update {
                    it.copy(isLoading = false, message = "Password is required")
                }
                return@launch
            } else if (credentialsModel.password.length < 6) {
                _editCredentialsState.update {
                    it.copy(isLoading = false, message = "Password must be at least 6 characters")
                }
                return@launch
            }

            // validate confirmPassword
            if (credentialsModel.confirmPassword.isEmpty()) {
                _editCredentialsState.update {
                    it.copy(isLoading = false, message = "Confirm Password is required")
                }
                return@launch
            } else if (credentialsModel.confirmPassword != credentialsModel.password) {
                _editCredentialsState.update {
                    it.copy(isLoading = false, message = "Password does not match")
                }
                return@launch
            }

            userRepository.updateCredentials(credentialsModel).collectLatest {
                when (it) {
                    is Response.Loading -> {
                        _editCredentialsState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Response.Success -> {
                        _editCredentialsState.update { state ->
                            state.copy(isLoading = false, data = it.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _editCredentialsState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = it.data?.message ?: "Unknown Error"
                            )
                        }
                    }
                }
            }
        }
    }
}