package com.vlab2024.virtuallab.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlab2024.virtuallab.R

@Composable
fun LandingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome_text),
            fontSize = 20.sp,
        )
        Text(text = stringResource(R.string.virtual_lab),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp))
        Image(
            painter = painterResource(id = R.drawable.landing_image),
            contentDescription = "landing image",
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 301.dp, height = 237.dp)
        )
        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(top = 53.dp)
                .height(48.dp),
            contentPadding = PaddingValues(horizontal = 32.dp),
            onClick = { /*TODO*/ },
            colors = buttonColors(containerColor = colorResource(id = R.color.light_blue), contentColor = Color.Black)
        ) {
            Text(text = stringResource(R.string.start_text))
        }
    }
}

@Preview
@Composable
private fun LandingScreenPrev() {
    LandingScreen()
}