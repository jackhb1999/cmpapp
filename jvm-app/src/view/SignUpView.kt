package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import components.ButtonHeight
import components.CustomTextField
import components.LargeSpacing
import components.MediumSpacing
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.SignUpViewModel

@Composable
@Preview
fun SignUpView(
    gotoLogin: () -> Unit,
    vm: SignUpViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val toaster = rememberToasterState()
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                label = "邮箱",
                value = vm.uiState.email,
                onValueChange = vm::updateEmail,
            )

            CustomTextField(
                label = "密码",
                value = vm.uiState.password,
                onValueChange = vm::updatePassword,
                isPasswordTextField = true,
            )

            Button(
                onClick = {
                    vm.signUp()
                },
                modifier = modifier.fillMaxWidth().height(ButtonHeight),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                ),
                shape = MaterialTheme.shapes.medium,

                ) {
                Text("用户注册")
            }

            Spacer(modifier = Modifier.padding(6.dp))
            Toaster(state = toaster)
        }
        if (vm.uiState.isAuthenticating) {
            CircularProgressIndicator()
        }
    }
    LaunchedEffect(
        key1 = vm.uiState.authenticationSucceed,
        key2 = vm.uiState.authErrorMessage,
        block = {
            if (vm.uiState.authenticationSucceed) {
                // 跳转登录页面
                gotoLogin()
            }

            if (vm.uiState.authErrorMessage != null) {
                // 显示错误信息
                toaster.show(
                    message = vm.uiState.authErrorMessage!!,
                    type = ToastType.Error,
                    duration = ToasterDefaults.DurationLong
                )
            }
        })
}