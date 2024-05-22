package org.d3ifcool.virtualab.ui.screen.murid.dashboard

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.model.Categories
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuridDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(start = 21.dp, end = 21.dp, top = 8.dp, bottom = 8.dp),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.vlab_logo_mini),
                        contentDescription = "virtual lab logo"
                    )
                },
                title = {
                    Text(
                        text = "Halo,\nAmru Abid Zakly",
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 12.dp),
                        color = Color.Black
                    )
                },
                colors = topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_about_24),
                            contentDescription = stringResource(R.string.about_button),
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNav(Screen.MuridDashboard.route, navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
//    val viewModel: DashboardMuridViewModel = viewModel()
//    val data = viewModel.dashboardCategories
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 33.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.dashboard_headline),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = Poppins,
                modifier = Modifier.padding(bottom = 12.dp),
                color = Color.Black
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(onClick = { /*TODO*/ }, colors = cardColors(containerColor = LightBlue)) {
                    Image(
                        modifier = Modifier.padding(15.dp),
                        painter = painterResource(id = R.drawable.video_thumbnail),
                        contentDescription = "Video Thumbnail"
                    )
                    Text(
                        text = stringResource(R.string.dashboard_h2),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 28.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.dashboard_category_title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = Poppins,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                    GridItem(
                        title = R.string.cateogry_pengenalan,
                        image = R.drawable.pengenalan_illustration,
                    ) {
                        navController.navigate(Screen.Introduction.route)
                    }
                    GridItem(
                        title = R.string.cateogry_materi_belajar,
                        image = R.drawable.materi_illustration
                    ) {
                        navController.navigate(Screen.MuridMateri.route)
                    }
                }
                Spacer(modifier = Modifier.height(27.dp))
                Row(modifier = Modifier.fillMaxSize().padding(bottom = 32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    GridItem(
                        title = R.string.cateogry_latihan,
                        image = R.drawable.latihan_illustration
                    ) {
                        navController.navigate(Screen.Latihan.route)
                    }
                    GridItem(
                        title = R.string.cateogry_contoh_reaksi,
                        image = R.drawable.contoh_reaksi_illustration
                    ) {
                        navController.navigate(Screen.Reaksi.route)
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(categories: Categories? = null, title: Int, image: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(153.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .background(LightBlue)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(image),
                contentDescription = "Gambar Molekul"
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            Text(
                text = stringResource(title),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 16.dp))

    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DashboardScreenPrev() {
    MuridDashboardScreen(navController = rememberNavController())
}