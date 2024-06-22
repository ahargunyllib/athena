package com.ahargunyllib.athena.features.presentation.screen.auth.splashScreen

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLightActive
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    data object First : OnBoardingPage(
        image = R.drawable.splash_1,
        title = "Walk Without Fear",
        description = "Reduce risks and enjoy a safer walking experience!"
    )

    data object Second : OnBoardingPage(
        image = R.drawable.splash_2,
        title = "Stay Connected",
        description = "Stay connected with your loved ones and feel secure wherever you go!"
    )

    data object Third : OnBoardingPage(
        image = R.drawable.splash_3,
        title = "Eyes Are Everywhere",
        description = "Stay vigilant with community-powered safety!"
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SplashScreen1(
    authController: NavController = rememberNavController()
) {
    val onBoardingPages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState {
        3
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                state = pagerState,
            ) { index ->
                val page = onBoardingPages[index]
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.height(500.dp)
                ) {
                    Image(
                        painter = painterResource(id = page.image),
                        contentDescription = "Onboarding Image",
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        text = page.title,
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Main
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        text = page.description,
                        style = Typography.labelLarge,
                        color = Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(250.dp)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Main else MainLightActive
                    val size = if (pagerState.currentPage == iteration) 12.dp else 8.dp
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(size)
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            if (pagerState.currentPage == onBoardingPages.size - 1) {
                Button(
                    onClick = {
                        val permissions = arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        )

                        // Request permissions
                        ActivityCompat.requestPermissions(context as Activity, permissions, 0)

                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
                            authController.navigate(
                                AuthNavObj.LoginNavObj.route
                            )
                        } else {
                            // Permission denied
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Main,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Continue",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                // set this button to be invisible
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Continue",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}