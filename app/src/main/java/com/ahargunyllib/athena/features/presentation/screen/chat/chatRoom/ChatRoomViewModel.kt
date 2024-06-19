package com.ahargunyllib.athena.features.presentation.screen.chat.chatRoom

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahargunyllib.athena.features.data.remote.response.Message
import com.ahargunyllib.athena.features.data.remote.response.MessagesResponse
import com.ahargunyllib.athena.features.data.remote.response.MinUserResponse
import com.ahargunyllib.athena.features.data.remote.response.ProfileUserResponse
import com.ahargunyllib.athena.features.domain.repository.ChatRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Constants
import com.ahargunyllib.athena.features.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URISyntaxException
import java.time.LocalDate
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
) : ViewModel() {
    private var socket: Socket? = null

    private val _messagesState = MutableStateFlow(MessagesState())
    val messagesState = _messagesState.asStateFlow()

    private val _friendState = MutableStateFlow(FriendState())
    val friendState = _friendState.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    fun initializeSocket(chatRoomId: String) {
        viewModelScope.launch {
            try {
                val token = userRepository.getToken()
                val options = IO.Options().apply {
                    query = "token=$token"
                }
                socket = IO.socket(Constants.SOCKET_URL, options)

                socket?.on("message") { args ->
                    if (args.isNotEmpty()) {
                        val json = args[0] as JSONObject

                        val message = Message(
                            messageId = "",
                            chatRoomId = json.getString("chatRoomId"),
                            senderId = json.getString("senderId"),
                            content = json.getString("message"),
                            type = json.getString("type"),
                            createdAt = json.getString("createdAt"),
                            sender = MinUserResponse(
                                userId = "",
                                fullName = "",
                                username = "",
                                imageUrl = ""
                            )
                        )

                        _messagesState.update { state ->
                            state.copy(messages = state.messages?.plus(message))
                        }
                    }
                }

                socket?.connect()
                joinRoom(chatRoomId)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }
    }

    fun getMessages(context: Context, chatRoomId: String) {
        viewModelScope.launch {
            _messagesState.update { state ->
                state.copy(isLoading = true)
            }

            chatRepository.getMessages(context, chatRoomId).collectLatest { response ->
                when (response) {
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
                            state.copy(
                                isLoading = false,
                                message = response.message ?: "An error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    fun getFriend(context: Context, userId: String) {
        viewModelScope.launch {
            _friendState.update { state ->
                state.copy(isLoading = true)
            }

            userRepository.getUser(context, userId).collectLatest { response ->
                when (response) {
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
                            state.copy(
                                isLoading = false,
                                message = response.message ?: "An error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun joinRoom(chatRoomId: String) {
        val json = JSONObject().apply {
            put("chatRoomId", chatRoomId)
        }
        socket?.emit("joinRoom", json)

        Log.i("ChatRoomViewModel", "joinRoom: $chatRoomId")
    }

    fun exitRoom(chatRoomId: String) {
        val json = JSONObject().apply {
            put("chatRoomId", chatRoomId)
        }
        socket?.emit("exitRoom", json)
    }

    fun sendMessage(chatRoomId: String, message: String, type: String) {
        val json = JSONObject().apply {
            put("chatRoomId", chatRoomId)
            put("message", message)
            put("type", type)
        }

        socket?.emit(
            "message",
            json
        )
    }

    override fun onCleared() {
        super.onCleared()
        // Disconnect socket saat ViewModel dihancurkan
        socket?.disconnect()
        socket?.off() // Opsional: Hapus semua listener
    }
}