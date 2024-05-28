package org.d3ifcool.virtualab.ui.screen.murid.introduction

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueText
import org.d3ifcool.virtualab.ui.theme.LightBlue

@Composable
fun IntroductionScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.introduction_title, navController = navController)
    },
        bottomBar = {
            BottomNav(currentRoute = Screen.MuridDashboard.route, navController = navController)
        }) {
        ScreenContent(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController
        )
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                LightBlue
            )
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(),
            colors = buttonColors(containerColor = Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.video_thumbnail),
                contentDescription = "Video Thumbnail",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                MediumLargeText(
                    text = stringResource(R.string.introduction_header),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 30.dp, top = 0.dp, end = 30.dp, bottom = 24.dp),
                    color = DarkBlueText
                )
                Column(modifier = Modifier.padding(horizontal = 50.dp)) {
                    SmallText(
                        text = stringResource(R.string.introduction_desc),
                        textAlign = TextAlign.Justify,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SmallText(
                        text = stringResource(R.string.introduction_desc2_title),
                        fontWeight = FontWeight.Bold,
                    )
                    SmallText(
                        text = stringResource(R.string.introduction_desc2),
                        textAlign = TextAlign.Justify,
                        modifier = modifier.padding(bottom = 24.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun IntroScreenPrev() {
    IntroductionScreen(navController = rememberNavController())
}