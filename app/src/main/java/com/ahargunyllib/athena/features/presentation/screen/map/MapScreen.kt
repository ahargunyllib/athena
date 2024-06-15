package com.ahargunyllib.athena.features.presentation.screen.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.provider.Settings
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.outlined.GpsNotFixed
import androidx.compose.material.icons.outlined.LocalPolice
import androidx.compose.material.icons.outlined.North
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Sos
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.domain.model.LocationModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Danger
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.BottomNavObj
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileViewModel
import com.ahargunyllib.athena.features.presentation.widget.PermissionBox
import com.ahargunyllib.athena.features.utils.Constants.UPDATE_LOCATION_SECOND
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun MapScreen(
    parentController: NavController = rememberNavController(),
    bottomNavController: NavController = rememberNavController(),
    onChangeScreen: (Int) -> Unit
) {
    val mapViewModel: MapViewModel = hiltViewModel()
    val friendsLocationState = mapViewModel.friendsLocationState.collectAsState()

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userState = profileViewModel.userState.collectAsState()

    val currentLocation = remember { mutableStateOf<LocationModel?>(null) }
    val cameraPositionState = rememberCameraPositionState()

    val context = LocalContext.current

    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    LaunchedEffect(Unit) {
        mapViewModel.getFriendsLocation(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { it ->
        Box(
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                properties = MapProperties(
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = true,
                    isTrafficEnabled = false,
                    isBuildingEnabled = false,
                    isIndoorEnabled = false,
                ),
                cameraPositionState = cameraPositionState,
                googleMapOptionsFactory = {
                    GoogleMapOptions()
                },
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = false,
                    zoomControlsEnabled = false,
                ),
                contentPadding = it
            ) {
                friendsLocationState.value.data?.forEach { friendLocation ->
                    MarkerComposable(
                        state = rememberMarkerState(
                            position = LatLng(
                                friendLocation.latitude.toDouble(),
                                friendLocation.longitude.toDouble()
                            )
                        )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = friendLocation.username,
                                modifier = Modifier
                                    .background(
                                        Color.White, RoundedCornerShape(16.dp)
                                    )
                                    .padding(8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .border(1.dp, Main, CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dummy_avatar),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }

//
//                    Circle(
//                        center = LatLng(
//                            friendLocation.latitude.toDouble(),
//                            friendLocation.longitude.toDouble()
//                        ), radius = 1000.0,
//                        fillColor = Danger.copy(alpha = 0.2f),
//                        strokeColor = Color.Transparent,
//                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 92.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.dummy_avatar),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    bottomNavController.navigate(BottomNavObj.ProfileNavObj.route)
                                })
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(
                            text = "Home",
                            color = Black,
                            style = Typography.titleLarge,
                            fontWeight = FontWeight.Bold,

                            )
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(
                            onClick = {
                                // change camera position state to north
                                cameraPositionState.position =
                                    CameraPosition(
                                        cameraPositionState.position.target,
                                        cameraPositionState.position.zoom,
                                        0f,
                                        0f
                                    )

                            }, colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White, contentColor = Main
                            ), modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.North,
                                contentDescription = "North",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        IconButton(
                            onClick = {

                            }, colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Yellow.copy(red = 0.5f)
                            ), modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocalPolice,
                                contentDescription = "Police",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        IconButton(
                            onClick = {

                            }, colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White, contentColor = Color.Red
                            ), modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Sos,
                                contentDescription = "Sos",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize()
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                onClick = {

                                }, colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Main, contentColor = Color.White
                                ), modifier = Modifier.size(56.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AttachFile,
                                    contentDescription = "Memory",
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            IconButton(
                                onClick = {

                                }, colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Main, contentColor = Color.White
                                ), modifier = Modifier.size(56.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Campaign,
                                    contentDescription = "Public Information",
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxSize()
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                // move camera position state to current location
                                cameraPositionState.isMoving
                                cameraPositionState.position =
                                    CameraPosition(
                                        LatLng(
                                            currentLocation.value?.latitude?.toDouble() ?: 0.0,
                                            currentLocation.value?.longitude?.toDouble() ?: 0.0
                                        ),

                                        15f,
                                        cameraPositionState.position.bearing,
                                        cameraPositionState.position.tilt
                                    )

                            }, colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White, contentColor = Main
                            ), modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.GpsNotFixed,
                                contentDescription = "GPS",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.width(80.dp),
                                color = Main,
                                thickness = 4.dp
                            )
                        }

                        Column {
                            Text(
                                "Status",
                                style = Typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.North,
                                    contentDescription = "North",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    "North",
                                    style = Typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.North,
                                    contentDescription = "North",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    "North",
                                    style = Typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(28.dp))
                        }
                    }

                }
            }


            PermissionBox(
                permissions = permissions,
                requiredPermissions = listOf(permissions.first()),
            ) {
                val usePreciseLocation = it.contains(Manifest.permission.ACCESS_FINE_LOCATION)

                // The location request that defines the location updates
                var locationRequest by remember {
                    mutableStateOf<LocationRequest?>(null)
                }

                // Only register the location updates effect when we have a request
                if (locationRequest != null) {
                    LocationUpdatesEffect(locationRequest!!) { result ->
                        val lastLocation = LocationModel(
                            latitude = result.lastLocation?.latitude?.toFloat() ?: 0f,
                            longitude = result.lastLocation?.longitude?.toFloat() ?: 0f,
                        )

                        // Update to the latest location
                        mapViewModel.updateLocation(context, lastLocation)

                        // Get friends location
                        mapViewModel.getFriendsLocation(context)

                        // Update the current location
                        currentLocation.value = lastLocation
                    }
                }

                locationRequest = if (userState.value.data?.isSharingLocation == true) {
                    // Define the accuracy based on your needs and granted permissions
                    val priority = if (usePreciseLocation) {
                        Priority.PRIORITY_HIGH_ACCURACY
                    } else {
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY
                    }
                    LocationRequest.Builder(
                        priority,
                        TimeUnit.SECONDS.toMillis(UPDATE_LOCATION_SECOND)
                    ).build()
                } else {
                    null
                }
            }
        }
    }
}


/**
 * An effect that request location updates based on the provided request and ensures that the
 * updates are added and removed whenever the composable enters or exists the composition.
 */
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun LocationUpdatesEffect(
    locationRequest: LocationRequest,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onUpdate: (result: LocationResult) -> Unit,
) {
    val context = LocalContext.current
    val currentOnUpdate by rememberUpdatedState(newValue = onUpdate)

    // Whenever on of these parameters changes, dispose and restart the effect.
    DisposableEffect(locationRequest, lifecycleOwner) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                currentOnUpdate(result)
            }
        }
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                locationClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper(),
                )
            } else if (event == Lifecycle.Event.ON_STOP) {
                locationClient.removeLocationUpdates(locationCallback)
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            locationClient.removeLocationUpdates(locationCallback)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}