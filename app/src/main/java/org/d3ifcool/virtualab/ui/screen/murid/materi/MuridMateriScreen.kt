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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
import org.d3ifcool.virtualab.ui.component.ExtraLargeText
import org.d3ifcool.virtualab.ui.component.GradientPage
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
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
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController
        )
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    var isEmpty by remember { mutableStateOf(true) }

    GradientPage(
        modifier = modifier,
        isCenter = false,
        image = R.drawable.materi_header
    ) {
        if (!isEmpty) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                MediumLargeText(
                    text = "Materi Ajar",
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontWeight = FontWeight.Medium
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
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
        } else {
            MuridEmptyState(text = stringResource(id = R.string.empty_materi))
        }
    }
}

@Composable
private fun CardList(onClick: () -> Unit) {
    Row(
        Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(LightBlue)
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ExtraLargeText(text = "01", color = GrayTitle)
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            MediumLargeText(text = "Materi 1", fontWeight = FontWeight.SemiBold)
            RegularText(
                text = "Materi ini berisi pembelajaran tentang Materi ini berisi pembelajaran tentang ",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
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