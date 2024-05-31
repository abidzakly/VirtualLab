package org.d3ifcool.virtualab.ui.screen.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.DarkBlueText
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins

@Composable
fun LandingScreen(navController: NavHostController) {
    Scaffold(containerColor = Color.White) {
        ScreenContent(modifier = Modifier.padding(it), navController = navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.welcome_text),
            fontSize = 32.sp,
            fontFamily = Poppins,
            color = DarkBlueText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 48.dp, end = 48.dp, bottom = 60.dp),
            lineHeight = 42.sp
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.landing_image),
                contentDescription = "landing image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 301.dp, height = 237.dp)
            )
            Text(
                text = stringResource(id = R.string.slogan),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 32.dp, bottom = 50.dp),
                color = DarkBlueText
            )
            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier.size(width = 300.dp, height = 47.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(id = R.string.signin), fontWeight = FontWeight.SemiBold, fontFamily = Poppins, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.padding(15.dp))
            Button(
                onClick = { navController.navigate(Screen.Role.route) },
                modifier = Modifier.size(width = 300.dp, height = 47.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.create_acc),
                    fontWeight = FontWeight.SemiBold, fontFamily = Poppins, fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun LandingScreenPrev() {
    LandingScreen(rememberNavController())
}