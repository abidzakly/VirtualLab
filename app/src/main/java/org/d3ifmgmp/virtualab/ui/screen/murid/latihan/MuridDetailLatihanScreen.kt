package org.d3ifmgmp.virtualab.ui.screen.murid.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifmgmp.virtualab.R
import org.d3ifmgmp.virtualab.navigation.Screen
import org.d3ifmgmp.virtualab.ui.component.BottomNav
import org.d3ifmgmp.virtualab.ui.component.RegularText
import org.d3ifmgmp.virtualab.ui.component.SmallText
import org.d3ifmgmp.virtualab.ui.component.TopNav
import org.d3ifmgmp.virtualab.ui.theme.DarkBlueDarker
import org.d3ifmgmp.virtualab.ui.theme.LightBlue
import org.d3ifmgmp.virtualab.ui.theme.LightBlueDarker

@Composable
fun MuridDetailLatihanScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.latihan_x, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.MuridDashboard.route, navController = navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = stringResource(R.string.kerjakan_latihan_header), fontSize = 16.sp)
        Column(
            modifier = Modifier
                .padding(top = 37.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemList(noSoal = "1")
            ItemList(noSoal = "2")
            ItemList(noSoal = "3")
            ItemList(noSoal = "4")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = buttonColors(DarkBlueDarker),
                contentPadding = PaddingValues(vertical = 9.dp, horizontal = 47.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                RegularText(text = "Cek", fontWeight = FontWeight.SemiBold, color = Color.White)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ItemList(noSoal: String) {
    Column {
        Text(text = "Soal $noSoal", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(LightBlue)
                .clip(shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(vertical = 22.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "C4H10 + O2 = ")
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(5.dp))
                            .size(23.dp)
                            .background(Color(0xFF5BC5DD)),
                        contentAlignment = Alignment.Center
                    ) {
                        RegularText(text = "2", fontWeight = FontWeight.Bold)
                    }
                    Text(text = " CO2 + O ")
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(5.dp))
                            .size(23.dp)
                            .background(Color(0xFF4AADFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        RegularText(text = "3", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(21.dp))
                SmallText(
                    text = "Pilihan Jawaban: ",
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AnswerBox("2")
                    AnswerBox("3")
                    AnswerBox("4")
                    AnswerBox("5")
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}


@Composable
fun AnswerBox(text: String) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .size(35.dp)
            .background(LightBlueDarker),
        contentAlignment = Alignment.Center
    ) {
        RegularText(text = text, fontWeight = FontWeight.Normal)
    }
}

@Preview
@Composable
private fun Prev() {
    MuridDetailLatihanScreen(rememberNavController())
}