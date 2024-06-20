package com.ahargunyllib.athena.features.presentation.screen.publicInformation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahargunyllib.athena.features.domain.model.CreatePublicInformationModel
import com.ahargunyllib.athena.features.domain.model.CredentialsModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Border
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileViewModel
import com.ahargunyllib.athena.features.presentation.widget.ErrorDialog
import com.ahargunyllib.athena.features.utils.getFileFromUri
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicInformationScreen(
    parentController: NavController,
    latitude: Float,
    longitude: Float,
) {
    val publicInformationViewModel: PublicInformationViewModel = hiltViewModel()
    val createPublicInformationState =
        publicInformationViewModel.publicInformationState.collectAsState()

    val context = LocalContext.current

    val content = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val file = remember { mutableStateOf<File?>(null) }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri.value = uri
            file.value = getFileFromUri(context, uri ?: Uri.EMPTY)
        }
    )

    LaunchedEffect(createPublicInformationState.value.data) {
        if (createPublicInformationState.value.data != null) {
            parentController.navigate(ParentNavObj.BottomNavObj.route)
        }
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
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { parentController.navigate(ParentNavObj.BottomNavObj.route) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainLight)
                .padding(horizontal = 20.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Tell us what happend?",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = content.value,
                onValueChange = { content.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Add Media",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = file.value?.name ?: "",
                onValueChange = { },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
                enabled = false,
                decorationBox = { textField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.AddPhotoAlternate,
                            contentDescription = "Add Media",
                            tint = Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Box {
                            if (imageUri.value == null) {
                                Text(
                                    text = "Add Media",
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
            Text(
                text = "Location",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = address.value,
                onValueChange = { address.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pinpoint",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = CameraPositionState(
                        position = CameraPosition(
                            LatLng(latitude.toDouble(), longitude.toDouble()),
                            16f,
                            0f,
                            0f
                        )
                    ),
                    properties = MapProperties(
                        mapType = MapType.NORMAL,
                        isMyLocationEnabled = false,
                        isTrafficEnabled = false,
                        isBuildingEnabled = false,
                        isIndoorEnabled = false,
                    ),
                    googleMapOptionsFactory = {
                        GoogleMapOptions()
                    },
                    uiSettings = MapUiSettings(
                        myLocationButtonEnabled = false,
                        zoomControlsEnabled = false,
                        zoomGesturesEnabled = false,
                        tiltGesturesEnabled = false,
                        rotationGesturesEnabled = false,
                        scrollGesturesEnabled = false,
                        scrollGesturesEnabledDuringRotateOrZoom = false,
                        compassEnabled = false,
                        mapToolbarEnabled = false,
                    ),
                ) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(latitude.toDouble(), longitude.toDouble())
                        ),
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            parentController.navigate("${ParentNavObj.LocationPickerNavObj.route}?latitude=$latitude&longitude=$longitude")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .shadow(4.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text("Change Pinpoint", style = Typography.labelSmall)
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
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
                    publicInformationViewModel.createPublicInformation(
                        context = context,
                        createPublicInformationModel = CreatePublicInformationModel(
                            content = content.value,
                            latitude = latitude,
                            longitude = longitude,
                            imageUri = imageUri.value ?: Uri.EMPTY
                        )
                    )
                },
            ) {
                Text(
                    text = "Upload",
                    color = Color.White,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }
}