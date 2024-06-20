package com.ahargunyllib.athena.features.presentation.screen.map

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.FriendLocationResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendsLocationResponse
import com.ahargunyllib.athena.features.domain.model.LocationModel
import com.ahargunyllib.athena.features.domain.repository.LocationRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FriendsLocationState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: List<FriendLocationResponse>? = null
)

data class UpdateLocationState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: List<Any>? = null
)

data class SOSSendState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: List<Any>? = null
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _friendsLocationState = MutableStateFlow(FriendsLocationState())
    val friendsLocationState = _friendsLocationState.asStateFlow()

    private val _updateLocationState = MutableStateFlow(UpdateLocationState())
    val updateLocationState = _updateLocationState.asStateFlow()

    private val _sosSendState = MutableStateFlow(SOSSendState())
    val sosSendState = _sosSendState.asStateFlow()

    fun getFriendsLocation(context: Context) {
        viewModelScope.launch {
            _friendsLocationState.update { state ->
                state.copy(isLoading = true)
            }

            locationRepository.getFriendsLocation(context).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        _friendsLocationState.update { state ->
                            state.copy(isLoading = false, data = response.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _friendsLocationState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = response.data?.message ?: "Unknown Error"
                            )
                        }
                    }

                    is Response.Loading -> {
                        _friendsLocationState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    fun updateLocation(context: Context, request: LocationModel) {
        viewModelScope.launch {
            _updateLocationState.update { state ->
                state.copy(isLoading = true)
            }

            locationRepository.updateLocation(context, request).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        _updateLocationState.update { state ->
                            state.copy(isLoading = false, data = response.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _updateLocationState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = response.data?.message ?: "Unknown Error"
                            )
                        }
                    }

                    is Response.Loading -> {
                        _updateLocationState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }

    }

    fun sendSOS(context: Context) {
        viewModelScope.launch {
            _sosSendState.update { state ->
                state.copy(isLoading = true)
            }

            locationRepository.sos(context).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        _sosSendState.update { state ->
                            state.copy(isLoading = false, data = response.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _sosSendState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = response.data?.message ?: "Unknown Error"
                            )
                        }
                    }

                    is Response.Loading -> {
                        _sosSendState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }

        }
    }
}