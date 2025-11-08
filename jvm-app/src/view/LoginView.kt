package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import components.ButtonHeight
import components.CustomTextField
import components.LargeSpacing
import components.MediumSpacing
import screen.HomeScreen
import viewmodel.LoginViewModel
import viewmodel.SignUpViewModel

@Composable
@Preview
fun LoginView(
    goAway:() -> Unit,
    vm: LoginViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LargeSpacing),
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colors.background
                } else {
                    MaterialTheme.colors.surface
                }
            )
            .padding(
                top = LargeSpacing,
                start = MediumSpacing,
                end = MediumSpacing,
                bottom = LargeSpacing,
            )
    ) {
        CustomTextField(
            label = "用户名",
            value = vm.uiState.username,
            onValueChange = vm::updateUsername,
        )

        CustomTextField(
            label = "密码",
            value = vm.uiState.password,
            onValueChange = vm::updatePassword,
            isPasswordTextField = true,
        )

        Button(
            onClick = goAway,
            modifier = modifier.fillMaxWidth().height(ButtonHeight),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
            ),
            shape = MaterialTheme.shapes.medium,

        ){
            Text("用户登录")
        }

    }
}