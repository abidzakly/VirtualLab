package org.d3ifcool.virtualab.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav

@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopNav(title = R.string.about_button, navController = navController) },
        bottomBar = {
            BottomNav(
                currentRoute = Screen.MuridDashboard.route,
                navController = navController
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 40.dp)
        ) {
            Spacer(Modifier.height(44.dp))
            RegularText(
                text = stringResource(R.string.about_app),
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier = Modifier.height(48.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.telyu_logo),
                        contentDescription = "Logo Telkom University",
                        modifier = Modifier.size(73.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RegularText(
                        text = stringResource(R.string.universitas_telkom_title),
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.mgmp_logo),
                        contentDescription = "Logo MGMP",
                        modifier = Modifier.size(73.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RegularText(
                        text = stringResource(R.string.mgmp_title),
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AboutScreenPrev() {
    AboutScreen(rememberNavController())
}