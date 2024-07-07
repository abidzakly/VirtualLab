package org.d3ifcool.virtualab.ui.screen.admin.introduction

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ContentView
import org.d3ifcool.virtualab.ui.component.CustomTextField
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.PopUpDialog
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.getFileName


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateIntroContentScreen(navController: NavHostController, viewModel: UpdateIntroViewModel) {
    val data by viewModel.data.collectAsState()
    val context = LocalContext.current
    var fileNameData by remember { mutableStateOf("") }
    var existedFile by remember { mutableStateOf<String?>(null) }
    var fileExist by remember { mutableStateOf(false) }
    var judulMateri by remember { mutableStateOf("") }
    var descMateri by remember { mutableStateOf("") }

    var newFile by remember { mutableStateOf<Uri?>(null) }
    Log.d("UpdateIntroScreen", "Judul Materi: $judulMateri")

    LaunchedEffect(data) {
        if (data != null) {
            fileNameData = data!!.filename
            existedFile = ApiService.getIntroductionMedia()
            fileExist = true
            judulMateri = data!!.title
            descMateri = data!!.description
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back_button
                            ),
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.introduction_title),
                        fontFamily = Poppins
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = {
                        if (!fileExist || judulMateri.isEmpty() || descMateri.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Lengkapi semua data dulu, yaa",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (data == null) {
                                viewModel.addOrUpdateData(
                                    judulMateri, descMateri,
                                    newFile!!, false, context.contentResolver
                                )
                            } else {
                                viewModel.addOrUpdateData(
                                    judulMateri, descMateri,
                                    newFile, true, context.contentResolver
                                )
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.save_button),
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon")
                    }
                }
            )
        },
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { padding ->
        ScreenContent(
            modifier = Modifier.padding(padding),
            viewModel = viewModel,
            navController = navController,
            onFileExistChange = { fileExist = it },
            judulMateri = judulMateri,
            onJudulChange = { judulMateri = it },
            descMateri = descMateri,
            onDescChange = { descMateri = it },
            fileNameData = fileNameData,
            fileData = existedFile,
            fileUpload = newFile,
            onFileUpload = { uri, stringUri ->
                newFile = uri; existedFile = stringUri
            }
        )

        if (showDialog) {
            PopUpDialog(
                onDismiss = { showDialog = false },
                icon = R.drawable.baseline_warning_amber,
                title = "Anda yakin ingin menghapus?"
            ) {
                showDialog = false
                viewModel.deleteData()
            }
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: UpdateIntroViewModel,
    navController: NavHostController,
    onFileExistChange: (Boolean) -> Unit,
    judulMateri: String,
    onJudulChange: (String) -> Unit,
    descMateri: String,
    onDescChange: (String) -> Unit,
    fileNameData: String,
    fileData: String?,
    fileUpload: Uri?,
    onFileUpload: (Uri?, String?) -> Unit,
) {
    val context = LocalContext.current
    val status by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    var isUploading by remember { mutableStateOf(false) }

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            isUploading = true
            Toast.makeText(context, GenericMessage.loadingMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearStatus()
        }

        ApiStatus.SUCCESS -> {
            isUploading = false
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearStatus()
            navController.navigate(Screen.AdminDashboard.route) {
                popUpTo(Screen.AdminDashboard.route)
            }
        }

        ApiStatus.FAILED -> {
            isUploading = false
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearStatus()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        RegularText(text = stringResource(R.string.video_pengenalan_title))
        PickVideo(
            onFileExistChange = { onFileExistChange(it) },
            fileNameData = fileNameData,
            fileData = fileData,
            fileUpload = fileUpload,
            onFileUpload = { newFile, oldFile ->
                onFileUpload(
                    newFile,
                    oldFile
                )
            }
        )
        RegularText(text = stringResource(R.string.judul_pengenalan))
        CustomTextField(
            value = judulMateri,
            onValueChange = { onJudulChange(it) },
            placeholder = R.string.judul_materi_placholder,
            isTitle = true
        )
        RegularText(text = stringResource(R.string.desc_materi))
        CustomTextField(
            value = descMateri,
            onValueChange = { onDescChange(it) },
            placeholder = R.string.desc_materi_placeholder,
            modifier = Modifier.fillMaxSize()
        )
        if (isUploading) {
            Dialog(onDismissRequest = { }) {
                LoadingState()
            }
        }
    }


}

@Composable
fun PickVideo(
    onFileExistChange: (Boolean) -> Unit,
    fileNameData: String?,
    fileData: String?,
    fileUpload: Uri?,
    onFileUpload: (Uri?, String?) -> Unit,
) {
    val context = LocalContext.current
    var isVideo by remember { mutableStateOf<Boolean?>(null) }
    var fileName by remember { mutableStateOf("") }
    var isVideoPlaying by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        isVideo = null
        onFileUpload(it, null)
        it?.let {
            fileName = getFileName(context, it)
        }
    }

    LaunchedEffect(isVideo) {
        if (isVideo == false) {
            onFileUpload(null, null)
            onFileExistChange(false)
            Toast.makeText(
                context,
                "File yang diunggah harus bertipe video, yaa",
                Toast.LENGTH_SHORT
            ).show()
            isVideo = null
        } else if (isVideo == true) {
            onFileExistChange(true)
        }
    }
    if (fileData != null) {
        isVideo = true
        onFileUpload(null, fileData)
        ContentView(
            isUri = false,
            fileName = fileNameData!!,
            file = fileData,
            isVideo = isVideo,
            onVideoCheck = { isVideo = it },
            isVideoPlaying = isVideoPlaying,
            onPlaying = { isVideoPlaying = it },
            context = context,
        )
    } else {
        fileUpload?.let { uri ->
            onFileUpload(uri, null)
            ContentView(
                isUri = true,
                fileName = fileName,
                file = uri,
                isVideo = isVideo,
                onVideoCheck = { isVideo = it },
                isVideoPlaying = isVideoPlaying,
                onPlaying = { isVideoPlaying = it },
                context = context,
            )
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Button(
            onClick = {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
                    )
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.DarkGray
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_upload),
                contentDescription = "Tombol unggah media",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                onFileUpload(null, null)
                onFileExistChange(false)
                isVideo = null
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.DarkGray
            )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Tombol Delete Media",
                tint = Color.Black
            )
        }
    }
}


@Preview
@Composable
private fun Prev() {
//    UpdateIntroContentScreen(navController = rememberNavController())
}