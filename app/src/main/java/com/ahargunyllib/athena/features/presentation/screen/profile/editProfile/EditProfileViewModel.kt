package com.ahargunyllib.athena.features.presentation.screen.profile.editProfile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.UserRegisterResponse
import com.ahargunyllib.athena.features.domain.model.UpdateModel
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: UserRegisterResponse? = null
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {
    private val _editProfileState = MutableStateFlow(EditProfileState())
    val editProfileState = _editProfileState.asStateFlow()

    fun updateUser(context: Context, updateModel: UpdateModel) {
        viewModelScope.launch {
            _editProfileState.update { state ->
                state.copy(isLoading = true)
            }

            userRepository.updateUser(context, updateModel).collect {
                when(it){
                    is Response.Loading -> {
                        _editProfileState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                    is Response.Success -> {
                        _editProfileState.update { state ->
                            state.copy(isLoading = false, data = it.data?.data)
                        }
                    }
                    is Response.Error -> {
                        _editProfileState.update { state ->
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