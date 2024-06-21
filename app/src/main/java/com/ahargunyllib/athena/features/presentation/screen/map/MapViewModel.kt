package com.ahargunyllib.athena.features.presentation.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.FriendLocationResponse
import com.ahargunyllib.athena.features.data.remote.response.MinPublicInformation
import com.ahargunyllib.athena.features.domain.model.LocationModel
import com.ahargunyllib.athena.features.domain.repository.LocationRepository
import com.ahargunyllib.athena.features.domain.repository.PublicInformationRepository
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

data class PublicInformationState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: List<MinPublicInformation>? = null
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val publicInformationRepository: PublicInformationRepository
) : ViewModel() {
    private val _friendsLocationState = MutableStateFlow(FriendsLocationState())
    val friendsLocationState = _friendsLocationState.asStateFlow()

    private val _updateLocationState = MutableStateFlow(UpdateLocationState())
    @Suppress("unused")
    val updateLocationState = _updateLocationState.asStateFlow()

    private val _sosSendState = MutableStateFlow(SOSSendState())
    val sosSendState = _sosSendState.asStateFlow()

    private val _publicInformationState = MutableStateFlow(PublicInformationState())
    val publicInformationState = _publicInformationState.asStateFlow()

    fun getFriendsLocation() {
        viewModelScope.launch {
            _friendsLocationState.update { state ->
                state.copy(isLoading = true)
            }

            locationRepository.getFriendsLocation().collectLatest { response ->
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

    fun updateLocation(request: LocationModel) {
        viewModelScope.launch {
            _updateLocationState.update { state ->
                state.copy(isLoading = true)
            }

            locationRepository.updateLocation(request).collectLatest { response ->
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

    fun sendSOS() {
        viewModelScope.launch {
            _sosSendState.update { state ->
                state.copy(isLoading = true)
            }

            locationRepository.sos().collectLatest { response ->
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

    fun getPublicInformation() {
        viewModelScope.launch {
            _publicInformationState.update { state ->
                state.copy(isLoading = true)
            }

            publicInformationRepository.getPublicInformation().collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        _publicInformationState.update { state ->
                            state.copy(isLoading = false, data = response.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _publicInformationState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = response.data?.message ?: "Unknown Error"
                            )
                        }
                    }

                    is Response.Loading -> {
                        _publicInformationState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}