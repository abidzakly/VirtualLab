package org.d3ifcool.virtualab.ui.screen.terms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav

@Composable
fun TermsConditionScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.kosong, navController = navController)
        }
    ) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LargeText(
                modifier = Modifier.absolutePadding(left = 43.dp),
                text = stringResource(R.string.terms),
                fontWeight = FontWeight.Bold
            )
            LargeText(
                modifier = Modifier.absolutePadding(left = 6.dp),
                text = stringResource(R.string.conditions),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0E4B9C)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        SmallText(
            text = stringResource(R.string.header),
            textAlign = TextAlign.Justify,
        )

        Spacer(modifier = Modifier.height(20.dp))
        SmallText(
            text = stringResource(R.string.terms_1),
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(10.dp))
        SmallText(
            text = stringResource(R.string.terms_penjelasan_1),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(20.dp))
        SmallText(
            text = stringResource(R.string.terms_2),
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(10.dp))
        SmallText(
            text = stringResource(R.string.terms_penjelasan_2),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(20.dp))
        SmallText(
            text = stringResource(R.string.terms_3),
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(10.dp))
        SmallText(
            text = stringResource(R.string.terms_penjelasan_3),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(20.dp))
        SmallText(
            text = stringResource(R.string.terms_4),
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(10.dp))
        SmallText(
            text = stringResource(R.string.terms_penjelasan_4),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(30.dp))
        SmallText(
            text = stringResource(R.string.footer),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview
@Composable
private fun Prev() {
    TermsConditionScreen(navController = rememberNavController())
}