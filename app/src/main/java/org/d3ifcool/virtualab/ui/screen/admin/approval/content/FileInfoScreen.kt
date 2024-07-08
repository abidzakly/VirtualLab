package org.d3ifcool.virtualab.ui.screen.admin.approval.content

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.LatihanReview
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.model.MateriReview
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SemiLargeText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.GreenButton
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.RedButton

@Composable
fun FileInfoScreen(
    navController: NavHostController,
    postType: String,
    viewModel: FileInfoViewModel
) {
    val context = LocalContext.current

    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.CheckFile.route) {
                popUpTo(Screen.AdminDashboard.route)
            } 
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopNav(title = R.string.category_check_file, navController = navController)
        }, bottomBar = {
            BottomNav(navController = navController)
        }) {
        ScreenContent(modifier = Modifier.padding(it), postType, viewModel)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, postType: String, viewModel: FileInfoViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val status by viewModel.apiStatus.collectAsState()
    val data by viewModel.data.collectAsState()
    var materiData: Materi? = null
    var latihanData: Latihan? = null
    var dataId: Int?


    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
                LoadingState()
        }

        ApiStatus.SUCCESS -> {
            if (postType == "Materi") {
                materiData = data as Materi
                dataId = materiData.materiReview!!.materialId
            } else {
                latihanData = data as Latihan
                dataId = latihanData.latihanReview!!.exerciseId
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (postType == "Materi") {
                    MaterialContent(data = materiData!!.materiReview!!)
                } else {
                    ExerciseContent(data = latihanData!!.latihanReview!!)
                }
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            viewModel.approveData(dataId, postType)
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenButton,
                            contentColor = Color.Black
                        )
                    ) {
                        RegularText(text = stringResource(id = R.string.button_terima))
                    }
                    Button(
                        onClick = { showDialog = true },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RedButton,
                            contentColor = Color.White
                        )
                    ) {
                        RegularText(
                            text = stringResource(id = R.string.button_tolak),
                            color = Color.White
                        )
                    }
                    if (showDialog) {
                        RejectFilePopup(onDismiss = { showDialog = false }) {
                            viewModel.rejectData(dataId, postType)
                        }
                    }
                }
            }
        }

        ApiStatus.FAILED -> {
            RegularText(text = "Gagal memuat data.")
            Button(onClick = { viewModel.getData() }) {
                RegularText(text = "Coba Lagi.")
            }
        }
    }
}

@Composable
private fun MaterialContent(data: MateriReview) {
    InfoAuthor(nip = data.authorNip, username = data.authorUserName)
    RegularText(
        text = stringResource(R.string.judul_materi_guru),
        fontWeight = FontWeight.SemiBold
    )
    RegularText(
        text = data.title,
        fontWeight = FontWeight.Normal
    )
    RegularText(
        text = stringResource(R.string.media_pembelajaran),
        fontWeight = FontWeight.SemiBold
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (data.mediaType == "image") {
            Column(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(ApiService.getMateriMedia(data.materialId))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.broken_image),
                    modifier = Modifier
                        .size(225.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        RegularText(
            text = data.filename,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )
    }
    RegularText(
        text = stringResource(R.string.deskripsi_text),
        fontWeight = FontWeight.SemiBold
    )
    RegularText(
        text = data.description,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Justify
    )
}

@Composable
private fun ExerciseContent(data: LatihanReview) {
    InfoAuthor(nip = data.authorNip, username = data.authorUserName)
    RegularText(
        text = "Soal Latihan",
        fontWeight = FontWeight.SemiBold
    )
    RegularText(
        text = data.title,
        fontWeight = FontWeight.Normal
    )
    SmallText(
        text = "Jumlah Soal: ${data.questionCount}",
        fontWeight = FontWeight.SemiBold
    )
    RegularText(
        text = "Tingkat Kesulitan",
        fontWeight = FontWeight.SemiBold
    )
    RegularText(
        text = data.difficulty,
        fontWeight = FontWeight.Normal,
    )
}

@Composable
private fun InfoAuthor(nip: String, username: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SmallText(
                text = stringResource(id = R.string.nip_author),
                fontWeight = FontWeight.SemiBold
            )
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFDAE8EB), shape = RoundedCornerShape(5.dp))
                    .padding(4.dp)
            ) {
                RegularText(text = nip)
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SmallText(
                text = stringResource(id = R.string.username_label),
                fontWeight = FontWeight.SemiBold
            )
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFDAE8EB), shape = RoundedCornerShape(5.dp))
                    .padding(4.dp)
            ) {
                RegularText(text = username)
            }
        }

    }
}

@Composable
private fun RejectFilePopup(onDismiss: () -> Unit, onClick: () -> Unit) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_accept_berkas),
                contentDescription = "Ikon penerimaan berkas",
                tint = DarkBlue
            )
        },
        title = { SemiLargeText(text = "Tolak berkas ini?", fontWeight = FontWeight.SemiBold) },
        confirmButton = {
            Button(
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedButton,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                MediumLargeText(text = "Ya", fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                MediumLargeText(text = "Tidak", fontWeight = FontWeight.SemiBold)
            }
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
//    FileInfoScreen(rememberNavController())
}