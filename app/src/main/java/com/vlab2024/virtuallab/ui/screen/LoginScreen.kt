package com.vlab2024.virtuallab.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.vlab2024.virtuallab.ui.theme.LightBlue

@Composable
fun LoginScreen(navController: NavHostController) {

    Scaffold {
    ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.signin),
            fontSize = 22.sp,
            modifier = Modifier.padding(32.dp)
        )
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
            label = { Text(text = stringResource(R.string.username)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
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
                }) { // Toggle visibility icon
                    Icon( // Use your preferred icon component
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Button(
            onClick = { navController.navigate(Screen.Register.route) },
            modifier = Modifier
                .height(47.dp)
                .width(150.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBlue,
                contentColor = Color.Black
            )
        ) {
            Text(text = stringResource(id = R.string.signin), fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}
