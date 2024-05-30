package org.d3ifcool.virtualab.ui.screen.guru.materi

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import org.d3ifcool.virtualab.ui.theme.GrayTextField
import org.d3ifcool.virtualab.ui.theme.LightBlue

@Composable
fun AddMateriScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.buat_materi, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.GuruMateri.route, navController)
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
            onValueChange = { newDescMateri -> descMateri = newDescMateri },
            placeholder = R.string.desc_materi_placeholder
        )
    }
}
@Composable
fun PickVideo(){
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }
    Row {
        Button(
            onClick = {
                launcher.launch(
                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = DarkBlue
            )
        ) {
            Icon(painter = painterResource(R.drawable.icon_upload), contentDescription = "Tombol upload media")
        }
        result.value?.let { image ->
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(GrayTextField)
                    .padding(4.dp)
            ) {
                RegularText(text = "Video Path: "+image.path.toString())
            }
        }
    }
}

@Preview
@Composable
private fun Prev() {
    AddMateriScreen(navController = rememberNavController())
}