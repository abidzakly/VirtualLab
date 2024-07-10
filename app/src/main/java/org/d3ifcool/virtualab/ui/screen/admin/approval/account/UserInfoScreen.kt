package org.d3ifcool.virtualab.ui.screen.admin.approval.account

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.PopUpDialog
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.GreenButton
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.ui.theme.RedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersInfoScreen(navController: NavHostController, viewModel: UserInfoViewModel) {
    val context = LocalContext.current
    val approveResponse by viewModel.approveResponse.collectAsState()
    val rejectResponse by viewModel.rejectResponse.collectAsState()

    LaunchedEffect(approveResponse) {
        if (approveResponse?.status == true) {
            Toast.makeText(context, approveResponse!!.message, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.CheckUser.route) {
                popUpTo(Screen.AdminDashboard.route)
            }
        }
    }

    LaunchedEffect(rejectResponse) {
        if (rejectResponse?.status == true) {
            Toast.makeText(context, rejectResponse!!.message, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.CheckUser.route) {
                popUpTo(Screen.AdminDashboard.route)
            }
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
            title = { LargeText(text = stringResource(id = R.string.category_check_account)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            ),
        )
    }, bottomBar = {
        BottomNav(navController = navController)
    }, containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), viewModel, context)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: UserInfoViewModel,
    context: Context,
) {

    var password by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userId by remember { mutableIntStateOf(0) }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    val fetchedUser by viewModel.fetchedUser.collectAsState()

    if (fetchedUser != null) {
        fullname = fetchedUser?.user?.fullName ?: ""
        username = fetchedUser?.user?.username ?: ""
        email = fetchedUser?.user?.email ?: ""
        userId = fetchedUser?.user?.userId ?: 0
        school = fetchedUser?.user?.school ?: ""
        uniqueId =
            if (fetchedUser?.user?.userType == 0) fetchedUser?.student?.nisn ?: ""
            else fetchedUser?.teacher?.nip ?: ""
    }

    var showDialog by remember { mutableStateOf(false) }

    val isEmailSent by viewModel.emailSent.collectAsState()

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
            text = R.string.fullname_label,
        )
        UserInfoColumn(
            value = username,
            text = R.string.username_label,
        )
        UserInfoColumn(
            value = email,
            text = R.string.email_label,
        )
        UserInfoColumn(
            value = uniqueId,
            text = R.string.nip_label,
        )
        UserInfoColumn(
            value = school,
            text = R.string.school_label,
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
            PopUpDialog(
                onDismiss = { showDialog = false },
                icon = R.drawable.icon_accept_akun,
                "Tolak Akun ini?"
            ) {
                viewModel.rejectUser(userId)
            }
        }
    }
}

@Composable
fun UserInfoColumn(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    text: Int,
    readOnly: Boolean = true,
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
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, fontFamily = Poppins),
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


@Preview
@Composable
private fun Prev() {
    PopUpDialog(
        onDismiss = { /*TODO*/ },
        icon = R.drawable.icon_accept_akun,
        title = "Tolak akun ini?"
    ) {

    }
//    UsersInfoScreen(navController = rememberNavController(), 0)
}