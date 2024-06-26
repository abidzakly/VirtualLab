package org.d3ifcool.virtualab.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(
    title: Int,
    navController: NavHostController,
    actions: @Composable() (RowScope.() -> Unit) = {},
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        id = R.string.back_button
                    ),
                    tint = Color.Black
                )
            }
        },
        title = {
            Text(text = stringResource(id = title))
        },
        colors = topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Black
        ),
        actions = actions,
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun TopNavPrev() {
    TopNav(R.string.register_title, rememberNavController())
}