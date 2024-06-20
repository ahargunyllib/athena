package com.ahargunyllib.athena.features.presentation.screen.publicInformation.post

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.data.remote.response.Author
import com.ahargunyllib.athena.features.data.remote.response.Comment
import com.ahargunyllib.athena.features.data.remote.response.MaxPublicInformation
import com.ahargunyllib.athena.features.domain.model.CreateCommentModel
import com.ahargunyllib.athena.features.domain.repository.PublicInformationRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: MaxPublicInformation? = null
)

data class CommentState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: List<Any>? = null
)

data class UserState(
    val isLoading: Boolean = true,
    val message: String = "",
    val data: UserEntity? = null
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val publicInformationRepository: PublicInformationRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _postState = MutableStateFlow(PostState())
    val postState = _postState.asStateFlow()

    private val _commentState = MutableStateFlow(CommentState())
    val commentState = _commentState.asStateFlow()

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
            Log.i("ProfileViewModel.getUser", "getUser: $user")
        }
    }

    fun getPublicInformationById(context: Context, publicInformationId: String) {
        viewModelScope.launch {
            _postState.update { state ->
                state.copy(isLoading = true)
            }

            publicInformationRepository.getPublicInformationById(context, publicInformationId)
                .collectLatest { response ->
                    when (response) {
                        is Response.Success -> {
                            _postState.update { state ->
                                state.copy(isLoading = false, data = response.data?.data)
                            }
                        }

                        is Response.Error -> {
                            _postState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    message = response.data?.message ?: "An error occurred"
                                )
                            }
                        }

                        is Response.Loading -> {
                            _postState.update { state ->
                                state.copy(isLoading = true)
                            }
                        }
                    }
                }
        }
    }

    fun createComment(
        context: Context,
        createCommentModel: CreateCommentModel,
        publicInformationId: String
    ) {
        viewModelScope.launch {
            _commentState.update { state ->
                state.copy(isLoading = true)
            }

            publicInformationRepository.createComment(
                context,
                createCommentModel,
                publicInformationId
            )
                .collectLatest { response ->
                    when (response) {
                        is Response.Success -> {
                            _commentState.update { state ->
                                state.copy(isLoading = false, data = response.data?.data)
                            }

                            _postState.update { state ->
                                state.copy(
                                    data = state.data?.copy(
                                        post = state.data.post.copy(
                                            comments = state.data.post.comments.plus(
                                                Comment(
                                                    commentId = "",
                                                    authorId = userState.value.data?.userId ?: "",
                                                    author = Author(
                                                        userId = userState.value.data?.userId ?: "",
                                                        username = userState.value.data?.username
                                                            ?: "",
                                                        fullName = userState.value.data?.fullName
                                                            ?: "",
                                                        imageUrl = userState.value.data?.imageUrl
                                                            ?: ""
                                                    ),
                                                    content = createCommentModel.content,
                                                    createdAt = ""
                                                )
                                            )
                                        )
                                    )
                                )
                            }
                        }

                        is Response.Error -> {
                            _commentState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    message = response.data?.message ?: "An error occurred"
                                )
                            }
                        }

                        is Response.Loading -> {
                            _commentState.update { state ->
                                state.copy(isLoading = true)
                            }
                        }
                    }
                }
        }
    }
}