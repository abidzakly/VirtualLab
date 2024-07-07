//package org.d3ifcool.virtualab.ui.screen.guru.latihan
//
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.testTag
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import org.d3ifcool.virtualab.R
//import org.d3ifcool.virtualab.data.model.QuestionCreate
//import org.d3ifcool.virtualab.ui.component.RegularText
//
//@Composable
//fun ye(modifier: Modifier = Modifier) {
//    LazyColumn(
//        modifier = Modifier
//            .padding(20.dp)
//            .weight(1f) // Memberi bobot agar LazyColumn menggunakan sisa ruang yang tersedia
//    ) {
//        items(latihan.questionCount) { count ->
//            // Semua konten soal
//            var isChecked =
//                remember { mutableStateListOf(false, false, false, false) }
//            var answerOption = remember {
//                mutableStateListOf(
//                    "",
//                    "",
//                    "",
//                    ""
//                )
//            }
//            var answerKey = remember { mutableStateListOf("", "") }
//            var selectedAnswersCount = remember { mutableStateOf(0) }
//            var questionText =
//                remember { mutableStateListOf(*Array(latihan.questionCount) { "" }) }
//            RegularText(
//                text = "Soal ${count + 1}",
//                fontWeight = FontWeight.SemiBold
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            CustomTextField2(
//                value = questionText[count],
//                onValueChange = { questionText[count] = (it) },
//                placeholder = R.string.soal
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            RegularText(text = stringResource(R.string.pilihan_jawaban))
//            for (i in 0 until 4) {
//                ListJawaban(
//                    modifier = Modifier.testTag("Jawaban ${i + 1}"),
//                    modifier2 = Modifier.testTag("Checkbox ${i + 1}"),
//                    isChecked = isChecked[i],
//                    onClick = {
//                        if (isChecked[i]) {
//                            // Uncheck the answer
//                            isChecked[i] = false
//                            selectedAnswersCount.value--
//                            // Remove from answerKey
//                            if (answerKey[0] == answerOption[i]) {
//                                answerKey[0] = ""
//                            } else if (answerKey[1] == answerOption[i]) {
//                                answerKey[1] = ""
//                            }
//                        } else {
//                            // Check if maximum selected answers reached
//                            if (selectedAnswersCount.value < 2) {
//                                // Check the answer
//                                isChecked[i] = true
//                                selectedAnswersCount.value++
//                                // Add to answerKey
//                                if (answerKey[0].isEmpty()) {
//                                    answerKey[0] = answerOption[i]
//                                } else {
//                                    answerKey[1] = answerOption[i]
//                                }
//                            } else {
//                                // Show toast
//                                Toast.makeText(
//                                    context,
//                                    "Harap pilih maksimal 2 kunci jawaban.",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    },
//                    jawaban = answerOption[i],
//                    onJawabanChange = { answerOption[i] = it }
//                )
//            }
//            Spacer(modifier = Modifier.height(12.dp))
//            HorizontalDivider(
//                color = Color.LightGray,
//                thickness = 2.dp
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            if (answerOption.all { it.isNotEmpty() } && answerKey.all { it.isNotEmpty() }
//                && questionText[count].isNotEmpty()
//            ) {
//                soal[count] = QuestionCreate(
//                    exerciseId,
//                    questionText[count],
//                    answerOption.toList(),
//                    answerKey.toList()
//                )
//                Log.d("AddSoalScreen", "soal $count added.\n soal: ${soal[count]}")
//            } else {
//                soal[count] = QuestionCreate(-1, "", emptyList(), emptyList())
//            }
//        }
//    }
//}