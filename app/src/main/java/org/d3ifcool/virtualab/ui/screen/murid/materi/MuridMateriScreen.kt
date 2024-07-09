package org.d3ifcool.virtualab.ui.screen.murid.materi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.ApprovedMateri
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ExtraLargeText
import org.d3ifcool.virtualab.ui.component.GradientPage
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.GrayTitle
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.utils.errorTextCheck


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MuridMateriScreen(navController: NavHostController, viewModel: MuridMateriViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    Scaffold(
        topBar = {
            TopNav(title = R.string.materi_title, navController = navController)
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
    viewModel: MuridMateriViewModel
) {
    val materis by viewModel.materis.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    GradientPage(
        modifier = modifier,
        isCenter = false,
        image = R.drawable.materi_header
    ) {
        when (status) {
            ApiStatus.IDLE -> null
            ApiStatus.LOADING -> {
                LoadingState()
            }

            ApiStatus.SUCCESS -> {
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn {
                    item {
                        MediumLargeText(
                            text = "Materi Ajar",
                            modifier = Modifier.padding(bottom = 16.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    itemsIndexed(materis) { index, it ->
                        CardList(index = index, materi = it) {
                            navController.navigate(Screen.MuridDetailMateri.withId(it.materialId))
                        }
                    }
                }
            }

            ApiStatus.FAILED -> {
                MuridEmptyState(text = errorTextCheck(errorMessage, stringResource(id = R.string.empty_materi))) {
                    viewModel.getApprovedMateris()
                    viewModel.clearMessage()
                }
            }
        }
    }
}

@Composable
private fun CardList(index: Int, materi: ApprovedMateri, onClick: () -> Unit) {
    Row(
        Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(LightBlue)
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ExtraLargeText(text = "${index + 1}", color = GrayTitle)
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            MediumLargeText(text = materi.title, fontWeight = FontWeight.SemiBold)
            RegularText(
                text = materi.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.width(280.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
private fun MateriScreenPrev() {
//    MuridMateriScreen(navController = rememberNavController())
}