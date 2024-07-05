package org.d3ifcool.virtualab.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker

@Composable
fun MuridEmptyState(text: String, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_state_murid),
            contentDescription = "Gambar Empty State"
        )
        Spacer(modifier = Modifier.height(28.dp))
        RegularText(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onClick() }, colors = ButtonDefaults.buttonColors(containerColor = DarkBlueDarker)) {
            SmallText(text = "Muat Ulang", color = Color.White)
        }
    }
}

@Composable
fun GuruEmptyState(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_state_guru),
            contentDescription = "Gambar Empty State"
        )
        Spacer(modifier = Modifier.height(28.dp))
        RegularText(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onClick() }, colors = ButtonDefaults.buttonColors(containerColor = DarkBlueDarker)) {
            SmallText(text = "Muat Ulang", color = Color.White)
        }
    }
}
@Composable
fun AdminEmptyState(text: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_state_admin),
            contentDescription = "Gambar Empty State"
        )
        Spacer(modifier = Modifier.height(28.dp))
        RegularText(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Prev() {
    GuruEmptyState(text = "Belum ada latihan dikerjakan") {

    }
}