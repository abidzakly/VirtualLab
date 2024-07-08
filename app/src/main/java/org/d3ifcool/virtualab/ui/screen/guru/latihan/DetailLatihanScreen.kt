package org.d3ifcool.virtualab.ui.screen.guru.latihan

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.PopUpDialog
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.LightBlue

@Composable
fun DetailLatihanScreen(navController: NavHostController, viewModel: DetailLatihanViewModel) {
    val context = LocalContext.current
    val deleteStatus by viewModel.deleteStatus.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }
    var exerciseId by remember { mutableIntStateOf(0) }
    when (deleteStatus) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            Dialog(onDismissRequest = { }) {
                LoadingState()
            }
        }

        ApiStatus.SUCCESS -> {
            isUploading = false
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.GuruLatihan.route)
        }

        ApiStatus.FAILED -> {
            isUploading = false
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    Log.d("DetailLatihanScreen", "exerciseId: $exerciseId")
    Scaffold(topBar = {
        TopNav(title = R.string.guru_detail_latihan_title, navController = navController) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    navController.navigate(
                        Screen.UpdateLatihan.withId(
                            exerciseId
                        )
                    )
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Ikon Edit")
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Ikon Delete")
                }
            }
        }
    }, bottomBar = {
        BottomNav(navController = navController)
    },
        containerColor = Color.White ) { padding ->
        ScreenContent(
            modifier = Modifier.padding(padding),
            viewModel,
            onExerciseIdChange = { exerciseId = it })

        if (showDialog) {
            PopUpDialog(
                onDismiss = { showDialog = false },
                icon = R.drawable.baseline_warning_amber,
                title = "Anda yakin ingin menghapus latihan ini?"
            ) {
                isUploading = true
                viewModel.deleteLatihan()
            }
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: DetailLatihanViewModel,
    onExerciseIdChange: (Int) -> Unit
) {
    val latihanData by viewModel.latihanData.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            LoadingState()
        }

        ApiStatus.SUCCESS -> {
            val latihan = latihanData!!.latihanDetail!!
            onExerciseIdChange(latihan.exerciseId)
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    MediumText(
                        text = stringResource(R.string.judul_latihan_guru),
                        fontWeight = FontWeight.SemiBold
                    )
                    MediumText(text = latihan.title, fontWeight = FontWeight.Normal)
                    Spacer(modifier = Modifier.height(16.dp))
                    MediumText(
                        text = stringResource(R.string.tingkat_kesulitan_title),
                        fontWeight = FontWeight.SemiBold
                    )
                    MediumText(text = latihan.difficulty, fontWeight = FontWeight.Normal)
                    Spacer(modifier = Modifier.height(16.dp))
                    MediumText(
                        text = stringResource(R.string.jumlah_soal_title),
                        fontWeight = FontWeight.SemiBold
                    )
                    MediumText(text = "${latihan.questionCount}", fontWeight = FontWeight.Normal)
                    Spacer(modifier = Modifier.height(16.dp))
                    MediumText(
                        text = stringResource(R.string.perintah_soal),
                        fontWeight = FontWeight.Normal
                    )
                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                }
                itemsIndexed(latihanData?.soal!!) { i, it ->
                    ListSoal(
                        title = "Soal ${i + 1}",
                        question = it.questionText,
                        answersKey = it.answerKeys
                    )
                }
            }
        }

        ApiStatus.FAILED -> {
            GuruEmptyState(text = "Gagal memuat data.") {
                viewModel.getLatihanDetail()
            }
        }
    }

}

@Composable
fun ListSoal(
    title: String,
    question: String,
    answersKey: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MediumText(text = title, fontWeight = FontWeight.SemiBold)
        MediumText(
            text = question,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(16.dp))
        MediumText(text = stringResource(R.string.kunci_jawaban), fontWeight = FontWeight.SemiBold)
        answersKey.forEach {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                    .background(LightBlue)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MediumText(text = it)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
//    DetailLatihanScreen(rememberNavController(), 0)
}