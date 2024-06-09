package org.d3ifcool.virtualab.ui.screen.guru.latihan

import   androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.GrayIco
import org.d3ifcool.virtualab.ui.theme.GrayTextField
import org.d3ifcool.virtualab.ui.theme.LightBlue

@Composable
fun AddSoalScreen(navController: NavHostController) {
    var soal1 by remember { mutableStateOf("") }
    var soal2 by remember { mutableStateOf("") }
    var jawaban1 by remember { mutableStateOf("") }
    var jawaban2 by remember { mutableStateOf("") }
    var jawaban3 by remember { mutableStateOf("") }
    var jawaban4 by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopNav(title = R.string.add_soal_title, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.GuruDashboard.route, navController = navController)
        }
    ){
        ScreenContent(
            modifier = Modifier.padding(it),
            soal1 = soal1,
            onSoal1Change = { soal1 = it },
            soal2 = soal2,
            onSoal2Change = { soal2 = it },
            jawaban1 = jawaban1,
            onJawaban1Change = { jawaban1 = it },
            jawaban2 = jawaban2,
            onJawaban2Change = { jawaban2 = it },
            jawaban3 = jawaban3,
            onJawaban3Change = { jawaban3 = it },
            jawaban4 = jawaban4,
            onJawaban4Change = { jawaban4 = it }
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    soal1: String,
    onSoal1Change: (String) -> Unit,
    soal2: String,
    onSoal2Change: (String) -> Unit,
    jawaban1: String,
    onJawaban1Change: (String) -> Unit,
    jawaban2: String,
    onJawaban2Change: (String) -> Unit,
    jawaban3: String,
    onJawaban3Change: (String) -> Unit,
    jawaban4: String,
    onJawaban4Change: (String) -> Unit,
) {
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        RegularText(text = stringResource(R.string.title_soal))
        RegularText(text = stringResource(R.string.difficulty_soal))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            RegularText(
                text = stringResource(R.string.number_soal_1),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField2(
                value = soal1,
                onValueChange = { onSoal1Change(it) },
                placeholder = R.string.soal
            )
            Spacer(modifier = Modifier.height(16.dp))
            RegularText(text = stringResource(R.string.pilihan_jawaban))

            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban1,
                    onValueChange = { onJawaban1Change(it) },
                    placeholder = R.string.pilihan_1
                )
            }
            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban2,
                    onValueChange = { onJawaban2Change(it) },
                    placeholder = R.string.pilihan_2
                )
            }
            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban3,
                    onValueChange = { onJawaban3Change(it) },
                    placeholder = R.string.pilihan_3
                )
            }
            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban4,
                    onValueChange = { onJawaban4Change(it) },
                    placeholder = R.string.pilihan_4
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 2.dp
            )
            Spacer(modifier = Modifier.height(10.dp))
            RegularText(
                text = stringResource(R.string.number_soal_2),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField2(
                value = soal2,
                onValueChange = { onSoal2Change(it) } ,
                placeholder = R.string.soal
            )
            Spacer(modifier = Modifier.height(16.dp))
            RegularText(text = stringResource(R.string.pilihan_jawaban))

            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban1,
                    onValueChange = { onJawaban1Change(it) },
                    placeholder = R.string.pilihan_1
                )
            }
            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban2,
                    onValueChange = { onJawaban2Change(it) },
                    placeholder = R.string.pilihan_2
                )
            }
            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban3,
                    onValueChange = { onJawaban3Change(it) },
                    placeholder = R.string.pilihan_3
                )
            }
            Row (
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { isChecked = !isChecked }) {
                    Icon(
                        painterResource(if (isChecked) R.drawable.check_box_outline_blank else R.drawable.check_box_filled),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(0xFF0E4B9C)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                CustomTextField2(
                    value = jawaban4,
                    onValueChange = { onJawaban4Change(it) },
                    placeholder = R.string.pilihan_4
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 2.dp
            )
            Button(
                modifier = modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue,
                    contentColor = Color.Black
                )
            ) {
                RegularText(
                    text = stringResource(R.string.button_unggah),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CustomTextField2(
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
            keyboardType = if(isPhone) KeyboardType.Number else if (isNumber == true) KeyboardType.Text else KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}
@Preview
@Composable
private fun Prev() {
    AddSoalScreen(navController = rememberNavController())
}