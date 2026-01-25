package view

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button

import androidx.compose.material3.IconButton

import androidx.compose.material3.Text

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
import components.ButtonHeight
import components.CircleImage
import components.CustomTextField
import components.ExtraLargeSpacing
import components.LargeSpacing
import components.SmallElevation
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
                        .background(
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colors.background
                            } else {
                                MaterialTheme.colors.surface
                            }
                        )
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
                                    color = MaterialTheme.colors.surface,
                                    shape = RoundedCornerShape(percent = 25)
                                ).size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
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
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
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
                        style = MaterialTheme.typography.caption.copy(textAlign = TextAlign.Center),
                    )
                    Button(
                        onClick = {vm.fetchProfile(userId)},
                        modifier = modifier.fillMaxWidth().height(ButtonHeight),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
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
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colors.surface
            } else {
                Gray
            },
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = "请输入", style = MaterialTheme.typography.body2)
        },
        shape = MaterialTheme.shapes.medium
    )

}