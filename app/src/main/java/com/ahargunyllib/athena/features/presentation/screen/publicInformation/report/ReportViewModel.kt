package com.ahargunyllib.athena.features.presentation.screen.publicInformation.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.domain.model.CreateReportModel
import com.ahargunyllib.athena.features.domain.repository.PublicInformationRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportState(
    val isLoading: Boolean = false,
    val message: String = "",
    val data: List<Any>? = null
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val publicInformationRepository: PublicInformationRepository,
) : ViewModel() {
    private val _reportState = MutableStateFlow(ReportState())
    val reportState = _reportState.asStateFlow()

    fun createReport(
        createReportModel: CreateReportModel,
        publicInformationId: String
    ) {
        viewModelScope.launch {
            _reportState.update {
                it.copy(isLoading = true)
            }

            publicInformationRepository.reportPublicInformation(
                createReportModel,
                publicInformationId
            ).collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _reportState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Response.Success -> {
                        _reportState.update { state ->
                            state.copy(isLoading = false, data = response.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _reportState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = response.data?.message ?: "An error occurred"
                            )
                        }
                    }
                }
            }
        }
    }
}