package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.LightBlueGradient
import org.d3ifcool.virtualab.ui.theme.Poppins

@Composable
fun GuruLatihanScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.lihat_latihan_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController = navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(4.dp)
    ) {
        RegularText(
            text = "Latihan yang pernah ditambahkan: ",
            modifier = Modifier.padding(start = 16.dp)
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            ListLatihan(Modifier.padding(8.dp))
            ListLatihan(Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ListLatihan(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
            .background(LightBlueGradient)
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MediumText(
                text = stringResource(R.string.latihan),
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            RegularText(
                text = stringResource(R.string.latihan_title_data),
            )
        }
        Icon(
            painter = painterResource(R.drawable.baseline_arrow_circle_outline_28),
            contentDescription = "Lihat Materi"
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun Prev() {
    GuruLatihanScreen(navController = rememberNavController())
}