package org.d3ifcool.virtualab.ui.screen.guru.latihan

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.QuestionCreate
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.GrayIco
import org.d3ifcool.virtualab.ui.theme.GrayTextField
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.utils.ViewModelFactory

@Composable
fun AddSoalScreen(navController: NavHostController, exerciseId: Int) {

    Log.d("AddSoalScreen", "exercise ID: $exerciseId")
    val factory = ViewModelFactory(id = exerciseId)
    val viewModel: AddSoalViewModel = viewModel(factory = factory)
    val uploadStatus by viewModel.uploadStatus.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uploadStatus) {
        if (uploadStatus) {
            Toast.makeText(
                context,
                "Soal berhasil disimpan.",
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate(Screen.GuruLatihan.route)
        } else if (errorMsg != null) {
            Toast.makeText(
                context,
                errorMsg,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearErrorMsg()
        }
    }


    Scaffold(
        topBar = {
            TopNav(title = R.string.add_soal_title, navController = navController)
        },
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) {
        ScreenContent(
            modifier = Modifier.padding(it),
            viewModel,
            exerciseId
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: AddSoalViewModel,
    exerciseId: Int
) {
    val context = LocalContext.current
    val latihanData by viewModel.latihanData.collectAsState()
    val latihan = latihanData!!.latihan!!
    val soal = latihanData!!.soal!!
    var isClicked by remember { mutableStateOf(false) }
    Log.d("AddSoalScreen", "Latihan Data: $latihanData")
    if (latihanData != null) {
        var soal = remember {
            mutableStateListOf(*Array(latihan.questionCount) {
                QuestionCreate(
                    -1,
                    "",
                    emptyList(),
                    emptyList()
                )
            })
        }

        Box(
            modifier = modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp) // Menambah ruang di bagian bawah untuk tombol
            ) {
                RegularText(text = "${stringResource(R.string.title_soal)} ${latihan.title}")
                RegularText(text = "${stringResource(R.string.difficulty_soal)} ${latihan.difficulty}")
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f) // Memberi bobot agar LazyColumn menggunakan sisa ruang yang tersedia
                ) {
                    items(latihan.questionCount) { count ->
                        // Semua konten soal
                        var isChecked = remember { mutableStateListOf(false, false, false, false) }
                        var answerOption = remember {
                            mutableStateListOf(
                                "",
                                "",
                                "",
                                ""
                            )
                        }
                        var answerKey = remember { mutableStateListOf("", "") }
                        var selectedAnswersCount = remember { mutableStateOf(0) }
                        var questionText =
                            remember { mutableStateListOf(*Array(latihan.questionCount) { "" }) }
                        RegularText(
                            text = "Soal ${count + 1}",
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomTextField2(
                            value = questionText[count],
                            onValueChange = { questionText[count] = (it) },
                            placeholder = R.string.soal
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        RegularText(text = stringResource(R.string.pilihan_jawaban))
                        for (i in 0 until 4) {
                            ListJawaban(
                                modifier = Modifier.testTag("Jawaban ${i + 1}"),
                                modifier2 = Modifier.testTag("Checkbox ${i + 1}"),
                                isChecked = isChecked[i],
                                onClick = {
                                    if (isChecked[i]) {
                                        // Uncheck the answer
                                        isChecked[i] = false
                                        selectedAnswersCount.value--
                                        // Remove from answerKey
                                        if (answerKey[0] == answerOption[i]) {
                                            answerKey[0] = ""
                                        } else if (answerKey[1] == answerOption[i]) {
                                            answerKey[1] = ""
                                        }
                                    } else {
                                        // Check if maximum selected answers reached
                                        if (selectedAnswersCount.value < 2) {
                                            // Check the answer
                                            isChecked[i] = true
                                            selectedAnswersCount.value++
                                            // Add to answerKey
                                            if (answerKey[0].isEmpty()) {
                                                answerKey[0] = answerOption[i]
                                            } else {
                                                answerKey[1] = answerOption[i]
                                            }
                                        } else {
                                            // Show toast
                                            Toast.makeText(
                                                context,
                                                "Harap pilih maksimal 2 kunci jawaban.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                },
                                jawaban = answerOption[i],
                                onJawabanChange = { answerOption[i] = it }
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 2.dp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        if (answerOption.all { it.isNotEmpty() } && answerKey.all { it.isNotEmpty() }
                            && questionText[count].isNotEmpty()
                        ) {
                            soal[count] = QuestionCreate(
                                exerciseId,
                                questionText[count],
                                answerOption.toList(),
                                answerKey.toList()
                            )
                            Log.d("AddSoalScreen", "soal $count added.\n soal: ${soal[count]}")
                        } else {
                            soal[count] = QuestionCreate(-1, "", emptyList(), emptyList())
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp),
                onClick = {
                    if (soal.all {
                            it.questionText == "" || it.questionText.isEmpty()
                                    || it.optionText.isEmpty() || it.optionText.size < 4
                                    || it.exerciseId == -1
                                    || it.answerKeys.isEmpty() || it.answerKeys.size < 2
                        }
                    ) {
                        Toast.makeText(context, "Semua data harus diisi, yaa", Toast.LENGTH_SHORT).show()
                        Log.d("AddSoalScreen", "IF IS TRUE")
                    } else {
                        Log.d("AddSoalScreen", "FALSE IS TRUE")
                        viewModel.addSoal(exerciseId, soal.toList())
                        Log.d("AddSoalScreen", "Soal: ${soal.toList()}")
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                )
            ) {
                RegularText(
                    text = stringResource(R.string.button_unggah),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CustomTextField2(
    modifier: Modifier? = Modifier,
    isNumber: Boolean? = false,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: Int,
    isPhone: Boolean = false
) {
    TextField(
        modifier = modifier?.fillMaxWidth()
            ?: Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = stringResource(id = placeholder), color = GrayIco) },
        singleLine = modifier == null,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = GrayTextField,
            focusedContainerColor = GrayTextField
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPhone) KeyboardType.Number else if (isNumber == true) KeyboardType.Text else KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ListJawaban(
    modifier: Modifier = Modifier,
    modifier2: Modifier = Modifier,
    isChecked: Boolean,
    onClick: (Boolean) -> Unit,
    jawaban: String,
    onJawabanChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(modifier = modifier2, onClick = { onClick(!isChecked) }) {
            Icon(
                painterResource(if (!isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = Color(0xFF0E4B9C)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        CustomTextField2(
            modifier = modifier,
            value = jawaban,
            onValueChange = { onJawabanChange(it) },
            placeholder = R.string.jawaban_placeholder
        )
    }
}

@Preview
@Composable
private fun Prev() {
    AddSoalScreen(navController = rememberNavController(), 0)
}