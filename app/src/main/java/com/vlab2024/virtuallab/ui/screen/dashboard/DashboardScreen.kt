package com.vlab2024.virtuallab.ui.screen.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vlab2024.virtuallab.R
import com.vlab2024.virtuallab.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.padding(horizontal = 21.dp),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Image(painter = painterResource(id = R.drawable.vlab_logo_mini), contentDescription = "virtual lab logo") },
                title = {
                Text(text = "Halo,\nAmru Abid Zakly", fontSize = 12.sp, lineHeight = 18.sp, fontWeight = FontWeight.SemiBold)
            },
                colors = topAppBarColors(containerColor = Color.Transparent))
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Text(text = stringResource(id = R.string.dashboard_headline), fontWeight = FontWeight.SemiBold, fontSize = 16.sp, fontFamily = Poppins)
        Spacer(modifier = Modifier.padding(bottom = 12.dp))
        Button(onClick = { /*TODO*/ }) {
            
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPrev() {
DashboardScreen(navController = rememberNavController())
}