package org.d3ifcool.virtualab.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins

@Composable
fun ExtraLargeText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle? = null
) {
    Text(
        fontStyle = fontStyle,
        text = text,
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun LargeText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle? = null
) {
    Text(
        fontStyle = fontStyle,
        text = text,
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun SemiLargeText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle? = null
) {
    Text(
        fontStyle = fontStyle,
        text = text,
        fontSize = 22.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun MediumLargeText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle? = null
) {
    Text(
        fontStyle = fontStyle,
        text = text,
        fontSize = 20.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun MediumText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle? = null
) {
    Text(
        fontStyle = fontStyle,
        text = text,
        fontSize = 18.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun RegularText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true
) {
    Text(
        softWrap = true,
        lineHeight = lineHeight,
        fontStyle = fontStyle,
        text = text,
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun SmallText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle? = null
) {
    Text(
        fontStyle = fontStyle,
        text = text,
        fontSize = 14.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun ExtraSmallText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip

) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = fontWeight,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}
