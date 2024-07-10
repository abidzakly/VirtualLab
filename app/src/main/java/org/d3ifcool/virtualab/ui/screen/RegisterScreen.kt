package org.d3ifcool.virtualab.ui.screen

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.UserCreate
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.theme.BlueLink
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.GrayText
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, id: Int, viewModel: AuthViewModel) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val apiStatus by viewModel.apiStatus.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    when (apiStatus) {
        ApiStatus.LOADING -> {
            Dialog(onDismissRequest = { showDialog = false }) {
                LoadingState()
            }
        }

        ApiStatus.SUCCESS -> {
            showDialog = true
        }

        ApiStatus.FAILED -> {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            viewModel.clearStatus()
        }

        ApiStatus.IDLE -> null
    }

    if (showDialog) {
        RegistSuccessPopup(
            onDismiss = { showDialog = false },
            navController
        )
    }

    LaunchedEffect(errorMsg) {
        if (errorMsg != "") {
            Toast.makeText(
                context,
                errorMsg,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearStatus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        ),
                        tint = Color.Black
                    )
                }
            }, title = {
                Text(text = stringResource(id = R.string.register_title), fontFamily = Poppins)
            }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent, titleContentColor = Color.Black
            )
            )
        }, containerColor = Color.White
    ) {

        ScreenContent(modifier = Modifier.padding(it), viewModel, navController = navController, id, context, focusManager)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier, viewModel: AuthViewModel, navController: NavHostController, id: Int, context: Context, focusManager: FocusManager
) {
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var uniqueId by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }

    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            }
            .padding(horizontal = 48.dp, vertical = 24.dp)
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
        RegisterForm(
            fullname = fullname,
            onFullnameChange = { fullname = it },
            fullnameResponse = fullnameCheck(fullname),
            username = username,
            onUsernameChange = { username = it },
            usernameResponse = usernameCheck(username),
            email = email,
            onEmailChange = { email = it },
            emailResponse = emailCheck(email),
            uniqueId = uniqueId,
            onUniqueIdChange = { uniqueId = it },
            uniqueIdResponse = uniqueIdCheck(id, uniqueId),
            school = school,
            onSchoolChange = { school = it },
            schoolResponse = if (school == "") 1 else 0,
            id = id
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.syarat_dan_ketentuan_label)),
                    style = TextStyle(
                        color = Color(0xFF0066FF),
                        fontSize = 14.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    navController.navigate(Screen.TermsCondition.route) {
                        popUpTo(Screen.Landing.route)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                SmallText(
                    text = stringResource(R.string.register_extra_note),
                    fontWeight = FontWeight.Light,
                    color = Color(0xFF4A4A4A)
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (!isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = "Check box register",
                        tint = Color(0xFF4D444C),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                SmallText(
                    text = "Saya Setuju",
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.padding(horizontal = 31.dp),
            onClick = {
                when {
                    fullnameCheck(fullname) != 0 -> return@Button
                    usernameCheck(username) != 0 -> return@Button
                    emailCheck(email) != 0 -> return@Button
                    uniqueIdCheck(id, uniqueId) != 0 -> return@Button
                    !isChecked -> Toast.makeText(
                        context,
                        "Anda harus menyetujui S&K sebelum mendaftar!",
                        Toast.LENGTH_SHORT
                    ).show()

                    school.isEmpty() || school.isBlank() -> return@Button
                    else -> {
                        viewModel.register(
                            uniqueId,
                            UserCreate(
                                fullname,
                                username,
                                id,
                                email = email,
                                school = school
                            )
                        )
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = buttonColors(
                containerColor = LightBlue, contentColor = Color.Black
            )
        ) {
            RegularText(
                text = stringResource(id = R.string.signup_button),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row {
            SmallText(
                text = stringResource(id = R.string.account_exist),
            )
            Spacer(modifier = Modifier.padding(2.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.signin)),
                style = TextStyle(color = BlueLink, fontSize = 14.sp, fontFamily = Poppins)
            ) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Landing.route)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

private fun fullnameCheck(fullname: String): Int {
    return when {
        fullname.isEmpty() || fullname.isBlank() -> 1
        fullname.length > 60 -> 2
        else -> 0
    }
}

private fun usernameCheck(username: String): Int {
    return when {
        username.isEmpty() || username.isBlank() -> 1
        username.length !in 20 downTo 8 -> 2
        username.contains(
            Regex("[^A-Za-z0-9_]")
        ) -> 3

        else -> 0
    }
}

private fun emailCheck(email: String): Int {
    return when {
        email.isEmpty() || email.isBlank() -> 1
        !email.contains(
            Regex("^.+@.+\\..+$")
        ) -> 2

        else -> 0
    }
}

private fun uniqueIdCheck(id: Int, uniqueId: String): Int {
    return if (id == 1) {
        when {
            uniqueId.isEmpty() || uniqueId.isBlank() -> 1
            uniqueId.length != 9 && uniqueId.length != 18 -> 2
            else -> 0
        }
    } else {
        when {
            uniqueId.isEmpty() || uniqueId.isBlank() -> 1
            uniqueId.length != 10 -> 3
            else -> 0
        }
    }
}

@Composable
private fun RegisterForm(
    fullname: String,
    onFullnameChange: (String) -> Unit,
    fullnameResponse: Int = 0,
    username: String,
    onUsernameChange: (String) -> Unit,
    usernameResponse: Int = 0,
    email: String,
    onEmailChange: (String) -> Unit,
    emailResponse: Int = 0,
    uniqueId: String,
    onUniqueIdChange: (String) -> Unit,
    uniqueIdResponse: Int = 0,
    school: String,
    onSchoolChange: (String) -> Unit,
    schoolResponse: Int = 0,
    id: Int
) {
    Box(modifier = Modifier
        .fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            RegistTextField(
                value = fullname,
                onValueChange = { onFullnameChange(it) },
                label = R.string.fullname_label,
                isError = fullnameResponse != 0,
                errorText = when (fullnameResponse) {
                    1 -> "Nama lengkap tidak boleh kosong!"
                    2 -> "Nama lengkap melebihi batas 60 karakter!"
                    else -> ""
                }
            )
            RegistTextField(
                value = username,
                onValueChange = { onUsernameChange(it) },
                label = R.string.username_label,
                supportingLabel = " (Username)",
                isUsername = true,
                isError = usernameResponse != 0,
                errorText = when (usernameResponse) {
                    1 -> "Nama pengguna tidak boleh kosong!"
                    2 -> "Nama pengguna harus memiliki minimal 8 hingga maksimal 20 karakter!"
                    3 -> "Nama pengguna hanya boleh mengandung huruf dan angka!"
                    else -> ""
                }
            )
            RegistTextField(
                value = email,
                onValueChange = { onEmailChange(it) },
                label = R.string.email_label,
                supportingLabel = " (Email)",
                isEmail = true,
                isError = emailResponse != 0,
                errorText = when (emailResponse) {
                    1 -> "Email tidak boleh kosong!"
                    2 -> "Format email tidak valid!"
                    else -> ""
                }
            )
            if (id == 0) {
                RegistTextField(
                    value = uniqueId,
                    onValueChange = { onUniqueIdChange(it) },
                    label = R.string.nisn_label,
                    isNumber = true,
                    isError = uniqueIdResponse != 0,
                    errorText = when (uniqueIdResponse) {
                        1 -> "NISN tidak boleh kosong!"
                        3 -> "Pastikan NISN berjumlah 10 digit!"
                        else -> ""
                    }
                )
            } else {
                RegistTextField(
                    value = uniqueId,
                    onValueChange = { onUniqueIdChange(it) },
                    label = R.string.nip_label,
                    isNumber = true,
                    isError = uniqueIdResponse != 0,
                    errorText = when (uniqueIdResponse) {
                        1 -> "NIP tidak boleh kosong!"
                        2 -> "Pastikan NIP berjumlah 9 atau 18 digit!"
                        else -> ""
                    }
                )
            }
            RegistTextField(
                value = school,
                onValueChange = { onSchoolChange(it) },
                label = R.string.school_label,
                isError = schoolResponse != 0,
                errorText = if (schoolResponse != 0) "Nama sekolah tidak boleh kosong!" else "",
                imeAction = ImeAction.Done
            )
        }
    }
}

@Composable
private fun RegistTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    supportingLabel: String = "",
    isUsername: Boolean = false,
    isNumber: Boolean = false,
    isEmail: Boolean = false,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    var passwordVisibility by remember { mutableStateOf(!isPassword) }
    var isFocused by remember { mutableStateOf(true) }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusable()
            .onFocusChanged { isFocused = !isFocused },
        textStyle = TextStyle(fontSize = 14.sp, fontFamily = Poppins),
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Row {
                Text(text = stringResource(label), fontSize = 14.sp, fontFamily = Poppins)
                Text(
                    text = supportingLabel,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    fontFamily = Poppins
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = when {
                isNumber -> KeyboardType.Number
                isEmail -> KeyboardType.Email
                isPassword -> KeyboardType.Password
                else -> KeyboardType.Text
            }, capitalization = when {
                !isUsername && !isEmail -> KeyboardCapitalization.Words
                else -> KeyboardCapitalization.None
            }, imeAction = if (!isPassword) imeAction else ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedLabelColor = GrayText,
            unfocusedLabelColor = GrayText,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        trailingIcon = if (!isPassword) null else {
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
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (isFocused) {
        if (isError) {
            SmallText(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RegistSuccessPopup(onDismiss: () -> Unit, navController: NavHostController) {
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
                RegularText(
                    text = "Akun berhasil dibuat!\n" +
                            "Mohon tunggu konfirmasi dari admin",
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
                        onClick = { onDismiss(); navController.navigate(Screen.Login.route) },
                        colors = buttonColors(
                            containerColor = LightBlue,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        MediumLargeText(
                            text = "Ok",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun RegisterScreenPreview() {
}