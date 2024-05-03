package com.vlab2024.virtuallab.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.AnnotatedString
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
import com.vlab2024.virtuallab.R
import com.vlab2024.virtuallab.navigation.Screen
import com.vlab2024.virtuallab.ui.theme.BlueLink
import com.vlab2024.virtuallab.ui.theme.GrayText
import com.vlab2024.virtuallab.ui.theme.LightBlue
import com.vlab2024.virtuallab.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, id: Long? = null) {
    Scaffold(
        topBar = {
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
                title = {
                    Text(text = stringResource(id = R.string.register_title))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController = navController, id)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController, id: Long? = null) {
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val identifier by remember { mutableLongStateOf(id!!) }

    var passwordVisibility by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp).
        verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.vlab_logo),
            contentDescription = "Logo Virtual Lab",
            modifier = Modifier
                .padding(bottom = 40.dp)
                .size(181.dp)
        )
        TextField(
            value = fullname,
            onValueChange = { fullname = it },
            label = { Text(text = stringResource(R.string.fullname)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedLabelColor = GrayText,
                unfocusedLabelColor = GrayText
            )
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
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedLabelColor = GrayText,
                unfocusedLabelColor = GrayText
            )
        )
        if (identifier == 0L) {
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
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = GrayText,
                    unfocusedLabelColor = GrayText
                )
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
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = GrayText,
                    unfocusedLabelColor = GrayText
                )
            )
        }
        TextField(
            value = school,
            onValueChange = { school = it },
            label = { Text(text = stringResource(R.string.school)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedLabelColor = GrayText,
                unfocusedLabelColor = GrayText
            )
        )
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
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
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
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedLabelColor = GrayText,
                unfocusedLabelColor = GrayText
            )
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = { navController.navigate(Screen.Register.route) },
            modifier = Modifier
                .height(47.dp)
                .width(150.dp),
            shape = RoundedCornerShape(10.dp),
            colors = buttonColors(
                containerColor = LightBlue,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = stringResource(id = R.string.signup),
                fontWeight = FontWeight.SemiBold,
                fontFamily = Poppins,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row {
            Text(text = stringResource(id = R.string.account_exist), fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
            Spacer(modifier = Modifier.padding(2.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.signin)),
                style = TextStyle(color = BlueLink, fontSize = 16.sp, fontFamily = Poppins)
            ) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Landing.route)
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    RegisterScreen(rememberNavController(), 0)
}