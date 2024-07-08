//package org.d3ifcool.virtualab.ui.screen.guru.latihan
//
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.testTag
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardCapitalization
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import androidx.navigation.NavHostController
//import org.d3ifcool.virtualab.R
//import org.d3ifcool.virtualab.data.model.QuestionCreate
//import org.d3ifcool.virtualab.data.network.ApiStatus
//import org.d3ifcool.virtualab.navigation.Screen
//import org.d3ifcool.virtualab.ui.component.BottomNav
//import org.d3ifcool.virtualab.ui.component.GuruEmptyState
//import org.d3ifcool.virtualab.ui.component.LoadingState
//import org.d3ifcool.virtualab.ui.component.PopUpDialog
//import org.d3ifcool.virtualab.ui.component.RegularText
//import org.d3ifcool.virtualab.ui.component.TopNav
//import org.d3ifcool.virtualab.ui.theme.GrayIco
//import org.d3ifcool.virtualab.ui.theme.GrayTextField
//import org.d3ifcool.virtualab.ui.theme.LightBlue
//
//@Composable
//fun AddSoalScreen(navController: NavHostController, viewModel: AddSoalViewModel) {
//
//    val successMessage by viewModel.successMessage.collectAsState()
//    val uploadStatus by viewModel.uploadStatus.collectAsState()
//    val errorMessage by viewModel.errorMessage.collectAsState()
//    var isUploading by remember { mutableStateOf(false) }
//    var showDeleteDialog by remember { mutableStateOf(false) }
//
//    val context = LocalContext.current
//
//    when (uploadStatus) {
//        ApiStatus.IDLE -> null
//        ApiStatus.LOADING -> {
//            isUploading = true
//        }
//
//        ApiStatus.SUCCESS -> {
//            isUploading = false
//            Toast.makeText(
//                context,
//                successMessage,
//                Toast.LENGTH_SHORT
//            ).show()
//            navController.navigate(Screen.GuruLatihan.route) {
//                popUpTo(Screen.GuruLatihan.route)
//            }
//            viewModel.clearStatus()
//        }
//
//        ApiStatus.FAILED -> {
//            Toast.makeText(
//                context,
//                errorMessage,
//                Toast.LENGTH_SHORT
//            ).show()
//            viewModel.clearStatus()
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopNav(title = R.string.add_soal_title, navController = navController) {
//                IconButton(onClick = { showDeleteDialog = true }) {
//                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Icon Delete")
//                }
//            }
//        },
//        bottomBar = {
//            BottomNav(navController = navController)
//        }
//    ) {
//        ScreenContent(
//            modifier = Modifier.padding(it),
//            viewModel,
//        )
//        if (isUploading) {
//            Dialog(onDismissRequest = { }) {
//                LoadingState()
//            }
//        }
//        if (showDeleteDialog) {
//            PopUpDialog(
//                icon = R.drawable.baseline_warning_amber,
//                title = "Anda yakin ingin menghapus latihan ini?",
//                onDismiss = { showDeleteDialog = false }
//            ) {
//                showDeleteDialog = false
//                viewModel.deleteLatihan()
//            }
//        }
//    }
//}
//
//@Composable
//private fun ScreenContent(
//    modifier: Modifier,
//    viewModel: AddSoalViewModel,
//) {
//    val context = LocalContext.current
//    val fetchStatus by viewModel.fetchStatus.collectAsState()
//    val state by viewModel.state.collectAsState()
//    val latihanData by viewModel.latihanData.collectAsState()
//    val listSoal by viewModel.listSoal.collectAsState()
//
//    Log.d("AddSoalScreen", "Latihan Data: $latihanData")
//    when (fetchStatus) {
//        ApiStatus.IDLE -> null
//        ApiStatus.LOADING -> {
//            LoadingState()
//        }
//
//        ApiStatus.FAILED -> {
//            GuruEmptyState(text = "Gagal Memuat Data") {
//                viewModel.getCurrentLatihan()
//            }
//        }
//
//        ApiStatus.SUCCESS -> {
//            val latihan = latihanData!!.latihanDetail!!
//            val soalData = latihanData!!.soal!!
//            Log.d("AddSoalScreen", "soalData: $soalData")
//            Box(
//                modifier = modifier
//                    .padding(24.dp)
//                    .fillMaxSize()
//            ) {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    item {
//                        RegularText(text = "${stringResource(R.string.title_soal)} ${latihan.title}")
//                        RegularText(text = "${stringResource(R.string.difficulty_soal)} ${latihan.difficulty}")
//                    }
//                    items(latihan.questionCount) { index ->
//                        var answerPositions = remember {
//                            mutableStateListOf(
//                                -1,
//                                -1,
//                                -1
//                            )
//                        } // to track positions of selected answers
//                        RegularText(
//                            text = "Soal ${index + 1}",
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        CustomTextField2(
//                            value = state.questionText[index],
//                            onValueChange = { viewModel.setQuestionText(index, it) },
//                            placeholder = R.string.soal
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        RegularText(text = stringResource(R.string.pilihan_jawaban))
//                        for (i in 0 until 4) {
//                            ListJawaban(
//                                modifier = Modifier.testTag("Jawaban ${i + 1}"),
//                                modifier2 = Modifier.testTag("Checkbox ${i + 1}"),
//                                isChecked = state.isChecked[index][i],
//                                onClick = {
//                                    if (state.isChecked[index][i]) {
//                                        viewModel.setIsChecked(index, i, false)
//                                        viewModel.setSelectedAnswers(
//                                            index,
//                                            state.selectedAnswers[index] - 1
//                                        )
//                                        val pos = answerPositions.indexOf(i)
//                                        if (pos != -1) {
//                                            viewModel.setAnswerKeys(
//                                                index = index,
//                                                position = pos,
//                                                isRemove = true
//                                            )
//                                            answerPositions[pos] = -1
//                                            // Shift positions left to maintain order
//                                            for (j in pos until answerPositions.size - 1) {
//                                                answerPositions[j] = answerPositions[j + 1]
//                                            }
//                                            answerPositions[answerPositions.size - 1] = -1
//                                        }
//                                    } else {
//                                        if (state.answersOptions[index][i].isNotEmpty()) {
//                                            if (state.selectedAnswers[index] < 3) {
//                                                viewModel.setIsChecked(index, i, true)
//                                                viewModel.setSelectedAnswers(
//                                                    index,
//                                                    state.selectedAnswers[index] + 1
//                                                )
//                                                viewModel.setAnswerKeys(
//                                                    index = index,
//                                                    answer = state.answersOptions[index][i]
//                                                )
//                                                val pos = answerPositions.indexOf(-1)
//                                                if (pos != -1) {
//                                                    answerPositions[pos] = i
//                                                }
//                                            } else {
//                                                Toast.makeText(
//                                                    context,
//                                                    "Pilih maksimal 3 kunci jawaban, yaa.",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
//                                            }
//                                        } else {
//                                            Toast.makeText(
//                                                context,
//                                                "Wah, teksnya masih kosong nih!",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        }
//                                    }
//                                },
//                                jawaban = state.answersOptions[index][i],
//                                onJawabanChange = {
//                                    viewModel.setAnswerOptions(index, i, it)
//                                    if (it.isEmpty() && state.isChecked[index][i]) {
//                                        viewModel.setIsChecked(index, i, false)
//                                        viewModel.setSelectedAnswers(
//                                            index,
//                                            state.selectedAnswers[index] - 1
//                                        )
//                                        val pos = answerPositions.indexOf(i)
//                                        if (pos != -1) {
//                                            viewModel.setAnswerKeys(
//                                                index,
//                                                isRemove = true,
//                                                position = pos
//                                            )
//                                            answerPositions[pos] = -1
//                                            // Shift positions left to maintain order
//                                            for (j in pos until answerPositions.size - 1) {
//                                                answerPositions[j] = answerPositions[j + 1]
//                                            }
//                                            answerPositions[answerPositions.size - 1] = -1
//                                        }
//                                    } else if (state.isChecked[index][i]) {
//                                        // Update the answerKey with new value
//                                        val pos = answerPositions.indexOf(i)
//                                        if (pos != -1) {
//                                            viewModel.setAnswerKeys(
//                                                index,
//                                                isUpdate = true,
//                                                position = pos,
//                                                answer = it
//                                            )
//                                        }
//                                    }
//                                }
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(12.dp))
//                        HorizontalDivider(
//                            color = Color.LightGray,
//                            thickness = 2.dp
//                        )
//                        Spacer(modifier = Modifier.height(12.dp))
//                        if (state.answersOptions[index].isNotEmpty() &&
//                            state.answersOptions[index].all { it.isNotEmpty() } &&
//                            state.answersKeys.isNotEmpty() &&
//                            state.questionText[index].isNotEmpty()
//                        ) {
//                            viewModel.setSoal(
//                                index,
//                                QuestionCreate(
//                                    state.questionText[index],
//                                    state.answersOptions[index],
//                                    state.answersKeys[index]
//                                )
//                            )
//                        } else {
//                            viewModel.setSoal(
//                                index,
//                                QuestionCreate(),
//                                isRemove = true
//                            )
//                        }
//                    }
//                    item {
//                        Column(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Button(
//                                modifier = Modifier
//                                    .padding(12.dp),
//                                onClick = {
//                                    if (listSoal.all {
//                                            it.questionText == "" && it.questionText.isEmpty()
//                                                    && it.optionText.isEmpty()
//                                                    && it.answerKeys.isEmpty()
//                                        }
//                                    ) {
//                                        Toast.makeText(
//                                            context,
//                                            "Semua data harus diisi, yaa",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    } else {
//                                        viewModel.submitSoal()
//                                    }
//                                },
//                                shape = RoundedCornerShape(10.dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = LightBlue,
//                                    contentColor = Color.Black
//                                )
//                            ) {
//                                RegularText(
//                                    text = stringResource(R.string.button_unggah),
//                                    fontWeight = FontWeight.SemiBold
//                                )
//                            }
//                        }
//                    }
//                }
////                    for (index in 0 until latihan.questionCount) {
////                    }
//            }
//        }
//    }
//}
//
//
//@Composable
//private fun CustomTextField2(
//    modifier: Modifier? = Modifier,
//    isNumber: Boolean? = false,
//    value: String,
//    onValueChange: (String) -> Unit,
//    placeholder: Int,
//    isPhone: Boolean = false
//) {
//    TextField(
//        modifier = modifier?.fillMaxWidth()
//            ?: Modifier.fillMaxWidth(),
//        value = value,
//        onValueChange = { onValueChange(it) },
//        placeholder = { Text(text = stringResource(id = placeholder), color = GrayIco) },
//        singleLine = modifier == null,
//        shape = RoundedCornerShape(16.dp),
//        colors = TextFieldDefaults.colors(
//            unfocusedIndicatorColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedContainerColor = GrayTextField,
//            focusedContainerColor = GrayTextField
//        ),
//        keyboardOptions = KeyboardOptions(
//            keyboardType = if (isPhone) KeyboardType.Number else if (isNumber == true) KeyboardType.Text else KeyboardType.Text,
//            capitalization = KeyboardCapitalization.Sentences
//        )
//    )
//    Spacer(modifier = Modifier.height(8.dp))
//}
//
//@Composable
//private fun ListJawaban(
//    modifier: Modifier = Modifier,
//    modifier2: Modifier = Modifier,
//    isChecked: Boolean,
//    onClick: () -> Unit,
//    jawaban: String,
//    onJawabanChange: (String) -> Unit
//) {
//    Row(
//        modifier = Modifier.padding(6.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start
//    ) {
//        IconButton(modifier = modifier2, onClick = { onClick() }) {
//            Icon(
//                painterResource(if (!isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                tint = Color(0xFF0E4B9C)
//            )
//        }
//        Spacer(modifier = Modifier.width(4.dp))
//        CustomTextField2(
//            modifier = modifier,
//            value = jawaban,
//            onValueChange = { onJawabanChange(it) },
//            placeholder = R.string.jawaban_placeholder
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun Prev() {
////    AddSoalScreen(navController = rememberNavController(), 0)
//}