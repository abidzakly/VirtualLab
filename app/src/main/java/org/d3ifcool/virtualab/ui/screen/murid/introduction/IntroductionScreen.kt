package org.d3ifcool.virtualab.ui.screen.murid.introduction

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.DarkBlueText
import org.d3ifcool.virtualab.ui.theme.LightBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IntroductionScreen(navController: NavHostController, viewModel: IntroductionViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    Scaffold(topBar = {
        TopNav(title = R.string.introduction_title, navController = navController)
    },
        bottomBar = {
            BottomNav(navController = navController)
        }) {
        Box(
            modifier = Modifier
                .pullRefresh(refreshState)
                .padding(bottom = it.calculateBottomPadding())
        ) {
            ScreenContent(
                modifier = Modifier,
                navController = navController,
                viewModel
            )
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = Color.White,
                backgroundColor = DarkBlueDarker
            )
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: IntroductionViewModel
) {
    val context = LocalContext.current
    val introData by viewModel.introData.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                LightBlue
            )
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(),
            colors = buttonColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(ApiService.getIntroductionThumbnail())
                        .crossfade(true)
                        .build(),
                    contentDescription = "video thumbnail",
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.broken_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.play_button),
                    contentDescription = "Play Button",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (status) {
                ApiStatus.IDLE -> null
                ApiStatus.LOADING -> {
                    LoadingState()
                }

                ApiStatus.FAILED -> {
                    MuridEmptyState(text = "Gagal memuat data.") {
                        viewModel.getIntroData()
                    }
                }

                ApiStatus.SUCCESS -> {
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        MediumLargeText(
                            text = introData!!.title,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 30.dp,
                                    top = 0.dp,
                                    end = 30.dp,
                                    bottom = 24.dp
                                ),
                            color = DarkBlueText
                        )
                        Column(modifier = Modifier.padding(horizontal = 50.dp)) {
                            SmallText(
                                text = introData!!.description,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun IntroScreenPrev() {
//    IntroductionScreen(navController = rememberNavController())
}