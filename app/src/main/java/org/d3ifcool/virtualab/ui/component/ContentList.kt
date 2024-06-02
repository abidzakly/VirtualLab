package org.d3ifcool.virtualab.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.ui.theme.LightBlue


@Composable
fun ContentList(title: String, desc: String, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth(),
        colors = cardColors(containerColor = LightBlue),
        elevation = cardElevation(6.dp),
        onClick = { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(218.dp)) {
                MediumText(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                RegularText(
                    text = desc,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_circle_outline_28),
                contentDescription = "Detail Button",
                tint = Color.Black
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun Prev() {
    ContentList(title = "Test", desc = "test") {

    }
}