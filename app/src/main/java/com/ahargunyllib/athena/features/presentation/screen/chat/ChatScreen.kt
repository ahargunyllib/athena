package com.ahargunyllib.athena.features.presentation.screen.chat

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material.icons.outlined.PersonSearch
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.data.remote.response.FriendshipStatus
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Border
import com.ahargunyllib.athena.features.presentation.designSystem.Danger
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.MainLightHover
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileViewModel
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    parentController: NavController,
    bottomNavController: NavController,
    onChangeScreen: (Int) -> Unit
) {
    val usernameToFind = remember { mutableStateOf("") }
    val usernameToChat = remember { mutableStateOf("") }

    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }

    val chatViewModel: ChatViewModel = hiltViewModel()
    val friendListState = chatViewModel.friendListState.collectAsState()
    val searchUserState = chatViewModel.searchUserState.collectAsState()
    val chatRoomsState = chatViewModel.chatRoomsState.collectAsState()

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userState = profileViewModel.userState.collectAsState()

    LaunchedEffect(Unit) {
        chatViewModel.getChatRooms(context)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MainLight)
            .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 92.dp),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainLight)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = userState.value.data?.imageUrl,
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable {
                                parentController.navigate(ParentNavObj.ProfileNavObj.route)
                            }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Chat",
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                }


                Button(
                    onClick = { showBottomSheet.value = true },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Main
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.PersonAddAlt,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = "Add Friend",
                        style = Typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            BasicTextField(
                value = usernameToChat.value,
                onValueChange = { usernameToChat.value = it },
                textStyle = Typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Search
                        Log.i("ChatScreen", "username: ${usernameToChat.value}")
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                decorationBox = { textField ->
                    Row {
                        Icon(Icons.Outlined.Search, contentDescription = "Search", tint = Gray)
                        Spacer(modifier = Modifier.width(8.dp))

                        Box {
                            if (usernameToChat.value.isEmpty()) {
                                Text(
                                    text = "Search friends",
                                    style = Typography.bodyLarge,
                                    color = Gray
                                )
                            }
                            textField()
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // List chat room
            if (chatRoomsState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .size(32.dp),
                    color = Main
                )
            } else {
                LazyColumn {
                    items(chatRoomsState.value.data?.size ?: 0) { index ->
                        val chatRoom = chatRoomsState.value.data?.get(index)
                        if (chatRoom != null) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        parentController.navigate("ParentNavObj.ChatRoomNavObj.route/${chatRoom.chatRoomId}/${chatRoom.friend.userId}")
                                    }
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            model = chatRoom.friend.imageUrl,
                                            contentDescription = "avatar",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(32.dp).clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(
                                            verticalArrangement = Arrangement.SpaceBetween,
                                            horizontalAlignment = Alignment.Start,
                                        ) {
                                            Text(
                                                text = chatRoom.friend.username,
                                                style = Typography.labelLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = Black
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Recent Message",
                                                style = Typography.bodySmall,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Gray
                                            )
                                        }
                                    }

                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Time",
                                            style = Typography.labelSmall,
                                            color = Main
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        IconButton(
                                            onClick = { /*TODO*/ },
                                            colors = IconButtonDefaults.iconButtonColors(
                                                contentColor = Color.White,
                                                containerColor = Main
                                            ),
                                            modifier = Modifier.size(16.dp)
                                        ) {
                                            Text(text = "1", style = Typography.labelSmall)
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

            }
        }

        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet.value = false },
                sheetState = sheetState,
                containerColor = MainLightHover,
                sheetMaxWidth = 608.dp,
            ) {
                LaunchedEffect(Unit) {
                    chatViewModel.getFriendList(context)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicTextField(
                        value = usernameToFind.value,
                        onValueChange = { usernameToFind.value = it },
                        textStyle = Typography.bodyLarge,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                // Search
                                Log.i(
                                    "ChatScreen.Dialog",
                                    "usernameToFind: ${usernameToFind.value}"
                                )

                                chatViewModel.searchUser(context, usernameToFind.value)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        decorationBox = { textField ->
                            Row {
                                Icon(
                                    Icons.Outlined.Search,
                                    contentDescription = "Search",
                                    tint = Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Box(modifier = Modifier.fillMaxWidth()) {
                                    if (usernameToFind.value.isEmpty()) {
                                        Text(
                                            text = "Add friend",
                                            style = Typography.bodyLarge,
                                            color = Gray
                                        )
                                    }
                                    textField()
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (usernameToFind.value != "") {
                        if (searchUserState.value.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(top = 64.dp)
                                    .size(32.dp),
                                color = Main
                            )
                        } else if (searchUserState.value.data != null) {
                            val users = searchUserState.value.data
                            Log.i("ChatScreen", "user: $users")
                            if (users?.size == 0) {
                                Text(
                                    text = "No user found",
                                    style = Typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Gray
                                )
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        AsyncImage(
                                            model = users?.get(0)?.imageUrl,
                                            contentDescription = "avatar",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(48.dp).clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = users?.get(0)?.username ?: "No Username",
                                            style = Typography.bodySmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Black
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            chatViewModel.addFriend(
                                                context,
                                                users?.get(0)?.userId ?: ""
                                            )

                                            usernameToFind.value = ""
                                            chatViewModel.getFriendList(context)
                                            chatViewModel.getChatRooms(context)
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = Color.White,
                                            containerColor = Main
                                        )
                                    ) {
                                        Icon(
                                            Icons.Outlined.Add,
                                            contentDescription = "Add",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(
                                text = "No user found",
                                style = Typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray
                            )
                        }
                    } else {
                        if (friendListState.value.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(top = 64.dp)
                                    .size(32.dp),
                                color = Main
                            )
                        } else if (friendListState.value.data.isNullOrEmpty()) {
                            Text(
                                text = "No friend found",
                                style = Typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray
                            )
                        } else {
                            val friendList = friendListState.value.data ?: emptyList()
                            val userId = userState.value.data?.userId ?: ""
                            Log.i("ChatScreen", "friendList: $friendList")

                            val isFriend = friendList.any { it.status == FriendshipStatus.ACCEPTED }
                            val isRequest = friendList.any { it.status == FriendshipStatus.PENDING }

                            if (isFriend) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Icon(
                                        Icons.Outlined.PeopleOutline,
                                        contentDescription = "Friends",
                                        tint = Gray
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Your Friends",
                                        style = Typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Gray
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            LazyColumn {
                                items(friendList.size) { index ->
                                    if (friendList[index].status == FriendshipStatus.ACCEPTED) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                AsyncImage(
                                                    model = friendList[index].imageUrl,
                                                    contentDescription = "avatar",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier.size(48.dp).clip(CircleShape)
                                                )
                                                Spacer(modifier = Modifier.width(16.dp))
                                                Text(
                                                    text = friendList[index].username,
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Black
                                                )
                                            }
                                            IconButton(
                                                onClick = {
                                                    chatViewModel.removeFriend(
                                                        context,
                                                        friendList[index].userId
                                                    )

                                                    chatViewModel.getFriendList(context)
                                                },
                                                colors = IconButtonDefaults.iconButtonColors(
                                                    contentColor = Color.White,
                                                    containerColor = Main
                                                )
                                            ) {
                                                Icon(
                                                    Icons.Filled.Close,
                                                    contentDescription = "Close",
                                                    tint = Color.White
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }

                            if (isFriend) {
                                Spacer(modifier = Modifier.height(28.dp))
                            }

                            if (isRequest) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Icon(
                                        Icons.Outlined.PersonSearch,
                                        contentDescription = "Friend Requests",
                                        tint = Gray
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Friend Requests",
                                        style = Typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Gray
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                            }
                            LazyColumn {
                                items(friendList.size) { index ->
                                    if (friendList[index].status == FriendshipStatus.PENDING) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                AsyncImage(
                                                    model = friendList[index].imageUrl,
                                                    contentDescription = "avatar",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier.size(48.dp).clip(CircleShape)
                                                )
                                                Spacer(modifier = Modifier.width(16.dp))
                                                Text(
                                                    text = friendList[index].username,
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Black
                                                )
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                IconButton(
                                                    onClick = {
                                                        chatViewModel.acceptFriend(
                                                            context,
                                                            friendList[index].userId
                                                        )

                                                        chatViewModel.getFriendList(context)
                                                    },
                                                    colors = IconButtonDefaults.iconButtonColors(
                                                        contentColor = Color.White,
                                                        containerColor = Main
                                                    )
                                                ) {
                                                    Icon(
                                                        Icons.Filled.Check,
                                                        contentDescription = "Accept",
                                                        tint = Color.White
                                                    )
                                                }
                                                IconButton(
                                                    onClick = {
                                                        chatViewModel.rejectFriend(
                                                            context,
                                                            friendList[index].userId
                                                        )

                                                        chatViewModel.getFriendList(context)
                                                    },
                                                    colors = IconButtonDefaults.iconButtonColors(
                                                        contentColor = Danger,
                                                        containerColor = Color.White
                                                    )
                                                ) {
                                                    Icon(
                                                        Icons.Filled.Close,
                                                        contentDescription = "Reject",
                                                        tint = Danger
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}