package com.ahargunyllib.athena.features.presentation.screen.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.UserRegisterResponse
import com.ahargunyllib.athena.features.domain.model.RegisterModel
import com.ahargunyllib.athena.features.domain.repository.AuthRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: UserRegisterResponse? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun register(
        request: RegisterModel
    ) {
        viewModelScope.launch {
            _registerState.update {
                it.copy(isLoading = true)
            }

            // validate fullName
            if (request.fullName.isEmpty()) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Full Name is required")
                }
                return@launch
            }

            // validate username
            if (request.username.isEmpty()) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Username is required")
                }
                return@launch
            }

            // validate email
            if (request.email.isEmpty()) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Email is required")
                }
                return@launch
            } else if (!Patterns.EMAIL_ADDRESS.matcher(request.email).matches()) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Invalid email address")
                }
                return@launch
            }

            // validate date
            if (request.dateOfBirth == "1970-01-01T00:00:00Z") {
                _registerState.update {
                    it.copy(isLoading = false, message = "Date of Birth is required")
                }
                return@launch
            } else if (request.dateOfBirth.length < 10) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Invalid date of birth")
                }
                return@launch
            }

            // validate phone number
            if (request.phoneNumber.isEmpty()) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Phone Number is required")
                }
                return@launch
            } else if (request.phoneNumber.length < 10) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Invalid phone number")
                }
                return@launch
            }

                // validate password
                if (request.password.isEmpty()) {
                    _registerState.update {
                        it.copy(isLoading = false, message = "Password is required")
                    }
                    return@launch
                } else if (request.password.length < 6) {
                    _registerState.update {
                        it.copy(isLoading = false, message = "Password must be at least 6 characters")
                    }
                    return@launch
                }

            // validate confirmPassword
            if (request.confirmPassword.isEmpty()) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Confirm Password is required")
                }
                return@launch
            } else if (request.confirmPassword != request.password) {
                _registerState.update {
                    it.copy(isLoading = false, message = "Password does not match")
                }
                return@launch
            }

            // call api
            repository.register(request).collectLatest { it ->
                when (it) {
                    is Response.Success -> {
                        _registerState.update {
                            it.copy(isLoading = false, data = it.data, message = "Success")
                        }
                    }

                    is Response.Error -> {
                        _registerState.update {
                            it.copy(isLoading = false, message = "Error: ${it.message}")
                        }
                    }

                    is Response.Loading -> {
                        _registerState.update {
                            it.copy(isLoading = it.isLoading)
                        }
                    }
                }
            }
        }
    }
}