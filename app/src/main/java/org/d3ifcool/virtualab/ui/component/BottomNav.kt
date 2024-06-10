package org.d3ifcool.virtualab.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.DarkBlueText
import org.d3ifcool.virtualab.ui.theme.GrayIco
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.utils.UserDataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(
    currentRoute: String,
    navController: NavHostController,
    isClicked: Boolean = false,
    onClick: () -> Unit = {}
) {
    val dataStore = UserDataStore(LocalContext.current)
    val userType by dataStore.userTypeFlow.collectAsState(-1)
    Log.d("Bottom Nav", "userId: $userType")
    BottomAppBar(
        modifier = Modifier
            .shadow(elevation = 20.dp, shape = RectangleShape)
            .fillMaxWidth()
            .height(68.dp)
            .zIndex(99f),
        containerColor = Color.White
    ) {
        when (userType) {
            0 -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomNavButton(
                        icon = R.drawable.baseline_home_filled_28,
                        title = R.string.bottom_app_beranda,
                        isSelected = currentRoute == Screen.MuridDashboard.route
                    ) {
                        navController.navigate(Screen.MuridDashboard.route) {
                            popUpTo(Screen.Login.route)
                        }
                    }
                    BottomNavButton(
                        icon = R.drawable.baseline_nilai_28,
                        title = R.string.bottom_app_nilai,
                        isSelected = currentRoute == Screen.Nilai.route
                    ) {
                        navController.navigate(Screen.Nilai.route) {
                            popUpTo(Screen.MuridDashboard.route)
                        }
                    }
                    BottomNavButton(
                        icon = R.drawable.baseline_account_circle,
                        title = R.string.bottom_app_profile,
                        isSelected = currentRoute == Screen.Profile.route
                    ) {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(Screen.MuridDashboard.route)
                        }
                    }
                }
            }

            1 -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomNavButton(
                        icon = R.drawable.baseline_home_filled_28,
                        title = R.string.bottom_app_beranda,
                        isSelected = if (!isClicked) currentRoute == Screen.GuruDashboard.route else false
                    ) {
                        navController.navigate(Screen.GuruDashboard.route) {
                            popUpTo(Screen.Login.route)
                        }
                    }
                    if (currentRoute == Screen.GuruLatihan.route || currentRoute == Screen.GuruMateri.route) {
                        BottomNavButton(
                            icon = R.drawable.baseline_history_24,
                            title = R.string.bottom_app_riwayat,
                            isSelected = true,
                            isSheet = isClicked
                        ) {
                            navController.navigate(currentRoute) {
                                popUpTo(Screen.GuruDashboard.route)
                            }
                        }
                    } else {
                        BottomNavButton(
                            icon = R.drawable.baseline_history_24,
                            title = R.string.bottom_app_riwayat,
                            isSelected = isClicked,
                            isSheet = isClicked
                        ) {
                            onClick()
                        }
                    }
                    BottomNavButton(
                        icon = R.drawable.baseline_account_circle,
                        title = R.string.bottom_app_profile,
                        isSelected = currentRoute == Screen.Profile.route
                    ) {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(Screen.GuruDashboard.route)
                        }
                    }
                }
            }

            else -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomNavButton(
                        icon = R.drawable.baseline_home_filled_28,
                        title = R.string.bottom_app_beranda,
                        isSelected = if (!isClicked) currentRoute == Screen.AdminDashboard.route else false
                    ) {
                        navController.navigate(Screen.AdminDashboard.route) {
                            popUpTo(Screen.Login.route)
                        }
                    }
                    BottomNavButton(
                        icon = R.drawable.icon_logout,
                        title = R.string.logout_button,
                        isSelected = currentRoute == Screen.Login.route
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavButton(
    icon: Int,
    title: Int,
    isSelected: Boolean,
    isSheet: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .width(108.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = GrayIco
        ),
        enabled = if (!isSheet) !isSelected else isSelected
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "Nilai",
                tint = if (isSelected) DarkBlueDarker else GrayIco,
                modifier = Modifier.width(30.dp)
            )
            Text(
                text = stringResource(title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontFamily = Poppins,
                color = if (isSelected) DarkBlueText else GrayIco
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavPrev() {
    BottomNav(
        currentRoute = Screen.MuridDashboard.route,
        navController = rememberNavController()
    )
}