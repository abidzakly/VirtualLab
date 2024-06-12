package org.d3ifcool.virtualab.ui.screen.admin.approval.content

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.AdminEmptyState
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckFileScreen(navController: NavHostController) {
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
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val viewModel: CheckFileViewModel = viewModel()
    val data = viewModel.data

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = if (data.isNotEmpty()) Arrangement.Top else Arrangement.Center,
    ) {
        if (data.isEmpty()) {
            AdminEmptyState(text = "Belum ada berkas yang perlu diperiksa")
        } else {
            RegularText(
                text = "Berkas yang perlu diperiksa: ",
                modifier = Modifier.padding(start = 16.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                FileList(username = "yanto123", filetype = R.string.materi_title) {
                    navController.navigate(Screen.FileInfo.route)
                }
            }
        }
    }
}

@Composable
private fun FileList(username: String, filetype: Int, onClick: () -> Unit) {
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
                    text = stringResource(filetype),
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
    CheckFileScreen(navController = rememberNavController())
}