package org.d3ifcool.virtualab.ui.screen.guru.artikel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.CustomTextField
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.ImageDialog
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.LoadingStateDialog
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.getFileName
import org.d3ifcool.virtualab.utils.isImage
import java.io.InputStream

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddContohReaksi(navController: NavHostController, viewModel: AddContohReaksiViewModel) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val status by viewModel.apiStatus.collectAsState()
    val isUploading by viewModel.uploadStatus.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val sheetStateLihat = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sheetStateLihat.currentValue) {
        snapshotFlow { sheetStateLihat.currentValue }
            .collect { bottomSheetValue ->
                if (bottomSheetValue == ModalBottomSheetValue.Hidden) {
                    isPressed = false
                }
            }
        }


    LaunchedEffect(isPressed) {
        if (isPressed) {
            scope.launch {
                sheetStateLihat.show()
            }
        } else {
            scope.launch {
                sheetStateLihat.hide()
            }
        }
    }

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
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.GuruContohReaksi.route) {
                popUpTo(Screen.GuruDashboard.route)
            }
            viewModel.clearStatus()
        }
    }

    Scaffold(
        topBar = {
            TopNav(
                title = if (status != ApiStatus.IDLE) R.string.edit_contoh_reaksi else R.string.buat_contoh_reaksi,
                navController = navController
            )
        },
        floatingActionButton = {
            if (isPressed) {
                FloatingActionButton(
                    containerColor = DarkBlueDarker,
                    onClick = {
                        navController.navigate(Screen.AddMateri.route)
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.menu_buat_button),
                        tint = Color.White
                    )
                }
            }
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), viewModel, status, context, focusManager)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: AddContohReaksiViewModel,
    status: ApiStatus,
    context: Context,
    focusManager: FocusManager
) {
    val articleData by viewModel.articleData.collectAsState()
    val articleId by viewModel.articleId.collectAsState()

    var judulKonten by remember { mutableStateOf("") }
    var descKonten by remember { mutableStateOf("") }
    var isFileExist by remember { mutableStateOf(false) }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
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
                    if (articleId != null) {
                        viewModel.getArticleData()
                    }
                }
            }

            ApiStatus.SUCCESS -> {
                if (articleData != null) {
                    judulKonten = articleData!!.title
                    descKonten = articleData!!.description
                    isFileExist = true
                }
                viewModel.clearStatus()
            }
        }
        MediumText(text = stringResource(R.string.judul_konten_contoh_reaksi))
        CustomTextField(
            value = judulKonten,
            onValueChange = { judulKonten = it },
            placeholder = R.string.judul_konten_placeholder
        )
        MediumText(text = stringResource(R.string.media_konten_contoh_reaksi))
        PickImage(
            isOldFileExist = isFileExist,
            onNewImage = { bmp, isOldFileChanged ->
                bitmap = bmp; isFileExist = isOldFileChanged
            },
            articleId = articleId
        )
        MediumText(text = stringResource(R.string.desc_konten_contoh_materi))
        CustomTextField(
            value = descKonten,
            onValueChange = { descKonten = it },
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
                    if (judulKonten.isEmpty() || descKonten.isEmpty() || bitmap == null && !isFileExist) {
                        Toast.makeText(context, "Isi semua data dulu, yaa", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (articleData == null) {
                            viewModel.addOrUpdateArticle(
                                articleId = articleId,
                                title = judulKonten,
                                description = descKonten,
                                content = bitmap,
                                isUpdate = false,
                            )
                        } else {
                            viewModel.addOrUpdateArticle(
                                articleId = articleId,
                                title = judulKonten,
                                description = descKonten,
                                content = bitmap,
                                isUpdate = true,
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
                    text = if (articleId == null) stringResource(id = R.string.buat_contoh_reaksi) else "Edit Konten",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PickImage(
    isOldFileExist: Boolean,
    articleId: Int? = null,
    onNewImage: (Bitmap?, Boolean) -> Unit
) {
    val context = LocalContext.current
    var fileName by remember { mutableStateOf("") }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    var showImgDialog by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it != null) {
            if (isImage(it, context.contentResolver)) {
                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                bitmap = inputStream?.let { stream ->
                    BitmapFactory.decodeStream(
                        stream
                    )
                }
                onNewImage(bitmap, false)
            } else {
                Toast.makeText(
                    context,
                    "Media yang diunggah harus bertipe gambar, yaa",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
        if (!isOldFileExist && bitmap != null) {
            bitmap?.let {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            showImgDialog = true
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .size(300.dp)
                    )
                }
                if (showImgDialog) {
                    ImageDialog(bitmap = it, context = context) {
                        showImgDialog = false
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
        } else if (isOldFileExist) {
            val imageUrl = ApiService.getArticleMedia(articleId!!)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .clickable {
                        showImgDialog = true
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUrl)
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
            }
            if (showImgDialog) {
                ImageDialog(imageUrl = imageUrl, context = context) {
                    showImgDialog = false
                }
            }
        }
    }
}
