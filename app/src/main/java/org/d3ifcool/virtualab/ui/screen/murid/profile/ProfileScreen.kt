package org.d3ifcool.virtualab.ui.screen.murid.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.ui.component.BottomNav

@Composable
fun ProfileScreen(navController: NavHostController, route: String? = "") {
    val currentRoute by remember { mutableStateOf(route!!) }
    Scaffold(bottomBar = {
        BottomNav(currentRoute, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}


@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.profile_header))
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.profile_big),
            contentDescription = "Profile Picture"
        )
    }


}

@Preview
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(rememberNavController())
}