package org.d3ifcool.virtualab.ui.screen.murid.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.GradientPage
import org.d3ifcool.virtualab.ui.component.TopNav

@Composable
fun MuridLatihanScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.latihan, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.MuridDashboard.route, navController = navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(bottom = it.calculateBottomPadding()), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    GradientPage(modifier, image = R.drawable.latihan_header) {
        Text(
            text = stringResource(R.string.latihan_header),
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 30.dp),
            textAlign = TextAlign.Justify
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            CardList("Latihan 1", "Mudah") {
                navController.navigate(Screen.MuridDetailLatihan.route)
            }
            CardList("Latihan 2", "Sedang") {
                navController.navigate(Screen.MuridDetailLatihan.route)
            }
            CardList("Latihan 3", "Sulit") {
                navController.navigate(Screen.MuridDetailLatihan.route)
            }
            CardList("Latihan 4", "Mudah") {
                navController.navigate(Screen.MuridDetailLatihan.route)
            }
            CardList("Latihan 5", "Sedang") {
                navController.navigate(Screen.MuridDetailLatihan.route)
            }
            CardList("Latihan 6", "Sulit") {
                navController.navigate(Screen.MuridDetailLatihan.route)
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
            Text(text = title, fontSize = 16.sp)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Tingkat Kesulitan", fontSize = 14.sp)
                Text(text = difficulty, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun Prev() {
    MuridLatihanScreen(navController = rememberNavController())
}