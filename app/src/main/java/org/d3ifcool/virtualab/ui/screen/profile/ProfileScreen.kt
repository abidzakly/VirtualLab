package org.d3ifcool.virtualab.ui.screen.profile

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.theme.GrayText
import org.d3ifcool.virtualab.ui.theme.GrayTextField
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.ui.theme.RedButton

@Composable
fun ProfileScreen(navController: NavHostController, id: Long? = 1L) {
    val identifier by remember { mutableLongStateOf(id!!) }

    Scaffold(bottomBar = {
        BottomNav(Screen.Profile.route, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), navController, id!!)
    }
}


@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController, id: Long) {
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var readOnly by remember { mutableStateOf(true) }

    var passwordVisibility by remember { mutableStateOf(false) }
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.profile_header), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(18.dp))
        Image(
            painter = painterResource(id = R.drawable.profile_big),
            contentDescription = "Profile Picture"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            TextField(
                readOnly = readOnly,
                value = fullname,
                onValueChange = { fullname = it },
                label = { Text(text = stringResource(R.string.fullname)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = if (readOnly) {
                    TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = GrayTextField,
                        focusedContainerColor = GrayTextField
                    )
                } else {
                    TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedLabelColor = GrayText,
                        unfocusedLabelColor = GrayText
                    )
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(R.string.username)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = if (readOnly) {
                    TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = GrayTextField,
                        focusedContainerColor = GrayTextField
                    )
                } else {
                    TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedLabelColor = GrayText,
                        unfocusedLabelColor = GrayText
                    )
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            if (id == 0L) {
                TextField(
                    value = uniqueId,
                    onValueChange = { uniqueId = it },
                    label = { Text(text = stringResource(R.string.nip)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = if (readOnly) {
                        TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = GrayTextField,
                            focusedContainerColor = GrayTextField
                        )
                    } else {
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedLabelColor = GrayText,
                            unfocusedLabelColor = GrayText
                        )
                    }
                )
            } else {
                TextField(
                    value = uniqueId,
                    onValueChange = { uniqueId = it },
                    label = { Text(text = stringResource(R.string.nisn)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = if (readOnly) {
                        TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = GrayTextField,
                            focusedContainerColor = GrayTextField
                        )
                    } else {
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedLabelColor = GrayText,
                            unfocusedLabelColor = GrayText
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = school,
                onValueChange = { school = it },
                label = { Text(text = stringResource(R.string.school_label)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = if (readOnly) {
                    TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = GrayTextField,
                        focusedContainerColor = GrayTextField
                    )
                } else {
                    TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedLabelColor = GrayText,
                        unfocusedLabelColor = GrayText
                    )
                }
            )
            if (!readOnly) {
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(R.string.password)) },
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
                    colors = if (readOnly) {
                        TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = GrayTextField,
                            focusedContainerColor = GrayTextField
                        )
                    } else {
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedLabelColor = GrayText,
                            unfocusedLabelColor = GrayText
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        if (readOnly) {
            Button(
                onClick = { readOnly = false },
                modifier = Modifier
                    .height(47.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = stringResource(id = R.string.edit_button),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                )
            }
        } else {
            Button(
                onClick = { readOnly = !readOnly },
                modifier = Modifier
                    .height(47.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = stringResource(id = R.string.save_button),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier
                .height(47.dp)
                .width(150.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedButton,
                contentColor = Color.Black
            )
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


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(rememberNavController(), 1L)
}