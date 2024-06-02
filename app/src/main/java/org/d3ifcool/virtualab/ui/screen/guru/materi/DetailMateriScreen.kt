package org.d3ifcool.virtualab.ui.screen.guru.materi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav

@Composable
fun DetailMateriScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.guru_detail_materi_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruMateri.route, navController)
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
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RegularText(text = stringResource(R.string.judul_materi_guru), fontWeight = FontWeight.SemiBold)
        RegularText(text = stringResource(R.string.judul_materi_data), fontWeight = FontWeight.Normal )
        RegularText(text = stringResource(R.string.media_pembelajaran), fontWeight = FontWeight.SemiBold )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(R.drawable.media_example),
                contentDescription = "File Media Pembelajaran"
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            RegularText(text = stringResource(R.string.file_media_belajar), fontWeight = FontWeight.Normal)
        }
        RegularText(text = stringResource(R.string.deskripsi_text), fontWeight = FontWeight.SemiBold)
        RegularText(text = stringResource(R.string.guru_deskripsi_materi_data), fontWeight = FontWeight.Normal, textAlign = TextAlign.Justify )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
    DetailMateriScreen(rememberNavController())
}