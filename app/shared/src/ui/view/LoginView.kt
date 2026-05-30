package ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import org.koin.compose.viewmodel.koinViewModel
import ui.components.ButtonHeight
import ui.components.CustomTextField
import ui.components.LargeSpacing
import ui.components.MediumSpacing
import ui.viewmodel.LoginViewModel

@Composable
fun LoginView(
    gotoHome: () -> Unit,
    gotoSignUp: () -> Unit,
    vm: LoginViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val toaster = rememberToasterState()
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing),
            modifier = modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = LargeSpacing,
                    start = MediumSpacing,
                    end = MediumSpacing,
                    bottom = LargeSpacing,
                )
        ) {
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
                onClick = vm::signIn,
                modifier = modifier.fillMaxWidth().height(ButtonHeight),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 0.dp,
                ),
                shape = MaterialTheme.shapes.medium,

                ) {
                Text("用户登录")
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
            ) {
                Text(text = "还没有账号？", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "去注册",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = modifier.clickable { gotoSignUp() }
                )
            }
        }

        Spacer(modifier = Modifier.padding(6.dp))
        Toaster(state = toaster)

    }
    LaunchedEffect(
        key1 = vm.uiState.id,
        block = {
            if (vm.uiState.id.isNotEmpty()) {
                // 跳转登录页面
                gotoHome()
            }
        })

}