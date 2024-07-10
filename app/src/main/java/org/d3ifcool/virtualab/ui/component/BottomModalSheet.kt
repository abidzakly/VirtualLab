package org.d3ifcool.virtualab.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.ui.theme.LightBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomModalSheet(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState, title: Int,
    action1: Int,
    onClickAct1: () -> Unit,
    action2: Int,
    onClickAct2: () -> Unit,
    action3: Int,
    onClickAct3: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetBackgroundColor = Color.White,
        sheetState = sheetState,
        sheetContent = {
            ModalContent(
                title = title,
                action1 = action1,
                onClickAct1 = { onClickAct1() },
                action2 = action2,
                onClickAct2 = { onClickAct2() },
                action3 = action3,
                onClickAct3 = { onClickAct3() }
            )
        }
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalContent(
    title: Int,
    action1: Int,
    onClickAct1: () -> Unit,
    action2: Int,
    onClickAct2: () -> Unit,
    action3: Int,
    onClickAct3: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .size(701.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DragHandle(color = Color.Black, modifier = Modifier.fillMaxWidth(.25f))
        Spacer(modifier = Modifier.height(16.dp))
        MediumText(
            text = stringResource(title),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 34.dp),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ButtonBottomSheet(
                action = action1,
                onClickAct = onClickAct1,
                image = R.drawable.latihan_icon,
                imageDesc = "Ikon latihan"
            )
            ButtonBottomSheet(
                action = action2,
                onClickAct = onClickAct2,
                image = R.drawable.materi_icon,
                imageDesc = "Ikon latihan"
            )
            ButtonBottomSheet(
                action = action3,
                onClickAct = onClickAct3,
                image = R.drawable.contoh_reaksi_icon,
                imageDesc = "Ikon contoh reaksi"
            )
        }
    }
}

@Composable
private fun ButtonBottomSheet(action: Int, onClickAct: () -> Unit, image: Int, imageDesc: String) {
    Button(
        onClick = { onClickAct() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(LightBlue),
        modifier = Modifier.fillMaxHeight(.3f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(image),
                contentDescription = imageDesc,
                tint = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            RegularText(
                text = stringResource(action),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}