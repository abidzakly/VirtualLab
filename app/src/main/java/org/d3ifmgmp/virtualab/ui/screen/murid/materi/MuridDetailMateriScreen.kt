package org.d3ifmgmp.virtualab.ui.screen.murid.materi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifmgmp.virtualab.R
import org.d3ifmgmp.virtualab.navigation.Screen
import org.d3ifmgmp.virtualab.ui.component.BottomNav
import org.d3ifmgmp.virtualab.ui.component.LargeText
import org.d3ifmgmp.virtualab.ui.component.RegularText
import org.d3ifmgmp.virtualab.ui.component.TopNav
import org.d3ifmgmp.virtualab.ui.theme.LightBlue2

@Composable
fun MuridDetailMateriScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.empty_title, navController = navController)
    },
        bottomBar = {
            BottomNav(currentRoute = Screen.MuridDashboard.route, navController = navController)
        }) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 21.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(LightBlue2)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LargeText(
            text = "Materi 01",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 23.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Image(
            painter = painterResource(id = R.drawable.rumus_kimia),
            contentDescription = "Rumus Kimia",
            modifier = Modifier.size(271.dp, 18.dp)
        )
        Spacer(modifier = Modifier.height(36.dp))
        DetailContent(content = "Molekul CH4", image = R.drawable.gambar_molekul)
        DetailContent(content = "Molekul O2", image = R.drawable.gambar_molekul)
        DetailContent(content = "Molekul CO2", image = R.drawable.gambar_molekul)

        RegularText(
            text = "Reaksi pada materi 01 ini terjadi ketika zat " +
                    "x bertemu dengan zat y yang akan menghasilkan zat " +
                    "AB yang biasanya digunakan untuk membantu perbaikan tubuh.",
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 23.dp)
        )

    }
}

@Composable
private fun DetailContent(content: String, image: Int) {
    RegularText(
        text = stringResource(id = R.string.detail_materi_sub_header),
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 22.dp)
    )
    Spacer(modifier = Modifier.height(18.dp))
    RegularText(
        text = content,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 48.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    Image(
        painter = painterResource(id = image),
        contentDescription = "Gambar Molekul",
        modifier = Modifier.size(60.dp)
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Preview
@Composable
private fun Prev() {
    MuridDetailMateriScreen(rememberNavController())
}