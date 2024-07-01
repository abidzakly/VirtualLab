package org.d3ifcool.virtualab.ui.screen.admin.introduction

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.CustomTextField
import org.d3ifcool.virtualab.ui.component.RegularText
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateIntroContentScreen(navController: NavHostController) {
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
                    Text(text = stringResource(id = R.string.introduction_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.UpdateIntroContent.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.save_button),
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.AdminDashboard.route, navController = navController)
        }
    ) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}
@Composable
private fun ScreenContent(modifier: Modifier) {
    var judulMateri by remember { mutableStateOf("") }
    var descMateri by remember { mutableStateOf("") }

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
        PickVideo()
        RegularText(text = stringResource(R.string.judul_pengenalan))
        CustomTextField(
            value = judulMateri,
            onValueChange = { newJudulMateri -> judulMateri = newJudulMateri },
            placeholder = R.string.judul_materi_placholder
        )
        RegularText(text = stringResource(R.string.desc_materi))
        CustomTextField(
            value = descMateri,
            onValueChange = { newDesMateri -> descMateri = newDesMateri },
            placeholder = R.string.desc_materi_placeholder,
            modifier = Modifier.fillMaxSize()
        )
    }
}
@Composable
fun PickVideo() {
    val context = LocalContext.current
    val result = remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
        it?.let {
            fileName = getFileName(context, it)
        }
    }
    result.value?.let {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            val bitmap =
                inputStream?.let { stream -> BitmapFactory.decodeStream(stream) }
            bitmap?.let { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
            }
            RegularText(
                text = fileName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                contentDescription = "Tombol edit media",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.DarkGray
            )
        ) {
            Icon(
                imageVector = Icons.Default.Delete ,
                contentDescription = "Tombol Delete Media",
                tint = Color.Black
            )
        }
    }
}
fun getFileName(context: Context, uri: Uri): String {
    var fileName = ""
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

        if (cursor.moveToFirst()) {
            fileName = cursor.getString(nameIndex)
        }
    }
    return fileName
}
@Preview
@Composable
private fun Prev() {
    UpdateIntroContentScreen(navController = rememberNavController())
}