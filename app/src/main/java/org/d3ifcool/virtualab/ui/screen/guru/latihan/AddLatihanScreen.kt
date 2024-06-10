package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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

@Composable
fun AddLatihanScreen(navController: NavHostController) {
    var judulLatihan by remember { mutableStateOf("") }

    var jumlahSoal by remember { mutableStateOf("") }


    Scaffold (
        topBar = {
            TopNav(title = R.string.add_materi_title, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.GuruDashboard.route, navController = navController)
        }
    ){
        ScreenContent(
            modifier = Modifier.padding(it),
            judulLatihan = judulLatihan,
            onTitleChange = { judulLatihan = it },
            jumlahSoal = jumlahSoal,
            onSoalChange = { jumlahSoal = it },
            navController = navController
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    judulLatihan: String,
    onTitleChange: (String) -> Unit,
    jumlahSoal: String,
    onSoalChange: (String) -> Unit,
    navController: NavHostController
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp, vertical = 24.dp)
    ) {
        RegularText(text = stringResource(R.string.title_latihan))
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            value = judulLatihan,
            onValueChange = { onTitleChange(it) },
            placeholder = R.string.title_latihan
        )
        RegularText(text = stringResource(R.string.difficulty_latihan))
        Spacer(modifier = Modifier.height(20.dp))
        DropdownForm()

        RegularText(text = stringResource(R.string.number_latihan))
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            value = jumlahSoal,
            onValueChange = { onSoalChange(it) },
            placeholder = R.string.number_latihan,
            isPhone = true
        )
        ExtraSmallText(
            text = stringResource(R.string.limit_latihan),
            color = GrayText
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = modifier.padding(12.dp),
                onClick = { navController.navigate(Screen.AddSoal.route) },
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
fun DropdownForm(){
    val options = listOf("Mudah", "Sedang", "Sulit")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }

//    var isFocused by remember { mutableStateOf(true) }
    var isClicked by remember { mutableIntStateOf(0) }


    Box (
        modifier = Modifier
            .padding(10.dp)
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
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
                value = if (isClicked != 0) selectedOptionText else "",
                onValueChange = {},
                label = { ExtraSmallText(text = stringResource(R.string.dropdown_list)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {  }
            ) {
                options.forEach {selectedOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectedOption) },
                        onClick = {
                            selectedOptionText = selectedOption
                            isClicked++
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
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
){
    TextField(
        modifier = modifier?.fillMaxWidth()
            ?: Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {onValueChange(it)},
        placeholder = { Text(text = stringResource(id = placeholder), color = GrayIco)},
        singleLine = modifier == null,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = GrayTextField,
            focusedContainerColor = GrayTextField
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if(isPhone) KeyboardType.Number else if (isNumber == true) KeyboardType.Text else KeyboardType.Text,
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