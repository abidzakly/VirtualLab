package org.d3ifcool.virtualab.ui.screen.admin.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNavDashboard
import org.d3ifcool.virtualab.ui.theme.WhiteLightBlue

@Composable
fun AdminDashboardScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNav(Screen.AdminDashboard.route, navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        TopNavDashboard(name = "Admin", navController = navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 33.dp)
        ) {
            Column(modifier = Modifier.padding(top = 40.dp)) {
                Image(painter = painterResource(id = R.drawable.vektor_dashboard_admin), contentDescription = "Gambar Dashboard Admin" )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                RegularText(
                    text = stringResource(R.string.dashboard_category_title),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        GridItem(
                            title = R.string.category_check_account,
                            image = R.drawable.check_role_illustration,
                        ) {
                            navController.navigate(Screen.CheckUser.route)
                        }
                        GridItem(
                            title = R.string.category_check_file,
                            image = R.drawable.berkas_illustration
                        ) {
                            navController.navigate(Screen.CheckFile.route)
                        }
                    }
                    Spacer(modifier = Modifier.height(27.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        GridItem(
                            title = R.string.category_kelola_materi,
                            image = R.drawable.pengenalan_illustration
                        ) {
                            navController.navigate(Screen.ManageIntroContent.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(title: Int, image: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(153.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .background(WhiteLightBlue)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(image),
                contentDescription = "Gambar Molekul"
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            SmallText(
                text = stringResource(title),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Prev() {
    AdminDashboardScreen(navController = rememberNavController())
}