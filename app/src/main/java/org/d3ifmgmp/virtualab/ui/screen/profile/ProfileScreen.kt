package org.d3ifmgmp.virtualab.ui.screen.profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifmgmp.virtualab.R
import org.d3ifmgmp.virtualab.navigation.Screen
import org.d3ifmgmp.virtualab.ui.component.BottomNav
import org.d3ifmgmp.virtualab.ui.component.MediumText
import org.d3ifmgmp.virtualab.ui.component.RegularText
import org.d3ifmgmp.virtualab.ui.component.TopNav
import org.d3ifmgmp.virtualab.ui.theme.GrayText
import org.d3ifmgmp.virtualab.ui.theme.LightBlue
import org.d3ifmgmp.virtualab.ui.theme.Poppins
import org.d3ifmgmp.virtualab.ui.theme.RedButton

@Composable
fun ProfileScreen(navController: NavHostController, id: Long? = 1L) {
    Scaffold(
        topBar = {
            TopNav(R.string.profile_title, navController = navController)
        }, bottomBar = {
            BottomNav(Screen.Profile.route, navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController, id!!)
    }
}


@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController, id: Long) {
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var readOnly by remember { mutableStateOf(true) }

    var passwordVisibility by remember { mutableStateOf(true) }
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MediumText(text = stringResource(id = R.string.profile_header))
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
                readOnly = readOnly
            )
            UserTextFields(
                value = username,
                onValueChange = { username = it },
                text = R.string.username_label,
                readOnly = readOnly
            )
            if (id == 0L) {
                UserTextFields(
                    value = uniqueId,
                    onValueChange = { uniqueId = it },
                    text = R.string.nip_label,
                    readOnly = readOnly
                )
            } else {
                UserTextFields(
                    value = uniqueId,
                    onValueChange = { uniqueId = it },
                    text = R.string.nisn_label,
                    readOnly = readOnly
                )
            }
            UserTextFields(
                value = school,
                onValueChange = { school = it },
                text = R.string.school_label,
                readOnly = readOnly
            )
//            if (!readOnly) {
                RegularText(
                    text = stringResource(id = R.string.password_label),
                    fontWeight = FontWeight.Medium
                )
                TextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next,
                    ),
                    visualTransformation =
                    if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password"
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
                Text(
                    text = stringResource(R.string.edit_password_label),
                    fontSize = 15.sp,
                    fontFamily = Poppins,
                    color = GrayText
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next,
                    ),
                    visualTransformation =
                    if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password"
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
//            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
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
                        text = stringResource(if (readOnly) R.string.edit_button else R.string.save_button),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins,
                        fontSize = 16.sp,
                    )
                }
                Button(
                    onClick = { navController.navigate(Screen.Login.route) },
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
            }
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
            fontWeight = FontWeight.Medium
        )
        TextField(
            textStyle = TextStyle(fontSize = 14.sp, color = Color(0xFF6D6E6F)),
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
    }
    Spacer(modifier = Modifier.padding(8.dp))
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(rememberNavController(), 1L)
}