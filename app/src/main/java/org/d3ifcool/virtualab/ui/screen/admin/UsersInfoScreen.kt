package org.d3ifcool.virtualab.ui.screen.admin

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SemiLargeText
import org.d3ifcool.virtualab.ui.screen.profile.UserTextFields
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.GreenButton
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.ui.theme.RedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersInfoScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        ),
                        tint = Color.Black
                    )
                }
            },
            title = { LargeText(text = stringResource(id = R.string.category_check_role)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            ),
        )
    }, bottomBar = {
        BottomNav(currentRoute = Screen.AdminDashboard.route, navController = navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("amruabid8212@gmail.com") }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var readOnly by remember { mutableStateOf(true) }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoColumn(
            value = "Suyanto",
            onValueChange = { fullname = it },
            text = R.string.fullname_label,
            readOnly = readOnly
        )
        UserInfoColumn(
            value = "yanto123",
            onValueChange = { username = it },
            text = R.string.username_label,
            readOnly = readOnly
        )
        UserInfoColumn(
            value = email,
            onValueChange = { email = it },
            text = R.string.email_label,
            readOnly = readOnly
        )
        UserInfoColumn(
            value = "199202262017051001",
            onValueChange = { uniqueId = it },
            text = R.string.nip_label,
            readOnly = readOnly
        )
        UserInfoColumn(
            value = "SMAN 1 Bandung",
            onValueChange = { school = it },
            text = R.string.school_label,
            readOnly = readOnly
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { password = generatePassword(); sendEmail(context, email, password) },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenButton,
                contentColor = Color.Black
            )
        ) {
            RegularText(text = stringResource(id = R.string.button_terima))
        }
        Button(
            onClick = { showDialog = true },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedButton,
                contentColor = Color.White
            )
        ) {
            RegularText(text = stringResource(id = R.string.button_tolak), color = Color.White)
        }
        if (showDialog) {
            RejectPopup(onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun UserInfoColumn(
    modifier: Modifier? = null,
    value: String,
    onValueChange: (String) -> Unit,
    text: Int,
    readOnly: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        RegularText(
            text = stringResource(id = text),
            fontWeight = FontWeight.SemiBold
        )
        TextField(
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            shape = RoundedCornerShape(5.dp),
            readOnly = readOnly,
            value = value,
            onValueChange = { onValueChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = modifier?.fillMaxWidth()
                ?: Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color(0xFFDAE8EB),
                focusedContainerColor = Color(0xFFDAE8EB)
            )
        )
    }
}

@Composable
fun RejectPopup(onDismiss: () -> Unit) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_accept_akun),
                contentDescription = "Ikon penerimaan akun",
                tint = DarkBlue
            )
        },
        title = { SemiLargeText(text = "Tolak akun ini?", fontWeight = FontWeight.SemiBold) },
        confirmButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedButton,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                MediumLargeText(text = "Ya", fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                MediumLargeText(text = "Tidak", fontWeight = FontWeight.SemiBold)
            }
        }
    )
}

fun sendEmail(context: Context, email: String, password: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, "Password Akun Anda")
        putExtra(Intent.EXTRA_TEXT, "Password untuk akun Virtual Lab Anda adalah:\n\n $password")
    }

    context.startActivity(Intent.createChooser(intent, "Send Email"))
}

fun generatePassword(): String {
    val upperCaseLetters = ('A'..'Z')
    val lowerCaseLetters = ('a'..'z')
    val specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/"
    val digits = ('0'..'9')

    val allCharacters =
        upperCaseLetters + lowerCaseLetters + specialCharacters.toCharArray() + digits

    val password = StringBuilder()
    password.append(upperCaseLetters.random())
    password.append(lowerCaseLetters.random())
    password.append(specialCharacters.random())
    password.append(digits.random())

    for (i in 4 until 8) {
        password.append(allCharacters.random())
    }

    return password.toString().toList().shuffled().joinToString("")
}

@Preview
@Composable
private fun Prev() {
    UsersInfoScreen(navController = rememberNavController())
}