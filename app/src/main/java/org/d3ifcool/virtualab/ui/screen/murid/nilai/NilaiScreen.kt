package org.d3ifcool.virtualab.ui.screen.murid.nilai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.GradientPage
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav

@Composable
fun NilaiScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.nilai_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.Nilai.route, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(bottom = it.calculateBottomPadding()))
    }
}


@Composable
private fun ScreenContent(modifier: Modifier) {
    var isEmpty by remember { mutableStateOf(true) }

    GradientPage(modifier, image = R.drawable.nilai_illustration) {
        if (!isEmpty) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                RegularText(text = "Nilai dari latihan yang telah kamu kerjakan:")
                Spacer(modifier = Modifier.height(16.dp))
                CardList()
                CardList()
                CardList()
                CardList()
            }
        } else {
            MuridEmptyState(text = stringResource(id = R.string.empty_nilai))
        }
//            LazyColumn {
//                items() {
//                    CardList()
//                }
//            }
    }
}

@Composable
private fun CardList() {
    Row(
        Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            RegularText(
                text = stringResource(id = R.string.latihan_title),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            SmallText(text = stringResource(id = R.string.tingkat_kesulitan))
            Spacer(modifier = Modifier.height(8.dp))
            SmallText(text = stringResource(id = R.string.nilai))
            Spacer(modifier = Modifier.height(8.dp))

        }
        Image(
            painter = painterResource(id = R.drawable.gold_award),
            contentDescription = "Award",
            modifier = Modifier.size(57.dp, 81.2.dp),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun NilaiScrenPrev() {
    NilaiScreen(rememberNavController())
}