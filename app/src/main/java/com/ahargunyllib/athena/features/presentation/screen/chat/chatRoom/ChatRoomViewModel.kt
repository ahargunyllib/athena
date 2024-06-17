package com.ahargunyllib.athena.features.presentation.screen.chat.chatRoom

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.Message
import com.ahargunyllib.athena.features.data.remote.response.MessagesResponse
import com.ahargunyllib.athena.features.data.remote.response.ProfileUserResponse
import com.ahargunyllib.athena.features.domain.repository.ChatRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MessagesState(
    val isLoading: Boolean = false,
    val message: String = "",
    val messages: List<Message>? = null,
)

data class FriendState(
    val isLoading: Boolean = false,
    val message: String = "",
    val profile: ProfileUserResponse? = null,

)
@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
): ViewModel(){
   private val _messagesState = MutableStateFlow(MessagesState())
    val messagesState = _messagesState.asStateFlow()

    private val _friendState = MutableStateFlow(FriendState())
    val friendState = _friendState.asStateFlow()

    fun getMessages(context: Context, chatRoomId: String){
        viewModelScope.launch {
            _messagesState.update { state ->
                state.copy(isLoading = true)
            }

            chatRepository.getMessages(context, chatRoomId).collectLatest { response ->
                when(response){
                    is Response.Loading -> {
                        _messagesState.update { state ->
                            state.copy(isLoading = response.isLoading)
                        }
                    }
                    is Response.Success -> {
                        _messagesState.update { state ->
                            state.copy(isLoading = false, messages = response.data?.data)
                        }
                    }
                    is Response.Error -> {
                        _messagesState.update { state ->
                            state.copy(isLoading = false, message = response.message ?: "An error occurred")
                        }
                    }
                }
            }
        }
    }

    fun getFriend(context: Context, userId: String){
        viewModelScope.launch {
            _friendState.update { state ->
                state.copy(isLoading = true)
            }

            userRepository.getUser(context, userId).collectLatest { response ->
                when(response){
                    is Response.Loading -> {
                        _friendState.update { state ->
                            state.copy(isLoading = response.isLoading)
                        }
                    }
                    is Response.Success -> {
                        _friendState.update { state ->
                            state.copy(isLoading = false, profile = response.data)
                        }
                    }
                    is Response.Error -> {
                        _friendState.update { state ->
                            state.copy(isLoading = false, message = response.message ?: "An error occurred")
                        }
                    }
                }
            }
        }
    }
}