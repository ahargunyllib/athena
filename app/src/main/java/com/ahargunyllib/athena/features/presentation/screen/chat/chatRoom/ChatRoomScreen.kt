package com.ahargunyllib.athena.features.presentation.screen.chat.chatRoom

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.BottomNavObj
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatRoomScreen(
    parentController: NavController,
    chatRoomId: String,
    friendId: String
) {
    val message = remember { mutableStateOf("") }
    val context = LocalContext.current

    val chatRoomViewModel: ChatRoomViewModel = hiltViewModel()
    val messagesState = chatRoomViewModel.messagesState.collectAsState()
    val friendState = chatRoomViewModel.friendState.collectAsState()

    LaunchedEffect(Unit) {
        chatRoomViewModel.initializeSocket(chatRoomId)
        chatRoomViewModel.getMessages(context, chatRoomId)
        chatRoomViewModel.getFriend(context, friendId)
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    if (friendState.value.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(32.dp),
                            color = Color.White
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            AsyncImage(
                                model = friendState.value.profile?.data?.imageUrl ?: "",
                                contentDescription = "avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(32.dp).clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                friendState.value.profile?.data?.fullName ?: "",
                                style = Typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            chatRoomViewModel.exitRoom(chatRoomId)
                            parentController.navigate(ParentNavObj.BottomNavObj.route)
                        },
                        content = {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        })
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Main),
            )
        },
        content = { it ->
            if (messagesState.value.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .size(32.dp),
                    color = Main
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(messagesState.value.messages?.size ?: 0) { index ->
                        Spacer(modifier = Modifier.height(16.dp))

//                        TimeBadge()
//                        Spacer(modifier = Modifier.height(32.dp))

                        val message = messagesState.value.messages?.get(index)
                        val createdAt = message?.createdAt?.let {
                            val date = Date.from(Instant.parse(it))
                            DateFormat.format("HH:mm", date).toString()
                        }
                        if (message != null) {
                            BubbleChat(
                                isSender = message.senderId != friendId,
                                content = message.content,
                                type = message.type,
                                createdAt = createdAt ?: ""
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(bottom = 32.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    textStyle = Typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            chatRoomViewModel.sendMessage(chatRoomId, message.value, "text")
                            message.value = ""
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Main,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    decorationBox = { textField ->
                        Row {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                if (message.value.isEmpty()) {
                                    Text(
                                        text = "Message",
                                        style = Typography.bodyLarge,
                                        color = Gray
                                    )
                                }
                                textField()
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        chatRoomViewModel.sendMessage(chatRoomId, message.value, "text")
                        message.value = ""
                    },
                    content = {
                        Icon(
                            Icons.AutoMirrored.Outlined.Send,
                            contentDescription = "Send",

                            )
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Main,
                        contentColor = Color.White,
                    ),
                )
            }
        }
    )
}

@Composable
private fun TimeBadge() {
    Text(
        "31 Mei, 2024",
        style = Typography.labelSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(MainLight, RoundedCornerShape(32.dp))
            .padding(vertical = 4.dp, horizontal = 8.dp)
    )
}

@Composable
private fun BubbleChat(
    isSender: Boolean,
    content: String,
    type: String,
    createdAt: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isSender) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (isSender) {
            Text(
                createdAt,
                color = Gray,
                style = Typography.titleSmall.merge(TextStyle(fontSize = 8.sp))
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            content, style = Typography.bodySmall,
            modifier = Modifier
                .background(
                    if (isSender) Main else MainLight,
                    RoundedCornerShape(
                        topEnd = 16.dp,
                        topStart = 16.dp,
                        bottomStart = if (isSender) 16.dp else 0.dp,
                        bottomEnd = if (isSender) 0.dp else 16.dp
                    )
                )
                .padding(16.dp),
            color = if (isSender) Color.White else Black
        )
        if (!isSender) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                createdAt,
                color = Gray,
                style = Typography.titleSmall.merge(TextStyle(fontSize = 8.sp))
            )
        }
    }
}
