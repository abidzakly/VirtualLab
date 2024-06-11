package org.d3ifcool.virtualab

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.d3ifcool.virtualab.navigation.Screen.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class VirtualabTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testUIRegister() {

        rule.onNodeWithText("Selamat\nDatang\ndi Virtual Lab").assertExists()
        rule.onNodeWithText("Buat Akun").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Silakan pilih peran Anda untuk memulai:").assertExists()
        rule.onNodeWithText("Murid").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Daftar Akun").assertExists()

        rule.onNodeWithText("Nama Lengkap").performTextInput("Amru Abid")
        rule.onNodeWithText("Nama Pengguna").performTextInput("amruabid1")
        rule.onNodeWithText("Alamat Surel").performTextInput("amruabid@gmail.com")
        rule.onNodeWithText("Nomor Induk Siswa Nasional (NISN)").performTextInput("0041288417")
        rule.onNodeWithText("Asal Sekolah").performTextInput("SMAN 1 Bandung")

        rule.onNodeWithContentDescription("Check box register").performClick()
        rule.onNodeWithText("Daftar dan Verifikasi").performClick()

        rule.waitForIdle()
        Thread.sleep(1000)
        rule.onNode(
            isDialog() and hasAnyDescendant(hasText("Ok", ignoreCase = true)),
            useUnmergedTree = true
        ).assertExists()
        rule.waitForIdle()
        Thread.sleep(1000)
        rule.onNodeWithText("Ok").performClick()
        rule.waitForIdle()
    }

    @Test
    fun testUILogin() {
        rule.onNodeWithText("Selamat\nDatang\ndi Virtual Lab").assertExists()
        rule.onNodeWithText("Masuk").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Belum punya akun?").assertExists()
        rule.onNodeWithText("Nama Pengguna").performTextInput("abidzakly")
        rule.onNodeWithText("Password").performTextInput("abid1234")
        rule.onNodeWithText("Masuk").performClick()
        // Wait for the element with the specified text to appear
//        rule.waitForIdle()
        rule.waitUntil {
            rule.onAllNodesWithText("Video perkenalan reaksi kimia", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }

        // Assert that the element exists
        rule.onNodeWithText("Video perkenalan reaksi kimia", ignoreCase = true)
            .assertExists()
        Thread.sleep(10000)

    }
    @Test
    fun testAddLatihan() {
        rule.onNodeWithText("Selamat\nDatang\ndi Virtual Lab").assertExists()
        rule.onNodeWithText("Masuk").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Belum punya akun?").assertExists()
        rule.onNodeWithText("Nama Pengguna").performTextInput("teacher_abid")
        rule.onNodeWithText("Password").performTextInput("abid1234")
        rule.onNodeWithText("Masuk").performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Konten yang baru ditambahkan:", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Konten yang baru ditambahkan:", ignoreCase = true).assertExists()
        rule.onNodeWithContentDescription("Tambahkan", ignoreCase = true).performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Apa yang ingin kamu tambahkan?", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Apa yang ingin kamu tambahkan?", ignoreCase = true).assertExists()
        rule.onNodeWithText("Tambah Latihan").performClick()
        rule.waitForIdle()
        Thread.sleep(10000)

    }
}