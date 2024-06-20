package com.ahargunyllib.athena.features.presentation.screen.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresPermission
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.outlined.GpsNotFixed
import androidx.compose.material.icons.outlined.LocalPolice
import androidx.compose.material.icons.outlined.North
import androidx.compose.material.icons.outlined.Sos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.domain.model.LocationModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Danger
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileViewModel
import com.ahargunyllib.athena.features.presentation.widget.PermissionBox
import com.ahargunyllib.athena.features.utils.Constants.UPDATE_LOCATION_SECOND
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@SuppressLint("MissingPermission")
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
    val sosSendState = mapViewModel.sosSendState.collectAsState()
    val publicInformationState = mapViewModel.publicInformationState.collectAsState()

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userState = profileViewModel.userState.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(
            context as Activity
        )

    val currentLocation = remember {
        mutableStateOf(
            LocationModel(
                latitude = 0f,
                longitude = 0f
            )
        )
    }

    fusedLocationClient.lastLocation.addOnSuccessListener {
        it?.let {
            currentLocation.value = LocationModel(
                latitude = it.latitude.toFloat(),
                longitude = it.longitude.toFloat()
            )
        }
    }

    val cameraPositionState = mutableStateOf(
        CameraPositionState(
            position = CameraPosition(
                LatLng(
                    currentLocation.value.latitude.toDouble(),
                    currentLocation.value.longitude.toDouble()
                ),
                16f,
                0f,
                0f
            )
        )
    )

    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    LaunchedEffect(Unit) {
        mapViewModel.getFriendsLocation(context)
        mapViewModel.getPublicInformation(context)
    }

    if (sosSendState.value.data != null) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = "SOS sent. Please go to the safest area",
                duration = SnackbarDuration.Long,
                withDismissAction = true,
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box {
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
                cameraPositionState = cameraPositionState.value,
                googleMapOptionsFactory = {
                    GoogleMapOptions()
                },
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = false,
                    zoomControlsEnabled = false,
                ),
                contentPadding = paddingValues
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
                                    .padding(8.dp),
                                fontSize = 8.sp,
                            )
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .border(1.dp, Main, CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.dummy_avatar),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }
                }

                publicInformationState.value.data?.forEach { publicInformation ->
                    Circle(
                        center = LatLng(
                            publicInformation.latitude.toDouble(),
                            publicInformation.longitude.toDouble()
                        ),
                        radius = 100.0,
                        fillColor = Danger.copy(alpha = 0.2f),
                        strokeColor = Color.Transparent,
                    )

                    MarkerComposable(
                        anchor = Offset(0.5f, 0.75f),
                        state = rememberMarkerState(
                            position = LatLng(
                                publicInformation.latitude.toDouble(),
                                publicInformation.longitude.toDouble()
                            )
                        ),
                        onClick = { marker ->
                            parentController.navigate("${ParentNavObj.PostNavObj.route}/${publicInformation.publicInformationId}")
                            false
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .widthIn(max = 128.dp)
                                    .background(
                                        Color.White, RoundedCornerShape(16.dp)
                                    )
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Campaign,
                                    contentDescription = "Public Information",
                                    tint = Main,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = publicInformation.post.content,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    fontSize = 10.sp,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .border(1.dp, Main, CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.dummy_avatar),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    AsyncImage(
                        model = userState.value.data?.imageUrl,
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable {
                                parentController.navigate(ParentNavObj.ProfileNavObj.route)
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
                            cameraPositionState.value =
                                CameraPositionState(
                                    position = CameraPosition(
                                        LatLng(
                                            cameraPositionState.value.position?.target?.latitude?.toDouble()
                                                ?: 0.0,
                                            cameraPositionState.value.position?.target?.longitude?.toDouble()
                                                ?: 0.0
                                        ),
                                        cameraPositionState.value.position.zoom,
                                        0f,
                                        0f
                                    )
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
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:112")
                            context.startActivity(intent)
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
                            mapViewModel.sendSOS(context)
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
                                Toast.makeText(
                                    context,
                                    "This feature is currently disabled",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                                parentController.navigate("${ParentNavObj.PublicInformationNavObj.route}?latitude=${currentLocation.value.latitude}&longitude=${currentLocation.value.longitude}")
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
                            // Use current location
                            fusedLocationClient.lastLocation.addOnSuccessListener {
                                it?.let {
                                    currentLocation.value = LocationModel(
                                        latitude = it.latitude.toFloat(),
                                        longitude = it.longitude.toFloat()
                                    )

                                    cameraPositionState.value = CameraPositionState(
                                        position = CameraPosition(
                                            LatLng(
                                                it.latitude,
                                                it.longitude
                                            ),
                                            16f,
                                            0f,
                                            0f
                                        )
                                    )
                                }
                            }

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
                            color = Gray,
                            thickness = 4.dp
                        )
                    }

                    Box {
                        Column {
                            Text(
                                "Status",
                                style = Typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Nothing to show here ",
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(28.dp))
                        }

                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                        ) { snackbarData ->
                            Snackbar(
                                snackbarData = snackbarData,
                                containerColor = Danger,
                                contentColor = Color.White,
                            )

                        }
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