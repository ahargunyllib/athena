package com.ahargunyllib.athena.features.presentation.screen.profile.editProfile

import android.net.Uri
import android.text.format.DateFormat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ahargunyllib.athena.features.domain.model.UpdateModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Border
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.widget.DateDialog
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileViewModel
import java.time.Instant
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    parentController: NavController = rememberNavController()
) {
    val editProfileViewModel: EditProfileViewModel = hiltViewModel()
    val editProfileState = editProfileViewModel.editProfileState.collectAsState()

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userState = profileViewModel.userState.collectAsState()

    val fullName = remember { mutableStateOf(userState.value.data?.fullName) }
    val username = remember { mutableStateOf(userState.value.data?.username) }
    val dateOfBirth = rememberDatePickerState()
    val millisToLocalDate = dateOfBirth.selectedDateMillis?.let {
        DateFormat.format("dd/MM/yyyy", it)
    }
    val phoneNumber = remember { mutableStateOf(userState.value.data?.phoneNumber) }


    val showDateDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> imageUri.value = uri }
    )

    LaunchedEffect(editProfileState.value.data) {
        if (editProfileState.value.data != null) {
            parentController.navigate(ParentNavObj.ProfileNavObj.route)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MainLight),
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(
                        onClick = { parentController.navigate(ParentNavObj.ProfileNavObj.route) },
                        content = {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Black
                            )
                        })
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MainLight),
            )
        }
    ) { paddingValues ->
        if (userState.value.isLoading) {
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
                    .padding(horizontal = 20.dp)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = imageUri.value ?: userState.value.data?.imageUrl ?: "",
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(92.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Button(
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Main
                    )
                ) {
                    Text(text = "Change Profile", style = Typography.labelSmall)
                }
                Spacer(modifier = Modifier.size(48.dp))
                Text(
                    text = "Full Name",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = fullName.value ?: userState.value.data?.fullName ?: "",
                    onValueChange = { fullName.value = it },
                    textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = (12.5).dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Username",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = username.value ?: userState.value.data?.username ?: "",
                    onValueChange = { username.value = it },
                    textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = (12.5).dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Birth of Date",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp)
                ) {
                    Text(
                        text = millisToLocalDate?.toString()
                            ?: userState.value.data?.dateOfBirth?.let { dateString ->
                                val date = Date.from(Instant.parse(dateString))
                                DateFormat.format("dd/MM/yyyy", date)
                            }.toString(),
                        color = Typography.bodyMedium.color,
                        style = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.clickable {
                            showDateDialog.value = true
                        }
                    )
                    IconButton(
                        onClick = {
                            showDateDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = "Choose Date",
                            tint = Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Phone Number",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = phoneNumber.value ?: userState.value.data?.phoneNumber ?: "",
                    onValueChange = { phoneNumber.value = it },
                    textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = (12.5).dp),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        editProfileViewModel.updateUser(context, UpdateModel(
                            fullName = fullName.value ?: userState.value.data?.fullName ?: "",
                            username = username.value ?: userState.value.data?.username ?: "",
                            phoneNumber = phoneNumber.value ?: userState.value.data?.phoneNumber
                            ?: "",
                            dateOfBirth = dateOfBirth.selectedDateMillis?.let {
                                Date(it).toInstant().toString()
                            } ?: userState.value.data?.dateOfBirth ?: "",
                            imageUri = imageUri.value ?: Uri.EMPTY
                        ))
                    },
                ) {
                    Text(
                        text = "Proceed",
                        color = Color.White,
                        style = Typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (showDateDialog.value) {
                    DateDialog(showDateDialog, dateOfBirth)
                }
            }

        }
    }
}
