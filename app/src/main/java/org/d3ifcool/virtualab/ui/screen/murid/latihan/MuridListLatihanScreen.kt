package org.d3ifcool.virtualab.ui.screen.murid.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
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
import org.d3ifcool.virtualab.utils.errorTextCheck
import org.d3ifcool.virtualab.utils.isInternetAvailable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MuridListLatihanScreen(navController: NavHostController, viewModel: MuridListLatihanViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    Scaffold(
        topBar = {
            TopNav(title = R.string.latihan, navController = navController)
        },
        bottomBar = {
            BottomNav(navController = navController)
        },
        containerColor = Color.White
    ) {
        Box(modifier = Modifier
            .pullRefresh(refreshState)
            .padding(bottom = it.calculateBottomPadding())) {
            ScreenContent(
                modifier = Modifier,
                navController,
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
    viewModel: MuridListLatihanViewModel
) {
    var isEmpty by remember { mutableStateOf(false) }
    val data by viewModel.approvedLatihan.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    val errorMesssage by viewModel.errorMessage.collectAsState()

    GradientPage(
        modifier,
        image = R.drawable.latihan_header,
    ) {
        if (!isEmpty) {
            Spacer(modifier = Modifier.height(24.dp))

            when (status) {
                ApiStatus.IDLE -> null
                ApiStatus.LOADING -> {
                    LoadingState()
                }

                ApiStatus.SUCCESS -> {
                    Spacer(modifier = Modifier.height(6.dp))
                    RegularText(
                        text = stringResource(R.string.latihan_header),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    if (data.isNotEmpty()) {
                        LazyColumn {
                            items(data) {
                                CardList(it.title, it.difficulty) {
                                    navController.navigate(Screen.MuridDetailLatihan.withId(it.exerciseId, it.title))
                                }
                            }
                        }
                    } else {
                        MuridEmptyState(text = "Anda telah mengerjakan semua latihan.") {
                            viewModel.getApprovedLatihan()
                        }
                    }
                }

                ApiStatus.FAILED -> {
                    MuridEmptyState(text = errorTextCheck(errorMesssage, stringResource(id = R.string.empty_latihan))) {
                        viewModel.getApprovedLatihan()
                        viewModel.clearMessage()
                    }
                }
            }
        }
    }
}

@Composable
private fun CardList(title: String, difficulty: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        elevation = cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RegularText(text = title)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SmallText(text = "Tingkat Kesulitan")
                SmallText(text = difficulty, fontWeight = FontWeight.Bold)
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun Prev() {
//    MuridListLatihanScreen(navController = rememberNavController())
}