package org.d3ifcool.virtualab.ui.screen.admin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SemiLargeText
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.GreenButton
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.RedButton
import org.d3ifcool.virtualab.utils.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersInfoScreen(navController: NavHostController, userId: Int) {
    Log.d("Users Info Screen", "User ID at Users Info: $userId")
    val context = LocalContext.current
    val factory = ViewModelFactory(user_id = userId)
    val viewModel: UsersInfoViewModel = viewModel(factory = factory)
    val approveResponse by viewModel.approveResponse.collectAsState()

    Log.d("APPROVE_RESPONSE", "APPROVE RESP?: ${approveResponse?.status}")
    LaunchedEffect(approveResponse) {
        if (approveResponse?.status == true) {
            Toast.makeText(context, approveResponse!!.message, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.AdminDashboard.route)
        }
    }

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
        ScreenContent(modifier = Modifier.padding(it), navController, viewModel)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: UsersInfoViewModel
) {
    val context = LocalContext.current

    var password by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userId by remember { mutableIntStateOf(0) }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }

    val fetchedUser by viewModel.fetchedUser.collectAsState()

    if (fetchedUser != null) {
        fullname = fetchedUser?.user?.full_name ?: ""
        username = fetchedUser?.user?.username ?: ""
        email = fetchedUser?.user?.email ?: ""
        userId = fetchedUser?.user?.user_id ?: 0
        school = fetchedUser?.user?.school ?: ""
        uniqueId =
            if (fetchedUser?.user?.user_type == 0) fetchedUser?.student?.nisn ?: ""
            else fetchedUser?.teacher?.nip ?: ""
    }

    var showDialog by remember { mutableStateOf(false) }
    val isEmailSent by viewModel.emailSent.collectAsState()


    Log.d("EMAIL_SENT", "email sent?: $isEmailSent")
    LaunchedEffect(isEmailSent) {
        if (isEmailSent) {
            viewModel.approveUser(userId, password)
        }
    }


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
            value = fullname,
            onValueChange = { },
            text = R.string.fullname_label,
            readOnly = true
        )
        UserInfoColumn(
            value = username,
            onValueChange = { },
            text = R.string.username_label,
            readOnly = true
        )
        UserInfoColumn(
            value = email,
            onValueChange = { },
            text = R.string.email_label,
            readOnly = true
        )
        UserInfoColumn(
            value = uniqueId,
            onValueChange = { },
            text = R.string.nip_label,
            readOnly = true
        )
        UserInfoColumn(
            value = school,
            onValueChange = { },
            text = R.string.school_label,
            readOnly = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                password = viewModel.generatePassword()
                viewModel.sendEmail(
                    context,
                    email,
                    password
                )
            },
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
            RejectPopup(onDismiss = { showDialog = false }) {
                viewModel.rejectUser(userId)
            }
        }
    }
}

@Composable
fun UserInfoColumn(
    modifier: Modifier = Modifier,
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
            modifier = modifier.fillMaxWidth(),
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
fun RejectPopup(onDismiss: () -> Unit, onConfirm: () -> Unit) {
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
                onClick = { onConfirm() },
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

@Preview
@Composable
private fun Prev() {
    UsersInfoScreen(navController = rememberNavController(), 0)
}