package org.d3ifmgmp.virtualab.ui.screen.murid.latihan

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifmgmp.virtualab.R
import org.d3ifmgmp.virtualab.navigation.Screen
import org.d3ifmgmp.virtualab.ui.component.BottomNav
import org.d3ifmgmp.virtualab.ui.component.GradientPage
import org.d3ifmgmp.virtualab.ui.component.MuridEmptyState
import org.d3ifmgmp.virtualab.ui.component.RegularText
import org.d3ifmgmp.virtualab.ui.component.SmallText
import org.d3ifmgmp.virtualab.ui.component.TopNav

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
        ScreenContent(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController
        )
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    var isEmpty by remember { mutableStateOf(true) }

    GradientPage(modifier, image = R.drawable.latihan_header) {
        if (!isEmpty) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                RegularText(
                    text = stringResource(R.string.latihan_header),
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(30.dp))
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
        } else {
            MuridEmptyState(text = stringResource(id = R.string.empty_latihan))
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
    MuridLatihanScreen(navController = rememberNavController())
}