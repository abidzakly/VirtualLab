package org.d3ifcool.virtualab.ui.screen.guru.latihan

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ExtraSmallText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.GrayIco
import org.d3ifcool.virtualab.ui.theme.GrayText
import org.d3ifcool.virtualab.ui.theme.GrayTextField
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.utils.UserDataStore

@Composable
fun AddLatihanScreen(navController: NavHostController) {
    val viewModel: AddLatihanViewModel = viewModel()
    val latihan by viewModel.latihanData.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(latihan) {
        if (latihan != null) {
            navController.navigate(Screen.AddSoal.withId(latihan!!.exerciseId)) {
                popUpTo(Screen.GuruDashboard.route)
            }
        }
    }

    LaunchedEffect(errorMsg) {
        if (errorMsg != null) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            viewModel.clearErrorMsg()
        }
    }

    Scaffold(
        topBar = {
            TopNav(title = R.string.add_materi_title, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.GuruDashboard.route, navController = navController)
        }
    ) {
        ScreenContent(
            modifier = Modifier.padding(it),
            viewModel,
            context
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: AddLatihanViewModel,
    context: Context
) {
    val options = listOf("Mudah", "Sedang", "Sulit")
    var judulLatihan by remember { mutableStateOf("") }
    var jumlahSoal by remember { mutableStateOf("") }
//    var jmlSoalError by remember { mutableStateOf(false) }
    var onClicked by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    val dataStore = UserDataStore(LocalContext.current)
    val userId by dataStore.userIdFlow.collectAsState(-1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp, vertical = 24.dp)
    ) {
        RegularText(
            modifier = Modifier.testTag("Judul Latihan Title"),
            text = stringResource(R.string.title_latihan)
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            modifier = Modifier.testTag("Judul Latihan TF"),
            value = judulLatihan,
            onValueChange = { judulLatihan = it },
            placeholder = R.string.title_latihan
        )
        RegularText(text = stringResource(R.string.difficulty_latihan))
        Spacer(modifier = Modifier.height(20.dp))
        DropdownForm(selectedOptionText, options) { selectedOptionText = it }
        RegularText(text = stringResource(R.string.question_count))
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            modifier = Modifier.testTag("Jumlah Soal TF"),
            value = jumlahSoal,
            onValueChange = { jumlahSoal = it },
            placeholder = R.string.question_count,
            isPhone = true
        )
        ExtraSmallText(
            text = stringResource(R.string.limit_latihan),
            color = GrayText
        )
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onClicked = true
                    if (onClicked && jumlahSoal.isEmpty() || onClicked && judulLatihan.isEmpty() || onClicked && selectedOptionText.isEmpty()) {
                        Toast.makeText(context, "Semua data harus diisi, yaa", Toast.LENGTH_SHORT)
                            .show()
                        onClicked = false
                    } else {
                        viewModel.addLatihan(
                            judulLatihan,
                            selectedOptionText,
                            jumlahSoal.toInt(),
                            userId
                        )
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                )
            ) {
                RegularText(
                    text = stringResource(R.string.button_tambahSoal),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownForm(selectedText: String, options: List<String>, onChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }

    var isClicked by remember { mutableIntStateOf(0) }


    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.testTag("Dropdown Menu"), expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .onGloballyPositioned { coordinates ->
                        textFilledSize = coordinates.size.toSize()
                    },
                readOnly = true,
                value = if (isClicked != 0) selectedText else "",
                onValueChange = {},
                label = { ExtraSmallText(text = stringResource(R.string.dropdown_list)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                var nomor = 1
                options.forEach { selectedOption ->
                    DropdownMenuItem(
                        modifier = Modifier.testTag("Pilihan Menu $nomor"),
                        text = { Text(text = selectedOption) },
                        onClick = {
                            onChange(selectedOption)
                            isClicked++
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                    nomor++
                }
            }
        }
    }

}

@Composable
fun CustomTextField(
    modifier: Modifier? = Modifier,
    isNumber: Boolean? = false,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: Int,
    isPhone: Boolean = false
) {
    TextField(
        modifier = modifier?.fillMaxWidth()
            ?: Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = stringResource(id = placeholder), color = GrayIco) },
        singleLine = modifier == null,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = GrayTextField,
            focusedContainerColor = GrayTextField
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPhone) KeyboardType.Number else if (isNumber == true) KeyboardType.Text else KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun Prev() {
    AddLatihanScreen(navController = rememberNavController())
}