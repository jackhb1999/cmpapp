package components


import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource


@Composable
fun FollowsButton(
    modifier: Modifier = Modifier,
//    @StringRes text: Int,
    text: String,
    onClick: () -> Unit,
    isOutline: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = if (isOutline) {
            ButtonDefaults.outlinedButtonColors()
        } else {
            ButtonDefaults.buttonColors()
        },
        border = ButtonDefaults.outlinedButtonBorder(isOutline),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 0.dp
        ),

    ){
        Text(
//            text = stringResource(text),
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 12.sp)
        )
    }
}

