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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Border
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.MainLightHover
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
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

    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MainLight)
            .padding(horizontal = 20.dp, vertical = 32.dp),
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
                    Image(
                        painter = painterResource(id = R.drawable.dummy_avatar),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(32.dp).clickable {  }
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
            BasicTextField2(
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
                decorator = { textField ->
                    Row {
                        Icon(Icons.Outlined.Search, contentDescription = "Search", tint = Gray)
                        Spacer(modifier = Modifier.width(8.dp))

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
            )
            Spacer(modifier = Modifier.height(16.dp))

            // List chat room
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {  }
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
                        Image(
                            painter = painterResource(id = R.drawable.dummy_avatar),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = "Username",
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
//                        Text(
//                            text = "N",
//                            style = Typography.labelSmall,
//                            color = Color.White,
//                            modifier = Modifier
//                                .size(16.dp)
//                                .clip(RoundedCornerShape(100))
//                                .background(Main)
//                                .padding(horizontal = 4.dp)
//                        )
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
        }

        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet.value = false },
                sheetState = sheetState,
                containerColor = MainLightHover,
                sheetMaxWidth = 608.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicTextField2(
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
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        decorator = { textField ->
                            Row {
                                Icon(
                                    Icons.Outlined.Search,
                                    contentDescription = "Search",
                                    tint = Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))

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
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
                            text = "Friends",
                            style = Typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // List friends
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dummy_avatar),
                                contentDescription = "avatar",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Username",
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = Black
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ },
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

                }
            }
        }
    }
}