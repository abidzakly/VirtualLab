package org.d3ifcool.virtualab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.d3ifcool.virtualab.navigation.SetupNavGraph
import org.d3ifcool.virtualab.ui.screen.guru.artikel.TesstSrrn

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
//            var text by remember { mutableStateOf("") }
//            val focusManager = LocalFocusManager.current
//            Column(modifier = Modifier.fillMaxSize().pointerInput(Unit){
//                detectTapGestures(
//                    onTap = { focusManager.clearFocus() }
//                )
//            }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//                Box(modifier = Modifier.fillMaxSize()
//                    , contentAlignment = Alignment.Center) {
//                    TextField(
//                        modifier = Modifier,
//                        value = text, onValueChange = { text = it })
//                }
//            }
            SetupNavGraph()
//            TesstSrrn()
        }
    }

    override fun onResume() {
        super.onResume()

    }
}
