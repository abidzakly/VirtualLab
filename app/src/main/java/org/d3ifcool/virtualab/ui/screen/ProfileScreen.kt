package org.d3ifcool.virtualab.ui.screen

import android.content.res.Configuration
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
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
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.User
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomModalSheet
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ImageDialog
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.PopUpDialog
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SemiLargeText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlue
import org.d3ifcool.virtualab.ui.theme.GrayTextField
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.ui.theme.RedButton
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.utils.getCroppedImage
import org.d3ifcool.virtualab.utils.isInternetAvailable
import org.d3ifcool.virtualab.utils.rememberImeState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel,
    dataStore: UserDataStore
) {
    val focusManager = LocalFocusManager.current
    val currentUser by dataStore.userFlow.collectAsState(User())
    val uniqueId = when (currentUser.userType) {
        0 -> dataStore.nisnFlow.collectAsState("")
        else -> dataStore.nipFlow.collectAsState("")
    }.value

    val sheetStateLihat = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sheetStateLihat.currentValue) {
        snapshotFlow { sheetStateLihat.currentValue }
            .collect { bottomSheetValue ->
                if (bottomSheetValue == ModalBottomSheetValue.Hidden) {
                    isPressed = false
                }
            }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            scope.launch {
                sheetStateLihat.show()
            }
        } else {
            scope.launch {
                sheetStateLihat.hide()
            }
        }
    }

    Scaffold(
        topBar = {
            TopNav(R.string.profile_title, navController = navController)
        }, bottomBar = {
            BottomNav(Screen.Profile.route, navController, isClicked = isPressed) {
                isPressed = !isPressed
            }
        },
        containerColor = Color.White
    ) {
        BottomModalSheet(
            sheetState = sheetStateLihat,
            title = R.string.lihat_slide_up_title,
            action1 = R.string.materi_title,
            onClickAct1 = { navController.navigate(Screen.GuruMateri.route) },
            action2 = R.string.latihan,
            onClickAct2 = { navController.navigate(Screen.GuruLatihan.route) },
            action3 = R.string.contoh_reaksi_icon_bottomsheet,
            onClickAct3 = { navController.navigate(Screen.GuruContohReaksi.route) }) {
            if (uniqueId.isNotEmpty()) {
                ScreenContent(
                    modifier = Modifier.padding(it),
                    navController,
                    currentUser,
                    uniqueId,
                    profileViewModel,
                    authViewModel,
                    focusManager
                )
            } else {
                LoadingState()
            }
        }
    }
}


@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    user: User,
    nipOrNisn: String,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel,
    focusManager: FocusManager
) {
    val context = LocalContext.current

    var fullname by remember { mutableStateOf(user.fullName) }
    var username by remember { mutableStateOf(user.username) }
    var email by remember { mutableStateOf(user.email) }
    var uniqueId by remember { mutableStateOf(nipOrNisn) }
    var school by remember { mutableStateOf(user.school) }
    var newPassword by remember { mutableStateOf("") }
    var readOnly by remember { mutableStateOf(true) }
    var oldPassword by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordVisibility2 by remember { mutableStateOf(false) }

    var profilePicture by remember { mutableStateOf(user.profilePicture ?: "") }

    var showImgDialog by remember { mutableStateOf(false) }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(CropImageContract()) {
        bitmap = getCroppedImage(context.contentResolver, it)
    }

    val apiStatus by profileViewModel.apiStatus.collectAsState()
    val errorMsg by profileViewModel.errorMsg.collectAsState()

    when (apiStatus) {
        ApiStatus.LOADING -> {
            Toast.makeText(context, GenericMessage.loadingMessage, Toast.LENGTH_SHORT).show()
        }

        ApiStatus.SUCCESS -> {
            showDialog = true
            profileViewModel.clearStatus()
        }

        ApiStatus.FAILED -> {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            profileViewModel.clearErrorMsg()
        }

        ApiStatus.IDLE -> null
    }
    val imeState by rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(imeState) {
        if (imeState) {
            scrollState.animateScrollTo(scrollState.maxValue, animationSpec = tween(300))
        }
    }
    Column(
        modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bitmap != null) {
                    bitmap?.let { bmp ->
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(108.dp)
                                .clickable {
                                    showImgDialog = true
                                },
                            bitmap = bmp.asImageBitmap(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Profile Picture"
                        )
                        EditPfp(readOnly = readOnly, launcher = launcher)
                        if (showImgDialog) {
                            ImageDialog(bitmap = bmp, context = context) {
                                showImgDialog = false
                            }
                        }
                    }
                } else if (profilePicture.isNotEmpty()) {
                    val imageUrl = ApiService.getMyPfp(user.userId)
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .diskCachePolicy(CachePolicy.DISABLED)
                            .memoryCachePolicy(CachePolicy.DISABLED)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.loading_img),
                        error = painterResource(id = R.drawable.broken_image),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(108.dp)
                            .clickable {
                                showImgDialog = true
                            }
                    )
                    if (showImgDialog) {
                        ImageDialog(imageUrl = imageUrl, context = context) {
                            showImgDialog = false
                        }
                    }
                    EditPfp(readOnly = readOnly, launcher = launcher)
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.profile_big),
                        contentDescription = "Profile Picture"
                    )
                    EditPfp(readOnly = readOnly, launcher = launcher)
                }
            }
        }
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
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, fontFamily = Poppins),
                value = email,
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
            if (user.userType == 0) {
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
            if (!readOnly) {
                RegularText(
                    text = "${stringResource(id = R.string.password_label)} Lama",
                    fontWeight = FontWeight.SemiBold
                )
                TextField(
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = Poppins
                    ),
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.None,
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
                    modifier = if (readOnly) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .testTag("PASSWORD_FIELD")
                    },
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

                RegularText(
                    text = stringResource(R.string.edit_password_label),
                    fontWeight = FontWeight.SemiBold
                )
                TextField(
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = Poppins
                    ),
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.None,
                        imeAction = ImeAction.Done,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("NEW_PASSWORD_FIELD"),
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
                        onClick = { readOnly = false },
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
                        PopUpDialog(
                            onDismiss = { showLogoutDialog = false },
                            icon = R.drawable.log_out_blue,
                            "Anda yakin ingin keluar?"
                        ) {
                            showLogoutDialog = false
                            CoroutineScope(Dispatchers.IO).launch {
                                authViewModel.logout()
                            }
                            navController.navigate(Screen.Landing.route) {
                                popUpTo(Screen.Landing.route)
                            }
                        }
                    }
                } else {
                    Button(
                        onClick = { readOnly = true },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RedButton,
                        ),
                        contentPadding = PaddingValues(horizontal = 42.dp, vertical = 12.dp)
                    ) {
                        RegularText(
                            text = stringResource(R.string.cancel_button),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            if (email == "") {
                                Toast.makeText(
                                    context,
                                    "Email tidak boleh kosong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            if (oldPassword == "") {
                                Toast.makeText(
                                    context,
                                    "Password Lama tidak boleh kosong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            if (newPassword == "") {
                                Toast.makeText(
                                    context,
                                    "Password Baru tidak boleh kosong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button

                            }
                            if (isInternetAvailable(context)) {
                                profileViewModel.update(
                                    user.userId,
                                    oldPassword,
                                    newPassword,
                                    email,
                                    bitmap
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    GenericMessage.noInternetError,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
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
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (showDialog) {
                SaveUpdatePopup(
                    onDismiss = { showDialog = false },
                    navController,
                    { readOnly = it }
                ) {
                    showDialog = false
                    passwordVisibility = false
                    passwordVisibility2 = false
                    oldPassword = ""
                    newPassword = ""
                }
            }
        }
    }
}

@Composable
fun EditPfp(
    modifier: Modifier = Modifier,
    readOnly: Boolean,
    launcher: ManagedActivityResultLauncher<CropImageContractOptions, CropImageView.CropResult>
) {
    Spacer(modifier = Modifier.height(8.dp))
    if (!readOnly) {
        Button(
            colors = buttonColors(containerColor = GrayTextField),
            shape = RoundedCornerShape(50.dp),
            onClick = {
                val options = CropImageContractOptions(
                    null, CropImageOptions(
                        imageSourceIncludeGallery = true,
                        imageSourceIncludeCamera = true,
                        fixAspectRatio = true
                    )
                )
                launcher.launch(options)
            }) {
            SmallText(text = "Edit Foto", color = Color.Black)
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
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF6D6E6F),
                fontFamily = Poppins
            ),
            shape = RoundedCornerShape(5.dp),
            readOnly = readOnly,
            value = value,
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
private fun SaveUpdatePopup(
    onDismiss: () -> Unit,
    navController: NavHostController,
    onChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
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
                        onClick = {
                            onChange(true)
                            onClick()
                        },
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ProfileScreenPrev() {
//    ProfileScreen(rememberNavController())
}