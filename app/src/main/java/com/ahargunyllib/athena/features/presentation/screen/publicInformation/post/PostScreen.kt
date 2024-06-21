package com.ahargunyllib.athena.features.presentation.screen.publicInformation.post

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ahargunyllib.athena.features.domain.model.CreateCommentModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.MainLightHover
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import java.time.Instant
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    parentController: NavController,
    publicInformationId: String,
) {
    val postViewModel: PostViewModel = hiltViewModel()
    val postState by postViewModel.postState.collectAsState()

    val content = remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        postViewModel.getPublicInformationById(publicInformationId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MainLight),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Public Information",
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold,

                        )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            parentController.navigate(ParentNavObj.BottomNavObj.route)
                        },
                        content = {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",

                                )
                        })
                },
                actions = {
                    IconButton(
                        onClick = {
                            showBottomSheet.value = true
                        },
                        content = {
                            Icon(
                                Icons.Outlined.Menu,
                                contentDescription = "Menu",

                                )
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainLight,
                    titleContentColor = Black,
                    actionIconContentColor = Black,
                    navigationIconContentColor = Black

                )
            )
        },
        content = { paddingValues ->
            if (postState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .size(32.dp),
                    color = Main
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainLight)
                        .padding(paddingValues)
                        .padding(vertical = 32.dp, horizontal = 20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        // User
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            AsyncImage(
                                model = postState.data?.author?.imageUrl ?: "",
                                contentDescription = "avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = postState.data?.author?.username ?: "",
                                style = Typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = Black
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Content
                        Text(
                            text = postState.data?.post?.content ?: "",
                            style = Typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Black
                        )

                        if (postState.data?.post?.imageUrl != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = postState.data?.post?.imageUrl ?: "",
                                contentDescription = "image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // createdAt
                        Text(
                            text = postState.data?.post?.createdAt?.let {
                                val date = Date.from(Instant.parse(it))
                                DateFormat.format("dd MMM yyyy HH:mm", date).toString()
                            } ?: "",
                            style = Typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // comments count
                        Text(
                            text = "${postState.data?.post?.comments?.size} Comments",
                            style = Typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        ) {
                        items(postState.data?.post?.comments?.size ?: 0) { index ->
                            val comment = postState.data?.post?.comments?.get(index)
                            val showReply = remember { mutableStateOf(false) }

                            Column {
                                // User
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    AsyncImage(
                                        model = comment?.author?.imageUrl ?: "",
                                        contentDescription = "avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = comment?.author?.username ?: "",
                                        style = Typography.labelSmall,
                                        color = Black
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // content
                                Text(
                                    text = comment?.content ?: "",
                                    style = Typography.bodySmall,
                                    color = Black
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // reply if exist
                                Text(
                                    text = "Reply",
                                    style = Typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Black
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .clickable {
                                            showReply.value = !showReply.value
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        Icons.Outlined.KeyboardArrowDown,
                                        contentDescription = "Send",
                                        tint = Main
                                    )
                                    Text(
                                        text = "X Reply",
                                        style = Typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Main
                                    )
                                }

                                /*
                                if (showReply.value) {
                                    // reply content
                                    Column(
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    ) {
                                        // User
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start,
                                        ) {
                                            AsyncImage(
                                                model = comment?.author?.imageUrl ?: "",
                                                contentDescription = "avatar",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(CircleShape)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = comment?.author?.username ?: "",
                                                style = Typography.labelSmall,
                                                color = Black
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // content
                                        Text(
                                            text = comment?.content ?: "",
                                            style = Typography.bodySmall,
                                            color = Black
                                        )
                                    }
                                }
                                 */
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
                ) {
                    Column {
                        // Report Button
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(48.dp),
                            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Main,
                                contentColor = Color.White,
                                disabledContentColor = Color.White.copy(alpha = 0.5f),
                                disabledContainerColor = Main.copy(alpha = 0.5f),
                            ),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                parentController.navigate("${ParentNavObj.ReportNavObj.route}/$publicInformationId")
                            },
                        ) {
                            Text(
                                text = "Report",
                                color = Color.White,
                                style = Typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        // Cancel Button
                        TextButton(
                            onClick = {
                                showBottomSheet.value = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Text(
                                text = "Cancel",
                                style = Typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = Black
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
                    value = content.value,
                    onValueChange = { content.value = it },
                    textStyle = Typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            postViewModel.createComment(
                                CreateCommentModel(
                                    content = content.value,
                                ),
                                publicInformationId
                            )
                            content.value = ""
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
                                if (content.value.isEmpty()) {
                                    Text(
                                        text = "Say something",
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
                        postViewModel.createComment(
                            CreateCommentModel(
                                content = content.value,
                            ),
                            publicInformationId
                        )
                        content.value = ""
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