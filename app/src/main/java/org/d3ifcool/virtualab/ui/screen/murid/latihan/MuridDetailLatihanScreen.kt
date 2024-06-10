package org.d3ifcool.virtualab.ui.screen.murid.latihan

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import org.d3ifcool.virtualab.model.OpsiJawaban
import org.d3ifcool.virtualab.model.Soal
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.LightBlue2
import org.d3ifcool.virtualab.ui.theme.RedButton

@Composable
fun MuridDetailLatihanScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.latihan_x, navController = navController)
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
    var showDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    val answers by viewModel.answers.collectAsState()
    Log.d("Itemlist @Murid Detail Latihan", "answers: ${answers.size}")

    val context = LocalContext.current

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
            ItemList(noSoal = "1", exerciseId = 1, viewModel)
            ItemList(noSoal = "2", exerciseId = 1, viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (answers.values.all { it.size == 2 }) {
                        showConfirmDialog = true
                    } else {
                        Toast.makeText(
                            context,
                            "Harus memilih 2 jawaban untuk tiap soal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = buttonColors(DarkBlueDarker),
                contentPadding = PaddingValues(vertical = 9.dp, horizontal = 47.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                RegularText(
                    text = "Kumpulkan Jawaban",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
    if (showConfirmDialog) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showConfirmDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = "Konfirmasi Jawaban",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            },
            title = {
                MediumLargeText(
                    text = "Anda yakin ingin mengumpulkan jawaban?",
                    fontWeight = FontWeight.SemiBold
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        showDialog = true
                    },
                    colors = buttonColors(
                        containerColor = LightBlue
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    RegularText(text = "Ya", fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmDialog = false },
                    colors = buttonColors(
                        containerColor = RedButton
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    RegularText(
                        text = "Batal",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
    if (showDialog) {
        ExerciseDonePopup(
            onDismiss = { showDialog = false },
            navController = navController
        )
    }
}

@Composable
private fun ItemList(noSoal: String, exerciseId: Int, viewModel: DetailLatihanVM) {
    val questions = listOf(
        Soal(1, 1, "C4H10 + O2 = ... CO2 + O...", "2;4"),
        Soal(2, 1, "H2 + O2 = ... H2O", "2;1")
    )
    val options = listOf(
        OpsiJawaban(1, 1, "2"),
        OpsiJawaban(2, 1, "3"),
        OpsiJawaban(3, 1, "4"),
        OpsiJawaban(4, 1, "5"),
        OpsiJawaban(5, 2, "1"),
        OpsiJawaban(6, 2, "2"),
        OpsiJawaban(7, 2, "3"),
        OpsiJawaban(8, 2, "4")
    )

    val answers = remember { mutableStateListOf<OpsiJawaban>() }

    LaunchedEffect(answers.size) {
        if (answers.size <= 2) {
            viewModel.setAnswers(noSoal.toInt(), answers)
        }
    }

    Column {
        RegularText(text = "Soal $noSoal")
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(LightBlue2)
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                questions.filter { it.exerciseId == exerciseId && it.questionId == noSoal.toInt() }
                    .forEachIndexed { index, question ->
                        val questionOptions =
                            options.filter { it.questionId == question.questionId }
                        QuestionItem(
                            question = question,
                            options = questionOptions,
                            selectedAnswers = answers,
                            onOptionSelected = { option, isSelected ->
                                if (isSelected) {
                                    if (answers.size < 2) {
                                        answers.add(option)
                                    }
                                } else {
                                    answers.remove(option)
                                }
                            }
                        )
                    }
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
private fun QuestionItem(
    question: Soal,
    options: List<OpsiJawaban>,
    selectedAnswers: List<OpsiJawaban>? = null,
    onOptionSelected: (OpsiJawaban, Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        MediumLargeText(text = question.questionText)
        RegularText(
            text = stringResource(id = R.string.instructions),
            fontWeight = FontWeight.SemiBold
        )
        options.forEach { option ->
            val isSelected = selectedAnswers?.contains(option) == true
            val borderColor = if (isSelected) DarkBlueDarker else Color.Transparent
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(LightBlue)
                    .border(4.dp, borderColor, shape = RoundedCornerShape(5.dp))
                    .padding(8.dp)
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onOptionSelected(option, !isSelected)
                    }
            ) {
                MediumText(
                    text = option.optionText,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ExerciseDonePopup(onDismiss: () -> Unit, navController: NavHostController) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.Red
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_process_success),
                    contentDescription = "Ikon proses berhasil",
                    tint = DarkBlue
                )
                RegularText(
                    text = "Bagus sekali!\n" +
                            "Anda telah menyelesaikan semua soal",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                            navController.navigate(Screen.CekJawaban.route)
                        },
                        colors = buttonColors(
                            containerColor = LightBlue,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        MediumLargeText(
                            text = "OK",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Prev() {
    MuridDetailLatihanScreen(rememberNavController())
}