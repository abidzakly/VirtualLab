package org.d3ifcool.virtualab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.d3ifcool.virtualab.navigation.SetupNavGraph
import org.d3ifcool.virtualab.ui.screen.guru.artikel.TesstSrrn

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SetupNavGraph()
//            TesstSrrn()
        }
    }

    override fun onResume() {
        super.onResume()

    }
}
