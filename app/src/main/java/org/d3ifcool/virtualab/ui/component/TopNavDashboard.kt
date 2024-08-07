package org.d3ifcool.virtualab.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen

@Composable
fun TopNavDashboard(name: String, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(start = 25.dp, end = 25.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.vlab_logo_mini),
                contentDescription = "Virtual Lab logo Mini"
            )
            Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                RegularText(
                    text = "Halo,\n$name!",
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        IconButton(onClick = { navController.navigate(Screen.About.route) }, modifier = Modifier.padding(top = 8.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_about_24),
                contentDescription = stringResource(R.string.about_button),
                tint = Color.Black
            )
        }
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Prev() {
    TopNavDashboard("Abid", navController = rememberNavController())
}