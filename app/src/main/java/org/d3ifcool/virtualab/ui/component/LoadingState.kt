package org.d3ifcool.virtualab.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = DarkBlueDarker)
    }
}

@Composable
fun LoadingStateDialog(modifier: Modifier = Modifier) {
    Dialog(onDismissRequest = {  }) {
        LoadingState()
    }
}