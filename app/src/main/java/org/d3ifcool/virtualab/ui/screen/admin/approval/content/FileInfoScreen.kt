package org.d3ifcool.virtualab.ui.screen.admin.approval.content

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
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
fun FileInfoScreen(navController: NavHostController) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopNav(title = R.string.category_check_file, navController = navController)
        }, bottomBar = {
            BottomNav(navController = navController)
        }) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InfoAuthor(nip = "199202262017051001", username = "suyanto")
        RegularText(
            text = stringResource(R.string.judul_materi_guru),
            fontWeight = FontWeight.SemiBold
        )
        RegularText(
            text = stringResource(R.string.judul_materi_data),
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
            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(R.drawable.media_example),
                contentDescription = "File Media Pembelajaran"
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            RegularText(
                text = stringResource(R.string.file_media_belajar),
                fontWeight = FontWeight.Normal
            )
        }
        RegularText(
            text = stringResource(R.string.deskripsi_text),
            fontWeight = FontWeight.SemiBold
        )
        RegularText(
            text = stringResource(R.string.guru_deskripsi_materi_data),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Justify
        )
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    Toast.makeText(context, R.string.accept_file_toast, Toast.LENGTH_LONG).show()
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
                RegularText(text = stringResource(id = R.string.button_tolak), color = Color.White)
            }
            if (showDialog) {
                RejectFilePopup(onDismiss = { showDialog = false })
            }
        }
    }
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
private fun RejectFilePopup(onDismiss: () -> Unit) {
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
                onClick = { onDismiss() },
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
    FileInfoScreen(rememberNavController())
}