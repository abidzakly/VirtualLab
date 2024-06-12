package org.d3ifcool.virtualab.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SemiLargeText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.ui.theme.RedButton

@Composable
fun ProfileScreen(navController: NavHostController, id: Int) {
    Scaffold(
        topBar = {
            TopNav(R.string.profile_title, navController = navController)
        }, bottomBar = {
            BottomNav(Screen.Profile.route, navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController, id)
    }
}


@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController, id: Int) {
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var readOnly by remember { mutableStateOf(true) }

    var showDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordVisibility2 by remember { mutableStateOf(false) }
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Image(
            painter = painterResource(id = R.drawable.profile_big),
            contentDescription = "Profile Picture"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier = Modifier.padding(horizontal = 48.dp)) {
            UserTextFields(
                value = fullname,
                onValueChange = { fullname = it },
                text = R.string.fullname_label,
                readOnly = true
            )
            UserTextFields(
                value = username,
                onValueChange = { username = it },
                text = R.string.username_label,
                readOnly = true
            )
                RegularText(
                    text = stringResource(id = R.string.email_label),
                    fontWeight = FontWeight.SemiBold
                )
                TextField(
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    value = "asdf@gmail.com",
                    readOnly = readOnly,
                    onValueChange = { email = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = if (readOnly) {
                        TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            focusedContainerColor = Color(0xFFFAFAFA)
                        )
                    } else {
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (id == 0) {
                    UserTextFields(
                        value = uniqueId,
                        onValueChange = { uniqueId = it },
                        text = R.string.nisn_label,
                        readOnly = true
                    )
                } else {
                    UserTextFields(
                        value = uniqueId,
                        onValueChange = { uniqueId = it },
                        text = R.string.nip_label,
                        readOnly = true
                    )
                }
                UserTextFields(
                    value = school,
                    onValueChange = { school = it },
                    text = R.string.school_label,
                    readOnly = true
                )
                RegularText(
                    text = stringResource(id = R.string.password_label),
                    fontWeight = FontWeight.SemiBold
                )
                TextField(
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    value = "asdfghjkl",
                    readOnly = readOnly,
                    onValueChange = { password = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next,
                    ),
                    visualTransformation =
                    if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = if (!readOnly) {
                        {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                                )
                            }
                        }
                    } else null,
                    modifier = Modifier.fillMaxWidth(),
                    colors = if (readOnly) {
                        TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            focusedContainerColor = Color(0xFFFAFAFA)
                        )
                    } else {
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        )
                    }
                )
                if (!readOnly) {
                    Spacer(modifier = Modifier.height(16.dp))

                    RegularText(
                        text = stringResource(R.string.edit_password_label)
                    )
                    TextField(
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next,
                        ),
                        visualTransformation =
                        if (passwordVisibility2) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility2 = !passwordVisibility2
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility2) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisibility2) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        )

                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    if (readOnly) {
                        Button(
                            onClick = { readOnly = !readOnly },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LightBlue,
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 42.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.edit_button),
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins,
                                fontSize = 16.sp,
                            )
                        }
                        Button(
                            onClick = { showLogoutDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RedButton,
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 42.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.logout_button),
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                        if (showLogoutDialog) {
                            ConfirmLogoutPopup(
                                onDismiss = { showLogoutDialog = false },
                                navController
                            )
                        }
                    } else {
                        Button(
                            onClick = { showDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LightBlue,
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 42.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.save_button),
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins,
                                fontSize = 16.sp,
                            )

                        }
                        if (showDialog) {
                            SaveUpdatePopup(
                                onDismiss = { showDialog = false }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UserTextFields(
    value: String,
    onValueChange: (String) -> Unit,
    text: Int,
    readOnly: Boolean,
) {
    Column {
        RegularText(
            text = stringResource(id = text),
            fontWeight = FontWeight.SemiBold
        )
        TextField(
            textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF6D6E6F)),
            shape = RoundedCornerShape(5.dp),
            readOnly = readOnly,
            value = "test",
            onValueChange = { onValueChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth(),
            colors = if (readOnly) {
                TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFAFAFA),
                    focusedContainerColor = Color(0xFFFAFAFA)
                )
            } else {
                TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SaveUpdatePopup(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.Red
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_process_success),
                    contentDescription = "Ikon proses berhasil",
                    tint = DarkBlue
                )
                SemiLargeText(
                    text = "Perubahan berhasil disimpan!",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightBlue,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        MediumLargeText(
                            text = "Ya",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConfirmLogoutPopup(onDismiss: () -> Unit, navController: NavHostController) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_logout),
                contentDescription = "Ikon konfirmasi logout",
                tint = DarkBlue,
                modifier = Modifier.size(50.dp)
            )
        },
        title = {
            SemiLargeText(
                text = "Anda yakin ingin keluar?",
                fontWeight = FontWeight.SemiBold
            )
        },
        confirmButton = {
            Button(
                onClick = { navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route)
                } },
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(rememberNavController(), 0)
}