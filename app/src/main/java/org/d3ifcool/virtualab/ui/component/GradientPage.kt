package org.d3ifcool.virtualab.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.ui.theme.BlueGradient
import org.d3ifcool.virtualab.ui.theme.LightBlueGradient

@Composable
fun GradientPage(modifier: Modifier, emptyText: String = "", image: Int, isCenter: Boolean? = true, isDiffSize: Boolean = false, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BlueGradient, LightBlueGradient)
                )
            ),
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 65.dp)
                .padding(horizontal = if (!isDiffSize) 60.dp else 50.dp)
        )
        Column(
            modifier = modifier
                .padding(top = 270.dp)
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(horizontal = 28.dp)
            ,

            horizontalAlignment = if (isCenter!!) Alignment.CenterHorizontally else Alignment.Start,
        ) {
                content()
        }
    }
}


@Preview
@Composable
private fun Preview() {
    GradientPage(modifier = Modifier.padding(bottom = 31.dp), image = R.drawable.materi_header) {

    }
}