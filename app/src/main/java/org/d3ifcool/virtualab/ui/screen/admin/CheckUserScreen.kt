package org.d3ifcool.virtualab.ui.screen.admin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.AdminEmptyState
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.utils.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckUserScreen(navController: NavHostController) {
    val dataStore = UserDataStore(LocalContext.current)
    val loggedInEmail by dataStore.userEmailFlow.collectAsState("")

    val factory = ViewModelFactory(loggedInEmail)
    val viewModel: CheckUsersViewModel = viewModel(factory = factory)


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
            title = { LargeText(text = stringResource(id = R.string.category_check_role)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.Black
            ),
        )
    }, bottomBar = {
        BottomNav(currentRoute = Screen.AdminDashboard.route, navController = navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), navController, viewModel)
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

    Log.d("GET ALL USER Error", "Get User Error: $errorMsg")
    Log.d("GET ALL USER", "Get USER: $userList")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = if (userList.isNotEmpty()) Arrangement.Top else Arrangement.Center,
    ) {
        if (userList.isEmpty()) {
            AdminEmptyState(text = "Belum ada akun yang perlu diperiksa")
        } else {
            RegularText(
                text = "Daftar akun baru dibuat: ",
                modifier = Modifier.padding(start = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                items(userList) { user ->
                    user?.let {
                        AccountList(
                            username = it.username ?: "Unknown",
                            number = it.nip ?: it.nisn ?: "Unknown"
                        ) {
                            navController.navigate(Screen.UsersInfo.withId(it.user_id))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountList(username: String, number: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
            .background(LightBlue)
            .padding(24.dp)
            .clickable { onClick() },
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

@Preview
@Composable
private fun Prev() {
    CheckUserScreen(navController = rememberNavController())
}