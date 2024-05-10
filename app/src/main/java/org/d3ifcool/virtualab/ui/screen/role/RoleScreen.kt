package org.d3ifcool.virtualab.ui.screen.role

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.theme.DarkBlueText
import org.d3ifcool.virtualab.ui.theme.GrayText
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button),
                            tint = Color.Black
                        )
                    }
                },
                title = {

                },
                colors = topAppBarColors(Color.Transparent)
            )
        },
        containerColor = Color.White
        ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val guru by remember { mutableLongStateOf(0L) }
    val murid by remember { mutableLongStateOf(1L) }

    Column(
        modifier = modifier.fillMaxSize().
        verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(267.dp, 197.dp),
            painter = painterResource(id = R.drawable.image_role),
            contentDescription = stringResource(
                id = R.string.role_image
            )
        )
        Spacer(modifier = Modifier.padding(vertical = 32.5.dp))
        Text(
            modifier = Modifier.padding(bottom = 40.dp),
            text = stringResource(id = R.string.role_instruction),
            fontWeight = FontWeight.SemiBold,
            color = DarkBlueText,
            fontSize = 15.sp,
            fontFamily = Poppins
        )
        Button(
            onClick = { navController.navigate(Screen.Register.withId(guru)) }, modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(horizontal = 45.dp),
            colors = buttonColors(LightBlue, Color.Black),
            shape = RoundedCornerShape(10.dp)

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.teacher_role),
                    contentDescription = stringResource(
                        id = R.string.role_teacher
                    )
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.role_teacher),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontFamily = Poppins
                    )
                    Text(
                        text = stringResource(id = R.string.role_teacher_desc),
                        color = GrayText,
                        fontSize = 12.sp,
                        fontFamily = Poppins
                    )
                }
            }

        }
        Spacer(modifier = Modifier.padding(bottom = 15.dp))
        Button(
            onClick = { navController.navigate(Screen.Register.withId(murid)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(horizontal = 45.dp),
            colors = buttonColors(LightBlue, Color.Black),
            shape = RoundedCornerShape(10.dp)

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.student_role),
                    contentDescription = stringResource(
                        id = R.string.role_student
                    )
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.role_student),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontFamily = Poppins
                    )
                    Text(
                        text = stringResource(id = R.string.role_student_desc),
                        color = GrayText,
                        fontSize = 12.sp,
                        fontFamily = Poppins
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun RoleScreenPrev() {
    RoleScreen(rememberNavController())
}