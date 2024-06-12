package org.d3ifcool.virtualab

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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
    fun testUIMurid() {
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
        Thread.sleep(2000)
        rule.onNodeWithText("Ok").performClick()

        rule.waitUntil {
            rule.onAllNodesWithText("Belum punya akun?", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Nama Pengguna").performTextInput("abidzakly")
        rule.onNodeWithText("Password").performTextInput("abid1234")
        rule.onNodeWithText("Masuk").performClick()

        rule.waitUntil {
            rule.onAllNodesWithText("Video perkenalan reaksi kimia", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Latihan", ignoreCase = true).assertExists()
        Thread.sleep(2000)
        rule.onNodeWithText("Latihan", ignoreCase = true).performClick()

        rule.onNodeWithText("Latihan 1").assertExists()
        Thread.sleep(2000)
        rule.onNodeWithText("Latihan 1").performClick()

        rule.waitUntil {
            rule.onAllNodesWithText("Latihan X", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Setarakan persamaan reaksi berikut").assertExists()
        Thread.sleep(2000)
        val options2 = rule.onAllNodesWithText("2")
        val options3 = rule.onAllNodesWithText("3")
        val options4 = rule.onAllNodesWithText("4")
        options2.assertCountEquals(2)
        options3.assertCountEquals(2)
        options4.assertCountEquals(2)

        options2[0].assertIsDisplayed().performClick()
        options4[0].assertIsDisplayed().performClick()
        options2[1].assertIsDisplayed().performClick()
        rule.onNodeWithText("1").assertExists().performClick()

        rule.onNodeWithText("Kumpulkan Jawaban").assertExists().performScrollTo()
        rule.onNodeWithText("Kumpulkan Jawaban").performClick()

        rule.waitForIdle()
        Thread.sleep(1000)
        rule.onNode(
            isDialog() and hasAnyDescendant(hasText("Batal", ignoreCase = true)),
            useUnmergedTree = true
        ).assertIsDisplayed()
        rule.onNodeWithText("Ya").isDisplayed()
        rule.onNodeWithText("Ya").performClick()

        rule.waitForIdle()
        Thread.sleep(1000)
        rule.onNode(
            isDialog() and hasAnyDescendant(hasText("OK", ignoreCase = true)),
            useUnmergedTree = true
        ).assertIsDisplayed()
        rule.onNodeWithText("OK").isDisplayed()
        rule.onNodeWithText("OK").performClick()

        rule.onNodeWithText("Cek Jawaban").assertExists()
        rule.onNodeWithText("Profil").assertExists()
        rule.onNodeWithText("Profil").performClick()


        rule.onNodeWithText("Keluar").assertExists().performScrollTo()
        Thread.sleep(1000)
        rule.onNodeWithText("Keluar").performClick()

        rule.waitForIdle()
        Thread.sleep(1000)
        rule.onNode(
            isDialog() and hasAnyDescendant(hasText("Tidak", ignoreCase = true)),
            useUnmergedTree = true
        ).assertIsDisplayed()
        rule.onNodeWithText("Ya").isDisplayed()
        rule.onNodeWithText("Ya").performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Belum punya akun?").assertExists()
        Thread.sleep(5000)
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
        rule.waitUntil {
            rule.onAllNodesWithTag("Judul Latihan Title")
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithTag("Judul Latihan Title").assertExists()
        rule.onNodeWithTag("Judul Latihan TF").performTextInput("Latihan Persamaan Reaksi Kimia")
        rule.onNodeWithTag("Dropdown Menu").performClick()
        rule.onNodeWithTag("Pilihan Menu 3").performClick()
        rule.onNodeWithTag("Jumlah Soal TF").performTextInput("2")
        rule.onNodeWithText("Tambah Soal").performClick()
        rule.waitUntil {
            rule.onAllNodesWithTag("Judul Soal")
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithTag("Judul Soal").assertExists()
        Thread.sleep(5000)
//        Done sampai buat latihan saja, kurang buat soal
    }

    @Test
    fun testRejectUser() {
        rule.onNodeWithText("Selamat\nDatang\ndi Virtual Lab").assertExists()
        rule.onNodeWithText("Masuk").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Belum punya akun?").assertExists()
        rule.onNodeWithText("Nama Pengguna").performTextInput("admin")
        rule.onNodeWithText("Password").performTextInput("abid1234")
        rule.onNodeWithText("Masuk").performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Kategori", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Kategori", ignoreCase = true).assertExists()
        rule.onNodeWithText("Cek Peran Akun").performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Daftar akun baru dibuat: " , ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Daftar akun baru dibuat: ", ignoreCase = true).assertExists()
        rule.onNodeWithTag("Akun user ke 4").performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Nama Lengkap", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Nama Lengkap", ignoreCase = true).assertExists()
        rule.onNodeWithText("Tolak").performClick()
        rule.waitForIdle()
        rule.onNode(
            isDialog() and hasAnyDescendant(hasText("Tidak", ignoreCase = true)),
            useUnmergedTree = true
        ).assertExists()
        rule.onNodeWithTag("Button_Yes_Delete").performClick()
        rule.waitForIdle()
        Thread.sleep(5000)

    }

//    Success
    @Test
    fun testApproveUser() {
        rule.onNodeWithText("Selamat\nDatang\ndi Virtual Lab").assertExists()
        rule.onNodeWithText("Masuk").assertExists().performClick()

        rule.waitForIdle()
        rule.onNodeWithText("Belum punya akun?").assertExists()
        rule.onNodeWithText("Nama Pengguna").performTextInput("admin")
        rule.onNodeWithText("Password").performTextInput("abid1234")
        rule.onNodeWithText("Masuk").performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Kategori", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Kategori", ignoreCase = true).assertExists()
        rule.onNodeWithText("Cek Peran Akun").performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Daftar akun baru dibuat: ", ignoreCase = true)
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Daftar akun baru dibuat: ", ignoreCase = true).assertExists()
        rule.waitUntil {
            rule.onAllNodesWithTag("Akun user ke 3")
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithTag("Akun user ke 3").performClick()
        rule.waitUntil {
            rule.onAllNodesWithText("Nama Lengkap")
                .fetchSemanticsNodes().size == 1
        }
        rule.onNodeWithText("Nama Lengkap").assertExists()
        rule.onNodeWithText("Terima").performClick()
        rule.waitForIdle()
        Thread.sleep(10000)
    }
}

