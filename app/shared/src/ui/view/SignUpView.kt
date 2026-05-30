package ui.view

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import ui.components.ButtonHeight
import ui.components.CustomTextField
import ui.components.LargeSpacing
import ui.components.MediumSpacing
import org.koin.compose.viewmodel.koinViewModel
import ui.viewmodel.SignUpViewModel

@Composable
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
                onClick = {
                    vm.signUp()
                },
                modifier = modifier.fillMaxWidth().height(ButtonHeight),
                elevation = ButtonDefaults.elevatedButtonElevation(),
                shape = MaterialTheme.shapes.medium,

                ) {
                Text("用户注册")
            }

            Spacer(modifier = Modifier.padding(6.dp))
            Toaster(state = toaster)
        }

    }

}