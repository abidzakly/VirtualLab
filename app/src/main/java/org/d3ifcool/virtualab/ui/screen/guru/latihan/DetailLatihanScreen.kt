package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.utils.ViewModelFactory

@Composable
fun DetailLatihanScreen(navController: NavHostController, exerciseId: Int) {
    val factory = ViewModelFactory(id = exerciseId)
    val viewModel: DetailLatihanViewModel = viewModel(factory = factory)

    Scaffold(topBar = {
        TopNav(title = R.string.guru_detail_latihan_title, navController = navController){
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Icon")
            }
        }
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), viewModel)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, viewModel: DetailLatihanViewModel) {
    val latihanData by viewModel.latihanData.collectAsState()
    val latihan = latihanData!!.latihan!!
    if (latihanData != null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            var nomorSoal = 0
            items(1) {
                RegularText(
                    text = stringResource(R.string.judul_materi_guru),
                    fontWeight = FontWeight.SemiBold
                )
                RegularText(text = latihan.title, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(16.dp))
                RegularText(
                    text = stringResource(R.string.tingkat_kesulitan_title),
                    fontWeight = FontWeight.SemiBold
                )
                RegularText(text = latihan.difficulty, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(16.dp))
                RegularText(
                    text = stringResource(R.string.perintah_soal),
                    fontWeight = FontWeight.Normal
                )
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
            }
            items(latihanData?.soal!!) {
                ListSoal(
                    title = "Soal ${nomorSoal + 1}",
                    question = it.questionText,
                    firstAnswer = it.answerKeys[0],
                    secondAnswer = it.answerKeys[1]
                )
                nomorSoal++
            }
        }
    }
}

@Composable
fun ListSoal(title: String, question: String, firstAnswer: String, secondAnswer: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RegularText(text = title, fontWeight = FontWeight.SemiBold)
        RegularText(
            text = question,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Justify
        )
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
            RegularText(text = firstAnswer)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                .background(LightBlue)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RegularText(text = secondAnswer)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
    DetailLatihanScreen(rememberNavController(), 0)
}