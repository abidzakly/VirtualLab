package org.d3ifcool.virtualab.ui.screen.murid.latihan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.model.JawabanMurid
import org.d3ifcool.virtualab.model.Soal
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.GreenButton
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.LightBlue2
import org.d3ifcool.virtualab.ui.theme.RedButton

@Composable
fun CekJawabanScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.cek_jawaban_title, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.MuridDashboard.route, navController = navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val viewModel: DetailLatihanVM = viewModel()

    val answers by viewModel.answers.collectAsState()
    Log.d("Itemlist @Cek Jawaban", "answers: ${answers.size}")

    val question1 = Soal(1, 1, "C4H10 + O2 = ... CO2 + O...", "2;4")
    val jawabanMurid1 = JawabanMurid(1, 1, 1, 2)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        RegularText(text = stringResource(R.string.kerjakan_latihan_header))
        Column(
            modifier = Modifier
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemList(question1, jawabanMurid1, viewModel)
            ItemList(question1, jawabanMurid1, viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            RegularText(text = "Nilai Latihan : 50", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate(Screen.Nilai.route) },
                colors = ButtonDefaults.buttonColors(DarkBlueDarker),
                contentPadding = PaddingValues(vertical = 9.dp, horizontal = 47.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                RegularText(text = "Selesai", fontWeight = FontWeight.SemiBold, color = Color.White)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ItemList(question: Soal, jawabanMurid: JawabanMurid?, viewModel: DetailLatihanVM) {
    val jawabanBenar = question.answerKey.split(";").map { it.toInt() }
    val isCorrect = jawabanMurid?.selectedOptionId in jawabanBenar
    val nilaiSoal = if (isCorrect) 10 else 0

    var expanded by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RegularText(text = "Soal ${question.questionId}")
            Spacer(modifier = Modifier.width(4.dp))
            if (isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Jawaban benar",
                    tint = GreenButton
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Jawaban salah",
                    tint = RedButton
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        MediumLargeText(text = question.questionText)
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(LightBlue2)
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                RegularText(text = "Jawaban kamu: ", fontWeight = FontWeight.SemiBold)
                RegularText(text = "${jawabanMurid?.selectedOptionId}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RegularText(text = stringResource(id = R.string.lihat_kunci_jawaban))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Expand/Collapse",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (expanded) {
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .background(color = LightBlue, shape = RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RegularText(
                            text = "Jawaban Benar: ${question.answerKey}"
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}
@Preview
@Composable
private fun Prev() {
    MuridDetailLatihanScreen(rememberNavController())
}