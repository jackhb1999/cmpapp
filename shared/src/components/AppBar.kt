package components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppBar(
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.current
    val key = navigator?.lastItem.let { if (it is Tab) it.options.title else it?.key ?: "null" }
    Surface(
        modifier = modifier,
    ) {
        TopAppBar(
            title = { key?.let { Text(it) } },
            modifier = modifier,
            actions = {
                AnimatedVisibility(visible = key == "Home") {
                    IconButton(
                        onClick = { /* 处理点击事件 */ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "菜单"
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        // 处理返回按钮点击事件
                        navigator?.pop()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "返回"
                    )
                }
            }
        )
    }
}
