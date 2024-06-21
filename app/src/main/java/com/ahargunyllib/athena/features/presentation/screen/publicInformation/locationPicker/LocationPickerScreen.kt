package com.ahargunyllib.athena.features.presentation.screen.publicInformation.locationPicker

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.GpsNotFixed
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun LocationPickerScreen(
    parentController: NavController,
    latitude: Float,
    longitude: Float
) {
    val cameraPositionState = remember {
        mutableStateOf(
            CameraPositionState(
                position = CameraPosition(
                    LatLng(latitude.toDouble(), longitude.toDouble()),
                    16f,
                    0f,
                    0f
                )
            )
        )
    }

    val addressToFind = remember { mutableStateOf("") }
    val street = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }

    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(
            context as Activity
        )

    val isPositionChanging = remember { mutableStateOf(false) }
    val cameraPositionFlow = remember { MutableStateFlow(cameraPositionState.value.position) }

    // Update the camera position state flow whenever the camera position changes
    LaunchedEffect(cameraPositionState.value) {
        snapshotFlow { cameraPositionState.value.position }
            .collect { position ->
                isPositionChanging.value = true
                cameraPositionFlow.value = position
            }
    }

    LaunchedEffect(cameraPositionFlow) {

        cameraPositionFlow
            .debounce(5000)
            .collectLatest { position ->
                val geocoder = Geocoder(context)
                geocoder.getFromLocation(
                    position.target.latitude,
                    position.target.longitude,
                    1
                ) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val address: Address = addresses[0]

                        val thoroughfare = address.thoroughfare
                        val subThoroughfare = address.subThoroughfare

                        val locality = address.locality
                        val subLocality = address.subLocality
                        val adminArea = address.adminArea
                        val subAdminArea = address.subAdminArea
                        val countryName = address.countryName

                        street.value = listOf(
                            thoroughfare,
                            subThoroughfare
                        ).filter { !it.isNullOrEmpty() }.joinToString(", ")
                        city.value = listOf(
                            locality,
                            subLocality,
                            adminArea,
                            subAdminArea,
                            countryName
                        ).filter { !it.isNullOrEmpty() }.joinToString(", ")

                        isPositionChanging.value = false
                    }

                }
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
                        "Change Pinpoint",
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { parentController.navigate("${ParentNavObj.PublicInformationNavObj.route}?latitude=$latitude&longitude=$longitude") },
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
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MainLight)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
                ) {
                    Text(
                        if (isPositionChanging.value) "Loading..." else street.value,
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Text(
                        if (isPositionChanging.value) "Loading..." else city.value,
                        style = Typography.bodySmall,
                        color = Gray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            parentController.navigate("${ParentNavObj.PublicInformationNavObj.route}?latitude=${cameraPositionState.value.position.target.latitude}&longitude=${cameraPositionState.value.position.target.longitude}")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Main
                        )
                    ) {
                        Text(
                            "Choose Location",
                            style = Typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box {
            GoogleMap(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState.value,
                properties = MapProperties(
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = true,
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
                    zoomGesturesEnabled = true,
                    tiltGesturesEnabled = true,
                    rotationGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    scrollGesturesEnabledDuringRotateOrZoom = true,
                    compassEnabled = false,
                    mapToolbarEnabled = false,
                ),
            ) {
                Marker(
                    state = MarkerState(
                        position = cameraPositionState.value.position.target
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 32.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicTextField(
                    value = addressToFind.value,
                    onValueChange = { addressToFind.value = it },
                    textStyle = Typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            // Search
                            Log.i("ChatScreen", "username: ${addressToFind.value}")
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .shadow(16.dp, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    decorationBox = { textField ->
                        Row {
                            Icon(Icons.Outlined.Search, contentDescription = "Search", tint = Gray)
                            Spacer(modifier = Modifier.width(8.dp))

                            Box {
                                if (addressToFind.value.isEmpty()) {
                                    Text(
                                        text = "Search for location",
                                        style = Typography.bodyLarge,
                                        color = Gray
                                    )
                                }
                                textField()
                            }
                        }
                    }
                )

                Button(
                    onClick = {
                        // Use current location
                        fusedLocationClient.lastLocation.addOnSuccessListener {
                            it?.let {
                                cameraPositionState.value = CameraPositionState(
                                    position = CameraPosition(
                                        LatLng(it.latitude, it.longitude),
                                        16f,
                                        0f,
                                        0f
                                    )
                                )
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .shadow(4.dp, shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.GpsNotFixed,
                        contentDescription = "GPS",
                        tint = Main
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Use Current Location",
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}