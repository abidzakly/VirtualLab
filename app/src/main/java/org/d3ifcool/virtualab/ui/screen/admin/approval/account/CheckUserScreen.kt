package org.d3ifcool.virtualab.ui.screen.admin.approval.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.testTag
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
fun CheckUserScreen(navController: NavHostController, viewModel: CheckUsersViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.getAllPendingUser() }
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
            title = { LargeText(text = stringResource(id = R.string.category_check_account)) },
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
    viewModel: CheckUsersViewModel
) {
    val userList by viewModel.userList.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = if (userList.isNotEmpty()) Arrangement.Top else Arrangement.Center,
    ) {
        when (isLoading) {
            ApiStatus.LOADING -> {
                LoadingState()
            }

            ApiStatus.SUCCESS -> {
                RegularText(
                    text = stringResource(R.string.check_users_title),
                    modifier = Modifier.padding(start = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    var nomorUrut = 1
                    items(userList) {
                        AccountList(
                            modifier = Modifier.testTag("Akun user ke $nomorUrut"),
                            username = it!!.username,
                            number = it.nip ?: it.nisn ?: ""
                        ) {
                            navController.navigate(Screen.UsersInfo.withId(it.userId))
                        }
                        nomorUrut++
                        if (nomorUrut > userList.size) {
                            nomorUrut = 1
                        }
                    }
                }
            }

            ApiStatus.FAILED -> {
                AdminEmptyState(text = if (errorMsg != null) errorMsg!! else "Belum ada akun yang perlu diperiksa") {
                    viewModel.getAllPendingUser()
                }
            }

            ApiStatus.IDLE -> null
        }

    }
}

@Composable
private fun AccountList(
    modifier: Modifier = Modifier,
    username: String,
    number: String,
    onClick: () -> Unit
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
        .fillMaxWidth()
        , onClick = { onClick() }, colors = CardDefaults.cardColors(LightBlue)
    ) {
    Row(
        modifier = modifier.fillMaxWidth()
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
                contentDescription = "Foto Profil User"
            )
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                RegularText(
                    text = username,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                RegularText(
                    text = number,
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
    Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun Prev() {
//    CheckUserScreen(navController = rememberNavController(), "")
}