package com.ahargunyllib.athena.features.presentation.screen.chat

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.FriendListResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendshipResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendshipStatus
import com.ahargunyllib.athena.features.data.remote.response.MinUserResponse
import com.ahargunyllib.athena.features.domain.repository.FriendshipRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FriendListState(
    val isLoading: Boolean = false,
    val message: String = "",
    val data: List<FriendshipResponse>? = null
)

data class SearchUserState(
    val isLoading: Boolean = false,
    val message: String = "",
    val data: List<MinUserResponse>? = null
)


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val friendshipRepository: FriendshipRepository
) : ViewModel() {
    private val _friendListState = MutableStateFlow(FriendListState())
    val friendListState = _friendListState.asStateFlow()

    private val _searchUserState = MutableStateFlow(SearchUserState())
    val searchUserState = _searchUserState.asStateFlow()

    fun getFriendList(context: Context) {
        viewModelScope.launch {
            _friendListState.update { state ->
                state.copy(isLoading = true)
            }

            friendshipRepository.getFriendList(context).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i(
                            "ChatViewModel.getFriendList.Success",
                            "getFriendList: ${response.data}"
                        )
                        _friendListState.update { state ->
                            state.copy(isLoading = false, data = response.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _friendListState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = response.message ?: "Unknown Error"
                            )
                        }
                    }

                    is Response.Loading -> {
                        Log.i("ChatViewModel.getFriendList.Loading", "getFriendList: Loading")
                        _friendListState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    fun searchUser(context: Context, username: String) {
        viewModelScope.launch {
            _searchUserState.update {state ->
                state.copy(isLoading = true)
            }

            friendshipRepository.searchUser(context, username).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i("ChatViewModel.searchUser.Success", "searchUser: ${response.data}")
                        _searchUserState.update { state ->
                            state.copy(isLoading = false, data = response.data?.data)
                        }
                    }

                    is Response.Error -> {
                        _searchUserState.update { state ->
                            state.copy(
                                isLoading = false,
                                message = response.message ?: "Unknown Error"
                            )
                        }
                    }

                    is Response.Loading -> {
                        Log.i("ChatViewModel.searchUser.Loading", "searchUser: Loading")
                        _searchUserState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    fun removeFriend(context: Context, userId: String) {
        viewModelScope.launch {
            friendshipRepository.removeFriend(context, userId).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i(
                            "ChatViewModel.removeFriend.Success",
                            "removeFriend: ${response.data}"
                        )
                        _friendListState.update { state ->
                            state.copy(data = state.data?.filter { user ->
                                user.userId != userId
                            })
                        }
                    }

                    is Response.Error -> {
                        Log.i(
                            "ChatViewModel.removeFriend.Error",
                            "removeFriend: ${response.message}"
                        )
                    }

                    is Response.Loading -> {
                        Log.i("ChatViewModel.removeFriend.Loading", "removeFriend: Loading")
                    }
                }
            }
        }
    }

    fun acceptFriend(context: Context, userId: String) {
        viewModelScope.launch {
            friendshipRepository.acceptFriend(context, userId).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i(
                            "ChatViewModel.acceptFriend.Success",
                            "acceptFriend: ${response.data}"
                        )
                        _friendListState.update { state ->
                            state.copy(data = state.data?.map { user ->
                                if (user.userId == userId) {
                                    user.copy(status = FriendshipStatus.ACCEPTED)
                                } else {
                                    user
                                }
                            })
                        }
                    }

                    is Response.Error -> {
                        Log.i(
                            "ChatViewModel.acceptFriend.Error",
                            "acceptFriend: ${response.message}"
                        )
                    }

                    is Response.Loading -> {
                        Log.i("ChatViewModel.acceptFriend.Loading", "acceptFriend: Loading")
                    }
                }
            }
        }
    }

    fun rejectFriend(context: Context, userId: String) {
        viewModelScope.launch {
            friendshipRepository.rejectFriend(context, userId).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i(
                            "ChatViewModel.rejectFriend.Success",
                            "rejectFriend: ${response.data}"
                        )
                        _friendListState.update { state ->
                            state.copy(data = state.data?.filter { user ->
                                user.userId != userId
                            })
                        }
                    }

                    is Response.Error -> {
                        Log.i(
                            "ChatViewModel.rejectFriend.Error",
                            "rejectFriend: ${response.message}"
                        )
                    }

                    is Response.Loading -> {
                        Log.i("ChatViewModel.rejectFriend.Loading", "rejectFriend: Loading")
                    }
                }
            }
        }
    }

    fun addFriend(context: Context, userId: String) {
        viewModelScope.launch {
            friendshipRepository.addFriendRequest(context, userId).collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i(
                            "ChatViewModel.addFriendRequest.Success",
                            "addFriendRequest: ${response.data}"
                        )
                        clearSearchUserState()
                    }

                    is Response.Error -> {
                        Log.i(
                            "ChatViewModel.addFriendRequest.Error",
                            "addFriendRequest: ${response.message}"
                        )
                    }

                    is Response.Loading -> {
                        Log.i("ChatViewModel.addFriendRequest.Loading", "addFriendRequest: Loading")
                    }
                }
            }
        }
    }

    private fun clearSearchUserState() {
        _searchUserState.value = SearchUserState()
    }
}
