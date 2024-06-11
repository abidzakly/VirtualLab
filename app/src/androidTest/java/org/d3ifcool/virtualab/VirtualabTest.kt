package org.d3ifcool.virtualab

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.d3ifcool.virtualab.ui.screen.landing.LandingScreen
import org.d3ifcool.virtualab.ui.theme.VirtualLabTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class VirtualabTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testUI() {

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        rule.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            VirtualLabTheme {
                LandingScreen(navController = navController)
            }
        }

        rule.onNodeWithText("Selamat\nDatang\ndi Virtual Lab").assertExists()
        rule.onNodeWithText("Buat Akun").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Pilih Peran").assertExists()
        rule.onNodeWithText("Murid").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Daftar Akun").assertExists()

        rule.onNodeWithText("Nama Lengkap").performTextInput("Amru Abid")
        rule.onNodeWithText("Nama Pengguna (Username)").performTextInput("amruabid1")
        rule.onNodeWithText("Email (Email)").performTextInput("amruabid@gmail.com")
        rule.onNodeWithText("NISN").performTextInput("0041288417")
        rule.onNodeWithText("Nama Sekolah").performTextInput("SMAN 1 Bandung")

        rule.onNodeWithContentDescription("Check box register").performClick()

        rule.onNodeWithText("Daftar dan Verifikasi").performClick()

    }

}