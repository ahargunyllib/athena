package com.ahargunyllib.athena.features.presentation.screen.auth.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.UserLoginResponse
import com.ahargunyllib.athena.features.domain.model.LoginModel
import com.ahargunyllib.athena.features.domain.repository.AuthRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: UserLoginResponse? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun login(
        context: Context,
        request: LoginModel
    ){
        viewModelScope.launch {
            _loginState.update {
                it.copy(isLoading = true)
            }

            // validate email
            if (request.email.isEmpty()) {
                _loginState.update {
                    it.copy(isLoading = false, message = "Email is required")
                }
                return@launch
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(request.email).matches()) {
                _loginState.update {
                    it.copy(isLoading = false, message = "Invalid email address")
                }
                return@launch
            }

            // validate password
            if (request.password.isEmpty()) {
                _loginState.update {
                    it.copy(isLoading = false, message = "Password is required")
                }
                return@launch
            }

            // call api
            repository.login(context, request).collectLatest {it ->
                when (it){
                    is Response.Success -> {
                        _loginState.update {
                            it.copy(isLoading = false, data = it.data, message = "Success")
                        }
                    }
                    is Response.Error -> {
                        _loginState.update {
                            it.copy(isLoading = false, message = "Error: ${it.message}")
                        }
                    }
                    is Response.Loading -> {
                        _loginState.update {
                            it.copy(isLoading = it.isLoading)
                        }
                    }
                }
            }
        }
    }
}