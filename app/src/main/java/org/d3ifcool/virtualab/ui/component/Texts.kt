package org.d3ifcool.virtualab.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.d3ifcool.virtualab.ui.theme.Poppins

@Composable
fun ExtraLargeText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}

@Composable
fun LargeText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}

@Composable
fun SemiLargeText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 22.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}

@Composable
fun MediumLargeText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}

@Composable
fun MediumText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}

@Composable
fun RegularText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}

@Composable
fun SmallText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}

@Composable
fun ExtraSmallText(
    text: String,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = align
    )
}