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
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
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
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
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
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
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
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
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
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
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
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        color = color,
        textAlign = align
    )
}

@Composable
fun SmallText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        fontWeight = fontWeight,
        text = text,
        fontSize = 14.sp,
        fontFamily = Poppins,
        color = color,
        textAlign = align
    )
}

@Composable
fun ExtraSmallText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    align: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        color = color,
        textAlign = align
    )
}