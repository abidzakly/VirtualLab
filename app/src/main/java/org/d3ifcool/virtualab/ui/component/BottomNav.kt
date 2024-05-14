package org.d3ifcool.virtualab.ui.component

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.GrayIco

@Composable
fun BottomNav(currentRoute: String, navController: NavHostController, id: Long? = 0L) {
    if (id == 0L) {
        BottomAppBar(
            modifier = Modifier
                .height(83.dp)
                .shadow(elevation = 5.dp, shape = RectangleShape),
            containerColor = Color.White
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BottomNavButton(
                    icon = R.drawable.baseline_home_filled_28,
                    title = R.string.bottom_app_beranda,
                    isSelected = currentRoute == Screen.Dashboard.route
                ) {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route)
                    }
                }
                BottomNavButton(
                    icon = R.drawable.list_nilai_filled_25,
                    title = R.string.bottom_app_nilai,
                    isSelected = currentRoute == Screen.Nilai.route
                ) {
                    navController.navigate(Screen.Nilai.route) {
                        popUpTo(Screen.Dashboard.route)
                    }
                }
                BottomNavButton(
                    icon = R.drawable.baseline_account_circle,
                    title = R.string.bottom_app_profile,
                    isSelected = currentRoute == Screen.Profile.route
                ) {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Dashboard.route)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavButton(icon: Int, title: Int, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .width(108.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = GrayIco,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = GrayIco
        ),
        enabled = !isSelected
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "Nilai",
                tint = if (isSelected) DarkBlueDarker else GrayIco
            )
            Text(
                text = stringResource(title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavPrev() {
    BottomNav(currentRoute = Screen.Dashboard.route, navController = rememberNavController(), 0L)
}