package view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dokar.sonner.ToastType
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import components.*
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.EditProfileViewModel


@Composable
fun EditProfileView(
    modifier: Modifier = Modifier,
    vm: EditProfileViewModel = koinViewModel(),
    userId: Int,
    onUploadSuccess: () -> Unit,
) {
    val toaster = rememberToasterState()
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            vm.uiState.profile != null -> {
                Column(
                    modifier = modifier.fillMaxSize()
                        .padding(ExtraLargeSpacing),
                    verticalArrangement = Arrangement.spacedBy(LargeSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        CircleImage(
                            modifier = modifier.size(120.dp),
                            imageUrl = vm.uiState.profile!!.profileUrl,
                            onClick = {}
                        )
                        IconButton(
                            onClick = {},
                            modifier = modifier.align(Alignment.BottomEnd)
                                .shadow(
                                    elevation = SmallElevation,
                                    shape = RoundedCornerShape(percent = 25)
                                ).background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(percent = 25)
                                ).size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = modifier.height(LargeSpacing))

                    CustomTextField(
                        value = vm.uiState.profile!!.name,
                        onValueChange = { vm.onNameChange(it) },
                        label = "请输入姓名"
                    )

                    BioTextField(value = vm.bioTextFieldValue, onValueChange = vm::onBioChange)

                    Button(
                        onClick = vm::uploadProfile,
                        modifier = modifier.fillMaxWidth().height(ButtonHeight),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "上传修改")
                    }
                }
            }

            vm.uiState.errorMessage != null -> {
                Column {
                    Text(
                        text = "报错了，加载不到个人信息！",
                        style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                    )
                    Button(
                        onClick = {vm.fetchProfile(userId)},
                        modifier = modifier.fillMaxWidth().height(ButtonHeight),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "重试")
                    }
                }
            }
        }
        if (vm.uiState.isLoading) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(key1 = Unit, block = { vm.fetchProfile(userId) })

    LaunchedEffect(key1 = vm.uiState.uploadSucceed,
        key2= vm.uiState.errorMessage,
        block = {
            if (vm.uiState.uploadSucceed) {
                onUploadSuccess()
            }
            if(vm.uiState.profile != null&&vm.uiState.errorMessage != null) {
                toaster.show(
                    message = vm.uiState.errorMessage!!,
                    type = ToastType.Error,
                    duration = ToasterDefaults.DurationLong
                )
            }
        })
}

@Composable
fun BioTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth().height(90.dp),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = "请输入", style = MaterialTheme.typography.bodyLarge)
        },
        shape = MaterialTheme.shapes.medium
    )

}