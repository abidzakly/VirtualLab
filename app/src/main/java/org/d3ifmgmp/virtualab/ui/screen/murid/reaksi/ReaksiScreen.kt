package org.d3ifmgmp.virtualab.ui.screen.murid.reaksi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.d3ifmgmp.virtualab.ui.component.GradientPage
import org.d3ifmgmp.virtualab.ui.component.MediumLargeText
import org.d3ifmgmp.virtualab.ui.component.MuridEmptyState
import org.d3ifmgmp.virtualab.ui.component.RegularText
import org.d3ifmgmp.virtualab.ui.component.SmallText
import org.d3ifmgmp.virtualab.ui.component.TopNav
import org.d3ifmgmp.virtualab.ui.theme.LightBlue2

@Composable
fun ReaksiScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.contoh_reaksi_title, navController = navController)
    },
        bottomBar = {
            BottomNav(currentRoute = Screen.MuridDashboard.route, navController = navController)
        }) {
        ScreenContent(modifier = Modifier.padding(bottom = it.calculateBottomPadding()))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    var isEmpty by remember { mutableStateOf(true) }

    GradientPage(
        modifier = modifier,
        image = R.drawable.reaksi_illustration,
        isDiffSize = true,
    ) {
        if (!isEmpty) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(22.dp))
                RegularText(text = stringResource(R.string.reaksi_content_header))
                Spacer(modifier = Modifier.height(25.dp))
                ItemList()
                ItemList()
            }
        } else {
            MuridEmptyState(text = stringResource(id = R.string.empty_reaksi))
        }
    }
}

@Composable
private fun ItemList() {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(LightBlue2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemContent()
    }
    Spacer(modifier = Modifier.height(33.dp))
}

@Composable
private fun ItemContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediumLargeText(text = "Reaksi Fotosintesis", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.fotosintesis_illustration),
            contentDescription = "Gambar Reaksi"
        )
        SmallText(
            text = "Reaksi kimia fotosintesis merupakan salah satu reaksi " +
                    "kimia sehari-hari yang paling umum karena inilah cara tumbuhan " +
                    "menghasilkan makanan untuk mereka sendiri serta mengubah karbon " +
                    "dioksida dan air menjadi oksigen dan glukosa.",
            textAlign = TextAlign.Justify,
        )
    }
}

@Preview
@Composable
private fun Prev() {
    ReaksiScreen(navController = rememberNavController())
}
