package org.d3ifcool.virtualab.ui.screen.guru.materi

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.LightBlue
import java.io.InputStream

@Composable
fun AddMateriScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.buat_materi, navController = navController)
        },
        bottomBar = {
            BottomNav(navController = navController)
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
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RegularText(text = stringResource(R.string.judul_materi_guru))
        CustomTextField(
            value = judulMateri,
            onValueChange = { newJudulMateri -> judulMateri = newJudulMateri },
            placeholder = R.string.judul_materi_placholder
        )
        RegularText(text = stringResource(R.string.media_pembelajaran))
        PickVideo()
        RegularText(text = stringResource(R.string.desc_materi))
        CustomTextField(
            value = descMateri,
            onValueChange = { newDesMateri -> descMateri = newDesMateri },
            placeholder = R.string.desc_materi_placeholder,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 31.dp),
                onClick = { },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue, contentColor = Color.Black
                )
            ) {
                RegularText(
                    text = stringResource(id = R.string.buat_materi),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
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
        result.value?.let {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                val bitmap =
                    inputStream?.let { stream -> android.graphics.BitmapFactory.decodeStream(stream) }
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
    AddMateriScreen(navController = rememberNavController())
}