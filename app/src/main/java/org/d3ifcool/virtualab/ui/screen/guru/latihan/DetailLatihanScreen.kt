package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.Soal
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.LightBlue

@Composable
fun DetailLatihanScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.guru_detail_latihan_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}
@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RegularText(text = stringResource(R.string.judul_materi_guru), fontWeight = FontWeight.SemiBold)
        RegularText(text = stringResource(R.string.judul_latihan_data), fontWeight = FontWeight.Normal )
        RegularText(text = stringResource(R.string.tingkat_kesulitan_title), fontWeight = FontWeight.SemiBold )
        RegularText(text = stringResource(R.string.tingkat_kesulitan_data), fontWeight = FontWeight.Normal)
        RegularText(text = stringResource(R.string.perintah_soal), fontWeight = FontWeight.Normal)
        HorizontalDivider(modifier = Modifier.padding(top =  16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ListSoal(
                title = R.string.soal_title,
                question = R.string.pertanyaan,
                firstAnswer = R.string.jawaban_pertama,
                secondAnswer = R.string.jawaban_kedua
            )

            ListSoal(
                title = R.string.soal_title,
                question = R.string.pertanyaan,
                firstAnswer = R.string.jawaban_pertama,
                secondAnswer = R.string.jawaban_kedua
            )
        }
    }
}
@Composable
fun ListSoal(soal: Soal? = null, title: Int, question: Int, firstAnswer: Int, secondAnswer: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RegularText(text = stringResource(title), fontWeight = FontWeight.SemiBold)
        RegularText(text = stringResource(question), fontWeight = FontWeight.Normal, textAlign = TextAlign.Justify)
        Spacer(modifier = Modifier.height(16.dp))
        RegularText(text = stringResource(R.string.kunci_jawaban), fontWeight = FontWeight.SemiBold)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                .background(LightBlue)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RegularText(text = stringResource(firstAnswer))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                .background(LightBlue)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RegularText(text = stringResource(secondAnswer))
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
    DetailLatihanScreen(rememberNavController())
}