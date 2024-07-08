package org.d3ifcool.virtualab.ui.screen.guru.materi

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.CustomTextField
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.LoadingStateDialog
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.component.VideoPlayer
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.getFileName
import org.d3ifcool.virtualab.utils.isImage
import java.io.InputStream

@Composable
fun AddMateriScreen(navController: NavHostController, viewModel: AddMateriViewModel) {
    val context = LocalContext.current
    val status by viewModel.apiStatus.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    when (isUploading) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            Toast.makeText(context, GenericMessage.loadingMessage, Toast.LENGTH_SHORT).show()
            LoadingStateDialog()
        }

        ApiStatus.FAILED -> {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearStatus()
        }

        ApiStatus.SUCCESS -> {
            Toast.makeText(context, successMessage, Toast.LENGTH_LONG).show()
            navController.navigate(Screen.GuruDashboard.route) {
                popUpTo(Screen.GuruDashboard.route)
            }
            viewModel.clearStatus()
        }
    }

    Scaffold(
        topBar = {
            TopNav(
                title = if (status != ApiStatus.IDLE) R.string.edit_materi else R.string.buat_materi,
                navController = navController
            )
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), viewModel, status, context)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: AddMateriViewModel,
    status: ApiStatus,
    context: Context
) {
    val materiData by viewModel.materiData.collectAsState()
    val materialId by viewModel.materiId.collectAsState()

    var judulMateri by remember { mutableStateOf("") }
    var isUri by remember { mutableStateOf(true) }
    var file by remember { mutableStateOf<Any?>(null) }
    var isFileChanged by remember { mutableStateOf(false) }
    var mediaType by remember { mutableStateOf("") }
    var descMateri by remember { mutableStateOf("") }

//    var newFile by remember { mutableStateOf<Uri?>(null) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (status) {
            ApiStatus.IDLE -> null
            ApiStatus.LOADING -> {
                LoadingState()
            }

            ApiStatus.FAILED -> {
                GuruEmptyState(text = "Gagal memuat data.") {
                    if (materialId != null) {
                        viewModel.getMateriData()
                    }
                }
            }

            ApiStatus.SUCCESS -> {
                if (materiData != null) {
                    file = ApiService.getMateriMedia(materialId!!)
                    mediaType = materiData!!.mediaType
                    isUri = false
                    judulMateri = materiData!!.title
                    descMateri = materiData!!.description
                }
                viewModel.clearStatus()
            }
        }
        MediumText(text = stringResource(R.string.judul_materi_guru))
        CustomTextField(
            value = judulMateri,
            onValueChange = { judulMateri = it },
            placeholder = R.string.judul_materi_placholder
        )
        MediumText(text = stringResource(R.string.media_pembelajaran))
        PickVideo(
            file = file,
            fileType = mediaType,
            onFileChange = { newFile, newType, isChanged ->
                file = newFile; mediaType = newType; isFileChanged = isChanged
            },
            isUri = isUri,
            onUriDetected = { isUri = it },
        )
        MediumText(text = stringResource(R.string.desc_materi))
        CustomTextField(
            value = descMateri,
            onValueChange = { descMateri = it },
            placeholder = R.string.desc_materi_placeholder,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 31.dp),
                onClick = {
                    if (judulMateri.isEmpty() || descMateri.isEmpty() || file == null) {
                        Toast.makeText(context, "Isi semua data dulu, yaa", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        var newFile: Uri? = null
                        if (isFileChanged) {
                            newFile = file as Uri
                        }
                        if (materiData == null) {
                            viewModel.addOrUpdateMateri(
                                materialId = materialId,
                                title = judulMateri,
                                description = descMateri,
                                uri = newFile,
                                mediaType = mediaType,
                                isUpdate = false,
                                contentResolver = context.contentResolver
                            )
                        } else {
                            viewModel.addOrUpdateMateri(
                                materialId = materialId,
                                title = judulMateri,
                                description = descMateri,
                                uri = newFile,
                                mediaType = mediaType,
                                isUpdate = true,
                                contentResolver = context.contentResolver
                            )
                        }
                    }

                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue, contentColor = Color.Black
                )
            ) {
                RegularText(
                    text = if (materialId == null) stringResource(id = R.string.buat_materi) else "Edit Materi",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PickVideo(
    file: Any? = null,
    fileType: String = "",
    onFileChange: (Any?, String, Boolean) -> Unit,
    isUri: Boolean = true,
    onUriDetected: (Boolean) -> Unit
) {
    val context = LocalContext.current
//    val result = remember { mutableStateOf<Any?>(null) }
    var fileName by remember { mutableStateOf("") }
    var isVideoPlaying by remember { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it != null) {
            onUriDetected(true)
            onFileChange(
                it,
                if (isImage(it, context.contentResolver)) "image" else "video", true
            )
        }
        it?.let {
            fileName = getFileName(context, it)
        }
    }
    Column(
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
                contentDescription = "Tombol upload media",
                tint = DarkBlue
            )
        }

        file?.let { file ->
            if (isUri) {
                val uri = file as Uri
                val isImage = isImage(uri, context.contentResolver)
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .clickable {
                            if (!isImage) isVideoPlaying = true else null
                        }
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isImage) {
                        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                        val bitmap =
                            inputStream?.let { stream ->
                                android.graphics.BitmapFactory.decodeStream(
                                    stream
                                )
                            }
                        bitmap?.let { bmp ->
                            Image(
                                bitmap = bmp.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(300.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    } else {

                        if (!isVideoPlaying) {
                            val retriever = MediaMetadataRetriever()
                            retriever.setDataSource(context, uri)

                            val bitmap = retriever.frameAtTime
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.8f))
                                    .clickable { isVideoPlaying = true },
                                contentAlignment = Alignment.Center
                            ) {
                                bitmap?.let {
                                    Image(
                                        bitmap = it.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.Black)
                                            .alpha(0.65f)
                                            .aspectRatio(1f),
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.play_button),
                                        contentDescription = "Play Button",
                                        tint = Color.White
                                    )
                                }
                            }
                        } else {
                            VideoPlayer(media = uri, isUri = true, appContext = context) {
                                isVideoPlaying = it
                            }
                        }
                    }
                }
                RegularText(
                    text = fileName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                val stringUri = file as String
                val isImage = fileType == "image"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (!isImage) Color.Black.copy(alpha = 0.8f) else Color.Transparent)
                        .clickable {
                            if (!isImage) {
                                isVideoPlaying = true
                                Toast
                                    .makeText(context, "Loading Player...", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isImage) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(stringUri)
                                .diskCachePolicy(CachePolicy.DISABLED)
                                .memoryCachePolicy(CachePolicy.DISABLED)
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
                    } else {
                        if (!isVideoPlaying) {
                            Log.d("AddMateriScreen", "$stringUri/thumbnail")
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("$stringUri/thumbnail")
                                    .diskCachePolicy(CachePolicy.DISABLED)
                                    .memoryCachePolicy(CachePolicy.DISABLED)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.loading_img),
                                error = painterResource(id = R.drawable.broken_image),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .aspectRatio(1f)
                                    .padding(4.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.play_button),
                                contentDescription = "Play Button",
                                tint = Color.White
                            )
                        } else {
                            VideoPlayer(media = stringUri, isUri = false, appContext = context) {
                                isVideoPlaying = it
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Prev() {
//    AddMateriScreen(navController = rememberNavController())
}