package org.d3ifcool.virtualab.ui.screen

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.BlueLink
import org.d3ifcool.virtualab.ui.theme.GrayText
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.utils.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val factory = ViewModelFactory(userDataStore = dataStore)
    val viewModel: AuthViewModel = viewModel(factory = factory)

    val currentUser by viewModel.currentUser.collectAsState()
    val apiStatus by viewModel.apiStatus.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    when (apiStatus) {
        ApiStatus.LOADING -> {
            Toast.makeText(context, "Loading..", Toast.LENGTH_SHORT).show()
        }

        ApiStatus.SUCCESS -> {
            if (currentUser != null) {
                Log.d("LoginScreen", "UserType: ${currentUser!!.user!!.user_type}")
                Log.d(
                    "LoginScreen",
                    "User: ${currentUser!!.user!!}\nGuru: ${currentUser!!.teacher}\nMurid: ${currentUser!!.student}"
                )
                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                when (currentUser!!.user!!.user_type) {
                    0 -> navController.navigate(Screen.MuridDashboard.route)

                    1 -> navController.navigate(Screen.GuruDashboard.route)

                    2 -> navController.navigate(Screen.AdminDashboard.route)
                    else -> Toast.makeText(context, "ID tidak valid", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ApiStatus.FAILED -> {
            Log.d("LoginScreen", "Login Error: $errorMsg")
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            viewModel.clearErrorMsg()
        }

        ApiStatus.IDLE -> null
    }

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
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.login_title),
                        fontSize = 22.sp,
                        fontFamily = Poppins
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) {
        LoginScreenContent(modifier = Modifier.padding(it), navController, viewModel, context)
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: AuthViewModel,
    context: Context
) {


    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var loginAttempt by remember { mutableIntStateOf(0) }

    var passwordVisibility by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.vlab_logo),
            contentDescription = "Logo Virtual Lab",
            modifier = Modifier
                .padding(bottom = 80.dp)
                .size(181.dp)
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = stringResource(R.string.username_label), fontFamily = Poppins) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = GrayText
            )
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.password_label), fontFamily = Poppins) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) { // Toggle visibility icon
                    Icon( // Use your preferred icon component
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = GrayText
            )
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Button(
            onClick = {
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Harap mengisi semua data terlebih dahulu!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                } else {
                    loginAttempt++
                    viewModel.login(username, password)
                }
            },
            modifier = Modifier
                .height(47.dp)
                .width(150.dp)
                .testTag("BUTTON_LOGIN"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBlue,
                contentColor = Color.Black
            )
        ) {
            RegularText(
                text = stringResource(id = R.string.signin),
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))

        Row {
            RegularText(
                text = stringResource(id = R.string.no_account),
            )
            Spacer(modifier = Modifier.padding(2.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.signup_clickable)),
                style = TextStyle(color = BlueLink, fontSize = 16.sp, fontFamily = Poppins)
            ) {
                navController.navigate(Screen.Role.route) {
                    popUpTo(Screen.Landing.route)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}
