package org.d3ifcool.virtualab.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    scaffoldSheetState: BottomSheetScaffoldState,
    title: Int,
    action1: Int,
    onClickAct1: () -> Unit,
    action2: Int,
    onClickAct2: () -> Unit
) {
    BottomSheetScaffold(
        modifier = Modifier.zIndex(99f),
        scaffoldState = scaffoldSheetState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            ModalContent(
                title, action1, { onClickAct1() }, action2, { onClickAct2() }
            )
        },
        sheetContainerColor = Color.White,
        sheetDragHandle = {
            Icon(
                painter = painterResource(id = R.drawable.draggable_line),
                contentDescription = "Draggable Handle",
                tint = Color.Black,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }) {
    }
}


@Composable
private fun ModalContent(
    title: Int,
    action1: Int,
    onClickAct1: () -> Unit,
    action2: Int,
    onClickAct2: () -> Unit
) {
    Column(
        modifier = Modifier
            .alpha(1f)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .size(301.dp, 396.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MediumText(
            text = stringResource(title),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 34.dp),
        )
        Button(
            onClick = { onClickAct1() },
            contentPadding = PaddingValues(horizontal = 52.dp, vertical = 18.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(DarkBlueDarker)
        ) {
            RegularText(
                text = stringResource(action1),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        Button(
            onClick = { onClickAct2() },
            contentPadding = PaddingValues(horizontal = 52.dp, vertical = 18.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(LightBlue)
        ) {
            RegularText(
                text = stringResource(action2),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}
