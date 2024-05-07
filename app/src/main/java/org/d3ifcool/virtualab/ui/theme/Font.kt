package org.d3ifcool.virtualab.ui.theme


import androidx.compose.ui.text.googlefonts.GoogleFont
import com.vlab2024.virtuallab.R
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Poppins")

val Poppins = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
