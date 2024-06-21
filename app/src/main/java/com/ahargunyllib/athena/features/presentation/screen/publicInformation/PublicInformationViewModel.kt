package com.ahargunyllib.athena.features.presentation.screen.publicInformation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.domain.model.CreatePublicInformationModel
import com.ahargunyllib.athena.features.domain.repository.PublicInformationRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PublicInformationState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: List<Any>? = null
)

@HiltViewModel
class PublicInformationViewModel @Inject constructor(
    private val publicInformationRepository: PublicInformationRepository
) : ViewModel() {

    private val _publicInformationState = MutableStateFlow(PublicInformationState())
    val publicInformationState = _publicInformationState.asStateFlow()

    fun createPublicInformation(
        context: Context,
        createPublicInformationModel: CreatePublicInformationModel
    ) {
        viewModelScope.launch {
            _publicInformationState.update { state ->
                state.copy(isLoading = true)
            }

            publicInformationRepository.createPublicInformation(
                context,
                createPublicInformationModel
            ).collect { response ->
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
                                message = response.data?.message ?: "An error occurred"
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