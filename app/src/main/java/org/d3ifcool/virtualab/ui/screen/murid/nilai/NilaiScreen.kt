package org.d3ifcool.virtualab.ui.screen.murid.nilai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.GradientPage
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
    GradientPage(modifier, image = R.drawable.nilai_illustration) {
        Text(
            text = "Nilai dari latihan yang telah kamu kerjakan:",
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
            fontSize = 16.sp
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            CardList()
            CardList()
            CardList()
            CardList()
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
            Text(
                text = stringResource(R.string.latihan_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.tingkat_kesulitan), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.nilai), fontSize = 14.sp)
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