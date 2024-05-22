package org.d3ifcool.virtualab.ui.screen.murid.materi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.GradientPage
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.GrayTitle
import org.d3ifcool.virtualab.ui.theme.LightBlue


@Composable
fun MuridMateriScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.materi_title, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.MuridDashboard.route, navController = navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(
            modifier = Modifier.padding(it.calculateBottomPadding()),
            navController = navController
        )
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    GradientPage(
        modifier = Modifier.padding(top = 65.dp),
        isCenter = false,
        image = R.drawable.materi_header
    ) {
        Text(
            text = "Materi Ajar",
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            CardList {
                navController.navigate(Screen.MuridDetailMateri.route)
            }
            CardList {
            }
            CardList {
            }
            CardList {
            }
            CardList {
            }
            CardList {
            }
        }
    }
}

@Composable
private fun CardList(onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(LightBlue)
            .padding(vertical = 16.dp, horizontal = 20.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "01", fontSize = 32.sp, color = GrayTitle)
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(text = "Materi 1", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Materi ini berisi pembelajaran tentang Materi ini berisi pembelajaran tentang ",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 16.sp,
                modifier = Modifier.width(280.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
private fun MateriScreenPrev() {
    MuridMateriScreen(navController = rememberNavController())
}