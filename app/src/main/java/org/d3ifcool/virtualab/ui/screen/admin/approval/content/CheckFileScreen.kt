package org.d3ifcool.virtualab.ui.screen.admin.approval.content

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.AdminEmptyState
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CheckFileScreen(navController: NavHostController, viewModel: CheckFileViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        ),
                        tint = Color.Black
                    )
                }
            },
            title = { LargeText(text = stringResource(id = R.string.category_check_file)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.Black
            ),
        )
    }, bottomBar = {
        BottomNav(navController = navController)
    }, containerColor = Color.White
    ) {
        Box(modifier = Modifier
            .pullRefresh(refreshState)
            .padding(it)) {
            ScreenContent(modifier = Modifier, navController, viewModel)
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
    viewModel: CheckFileViewModel
) {
    val data by viewModel.postData.collectAsState()
    val status by viewModel.apiStatus.collectAsState()

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            LoadingState()
        }

        ApiStatus.SUCCESS -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                RegularText(
                    text = "Berkas yang perlu diperiksa: ",
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    items(data) {
                        FileList(username = it.authorUserName, postType = it.postType) {
                            navController.navigate(Screen.FileInfo.withId(it.postId, it.postType))
                        }
                    }
                }
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                AdminEmptyState(text = "Belum ada berkas yang perlu diperiksa") {
                    viewModel.getPendingPosts()
                }
            }
        }
    }

}

@Composable
private fun FileList(username: String, postType: String, onClick: () -> Unit) {
    Card(modifier = Modifier
//                .clickable { onClick() }
        .fillMaxWidth()
        .padding(8.dp)
        .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
        , onClick = { onClick() }, colors = cardColors(LightBlue)) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_account_circle),
                    contentDescription = "Foto Profil User",
                    modifier = Modifier.padding(end = 4.dp)
                )
                Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    RegularText(
                        text = username,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    RegularText(
                        text = postType,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.arrow_circle),
                contentDescription = "Detail Button",
                tint = Color.Black
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

}

@Preview
@Composable
private fun Prev() {
//    CheckFileScreen(navController = rememberNavController())
}