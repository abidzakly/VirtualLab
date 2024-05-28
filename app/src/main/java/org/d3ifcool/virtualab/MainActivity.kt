package org.d3ifcool.virtualab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.navigation.SetupNavGraph
import org.d3ifcool.virtualab.ui.screen.guru.dashboard.GuruDashboardScreen
import org.d3ifcool.virtualab.ui.theme.VirtualLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SetupNavGraph()
        }
    }
}