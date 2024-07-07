package org.d3ifcool.virtualab.ui.screen.murid.latihan

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.JawabanMuridDummy
import org.d3ifcool.virtualab.data.model.NilaiDetailItem
import org.d3ifcool.virtualab.data.model.SoalDummy
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.GreenButton
import org.d3ifcool.virtualab.ui.theme.GreenStatus
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.LightBlue2
import org.d3ifcool.virtualab.ui.theme.RedButton
import org.d3ifcool.virtualab.ui.theme.RedStatus
import org.d3ifcool.virtualab.ui.theme.YellowStatus

@Composable
fun CekJawabanScreen(navController: NavHostController, viewModel: CekJawabanViewModel) {
    BackHandler {
        navController.navigate(Screen.Nilai.route) {
            popUpTo(Screen.MuridDashboard.route)
        }
    }

    Scaffold(
        topBar = {
            TopNav(
                title = R.string.cek_jawaban_title,
                navController = navController,
                isCustomBack = true,
                customBack = {
                    navController.navigate(Screen.Nilai.route) {
                        popUpTo(Screen.MuridDashboard.route)
                    }
                })
        },
        bottomBar = {
            BottomNav(navController = navController)
        },
        containerColor = Color.White
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
        ScreenContent(modifier = Modifier.padding(it), navController, viewModel)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: CekJawabanViewModel
) {
    val data by viewModel.data.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
//    val viewModel2: DetailLatihanVM = viewModel()

//    val answers by viewModel2.answers.collectAsState()
//    Log.d("Itemlist @Cek Jawaban", "answers: ${answers.size}")

//    val question1 = SoalDummy(1, 1, "C4H10 + O2 = ... CO2 + O...", "2;4")
//    val jawabanMurid1 = JawabanMuridDummy(1, 1, 1, 2)

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
                LoadingState()
        }

        ApiStatus.SUCCESS -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    RegularText(text = stringResource(R.string.kerjakan_latihan_header))
                }
                itemsIndexed(data!!.answersResults) { index, it ->
                    ItemListAbidd(index, it, viewModel)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RegularText(
                                text = "Nilai Latihan : ",
                                fontWeight = FontWeight.SemiBold
                            )
                            RegularText(
                                text = "${data!!.score}",
                                fontWeight = FontWeight.SemiBold,
                                color = when (data!!.score) {
                                    in 85.00..100.00 -> {
                                        GreenStatus
                                    }

                                    in 70.00..84.00 -> {
                                        YellowStatus
                                    }

                                    else -> {
                                        RedStatus
                                    }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        RegularText(
                            text = when (data!!.score) {
                                in 85.00..100.00 -> {
                                    "Keren banget! Selamat yaa🤩"
                                }

                                in 70.00..84.00 -> {
                                    "Not baaad 😁"
                                }

                                else -> {
                                    "Nice try. Tetap semangaat! 🔥"
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                navController.navigate(Screen.Nilai.route) {
                                    popUpTo(Screen.MuridDashboard.route)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(DarkBlueDarker),
                            contentPadding = PaddingValues(vertical = 9.dp, horizontal = 47.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            RegularText(
                                text = "Selesai",
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }

        ApiStatus.FAILED -> {
            MuridEmptyState(text = "Gagal memuat data.\nPastikan anda terhubung ke internet.") {
                viewModel.getNilaiDetail()
            }
        }
    }
}

@Composable
private fun ItemListAbidd(
    indexSoal: Int,
    soal: NilaiDetailItem,
    viewModel: CekJawabanViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RegularText(text = "Soal ${indexSoal + 1}")
            Spacer(modifier = Modifier.width(4.dp))
            if (soal.correct) {
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
        MediumLargeText(text = soal.questionTitle)
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
                RegularText(text = "Jawaban kamu", fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    soal.selectedOptions.forEachIndexed { index, it ->
                        RegularText(
                            text = it,
                            fontWeight = FontWeight.SemiBold
                        )
                        RegularText(
                            text =
                            when {
                                index < soal.selectedOptions.size - 2 -> {
                                    ", "
                                }

                                index < soal.selectedOptions.size - 1 -> {
                                    " dan "
                                }

                                else -> {
                                    ""
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
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
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            RegularText(
                                text = "Jawaban yang benar adalah "
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                soal.correctOptions.forEachIndexed { index, it ->
                                    RegularText(text = it, fontWeight = FontWeight.SemiBold)
                                    RegularText(
                                        text =
                                        when {
                                            index < soal.correctOptions.size - 2 -> {
                                                ", "
                                            }

                                            index < soal.correctOptions.size - 1 -> {
                                                " dan "
                                            }

                                            else -> {
                                                ""
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
private fun ItemList(
    question: SoalDummy,
    jawabanMurid: JawabanMuridDummy?,
    viewModel: DetailLatihanVM
) {
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
//    CekJawabanScreen(navController = rememberNavController())
}