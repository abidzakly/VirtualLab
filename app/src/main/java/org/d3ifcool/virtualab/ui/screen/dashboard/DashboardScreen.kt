package org.d3ifcool.virtualab.ui.screen.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.model.Categories
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.ui.theme.VirtualLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
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
                colors = topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            BottomNav(Screen.Dashboard.route, navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val viewModel: DashboardViewModel = viewModel()
    val data = viewModel.dashboardCategories
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 33.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.dashboard_headline),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = Poppins,
                modifier = Modifier.padding(bottom = 12.dp)
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
                        modifier = Modifier.fillMaxWidth(),
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
                text = stringResource(R.string.kategori_title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = Poppins,
                modifier = Modifier.fillMaxWidth()
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxHeight(),
                verticalItemSpacing = 27.dp,
                horizontalArrangement = Arrangement.spacedBy(43.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    GridItem(categories = it) {
                        navController.navigate(it.route)
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(categories: Categories, onClick: () -> Unit) {
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
                painter = painterResource(categories.image),
                contentDescription = "Gambar Molekul"
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            Text(
                text = stringResource(categories.title),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 16.dp))

    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DashboardScreenPrev() {
    VirtualLabTheme {
        DashboardScreen(navController = rememberNavController())
    }
}