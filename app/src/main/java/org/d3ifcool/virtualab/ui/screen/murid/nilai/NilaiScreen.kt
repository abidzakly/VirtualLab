package org.d3ifcool.virtualab.ui.screen.murid.nilai

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.Nilai
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.GradientPage
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.GreenStatus
import org.d3ifcool.virtualab.ui.theme.RedStatus
import org.d3ifcool.virtualab.ui.theme.YellowStatus
import org.d3ifcool.virtualab.utils.errorTextCheck

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NilaiScreen(navController: NavHostController, viewModel: NilaiViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    Scaffold(topBar = {
        TopNav(title = R.string.nilai_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.Nilai.route, navController)
    }) {
        Box(
            modifier = Modifier
                .pullRefresh(refreshState)
                .padding(bottom = it.calculateBottomPadding())
        ) {
            ScreenContent(
                modifier = Modifier,
                navController,
                viewModel = viewModel
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
    viewModel: NilaiViewModel
) {
    val myNilai by viewModel.nilaiData.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()


    GradientPage(modifier, image = R.drawable.nilai_illustration) {
        when (status) {
            ApiStatus.IDLE -> null
            ApiStatus.LOADING -> {
                LoadingState()
            }

            ApiStatus.SUCCESS -> {
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn {
                    item {
                            RegularText(text = "Nilai dari latihan yang telah kamu kerjakan:")
                            Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(myNilai) {
                        CardList(nilai = it) {
                            navController.navigate(Screen.CekJawaban.withId(it.resultId))
                        }
                    }
                }
            }

            ApiStatus.FAILED -> {
                MuridEmptyState(text = errorTextCheck(errorMessage, stringResource(id = R.string.empty_nilai))) {
                    viewModel.getMyNilai()
                    viewModel.clearMessage()
                }
            }
        }
    }
}

@Composable
private fun CardList(nilai: Nilai, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(vertical = 24.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            RegularText(
                text = nilai.title,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                SmallText(text = stringResource(id = R.string.tingkat_kesulitan))
                SmallText(text = " ${nilai.difficulty}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                SmallText(text = stringResource(id = R.string.nilai))
                SmallText(
                    text = " ${nilai.score}",
                    fontWeight = FontWeight.SemiBold,
                    color = when (nilai.score) {
                        in 85.00..100.00 -> {
                            GreenStatus
                        }

                        in 70.00..84.00 -> {
                            YellowStatus
                        }

                        else -> {
                            RedStatus
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
        Image(
            painter = painterResource(
                id = when (nilai.score) {
                    in 85.00..100.00 -> {
                        R.drawable.gold_award
                    }

                    in 70.00..84.00 -> {
                        R.drawable.silver_award
                    }

                    else -> {
                        R.drawable.bronze_award
                    }
                }

            ),
            contentDescription = "Medals Icon",
            modifier = Modifier.size(57.dp, 81.2.dp),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun NilaiScrenPrev() {
//    NilaiScreen(rememberNavController())
}