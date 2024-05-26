package org.d3ifcool.virtualab.ui.screen.auth

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.BlueLink
import org.d3ifcool.virtualab.ui.theme.GrayText
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins

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
        RegisterScreenContent(modifier = Modifier.padding(it), navController = navController, id)
    }
}

@Composable
private fun RegisterScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    id: Long? = null
) {
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
            .padding(horizontal = 34.dp)
            .verticalScroll(rememberScrollState()),
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
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            TextField(
                value = fullname,
                onValueChange = { fullname = it },
                label = { Text(text = stringResource(R.string.fullname_label)) },
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
                label = { Text(text = stringResource(R.string.username_label)) },
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

            if (identifier == 0L) {
                TextField(
                    value = uniqueId,
                    onValueChange = { uniqueId = it },
                    label = { Text(text = stringResource(R.string.nip_label)) },
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
                    label = { Text(text = stringResource(R.string.nisn_label)) },
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
                label = { Text(text = stringResource(R.string.password_label)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
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
                modifier = Modifier.padding(horizontal = 31.dp),
                onClick = { navController.navigate(Screen.Login.route) },
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                )
            ) {
                RegularText(
                    text = stringResource(id = R.string.signup_button),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row {
            RegularText(
                text = stringResource(id = R.string.account_exist),
            )
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController(), 0)
}