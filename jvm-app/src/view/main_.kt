package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
@Preview
fun test_input() {
    Column {

        var username by remember { mutableStateOf("") }
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            leadingIcon = {
                Icon(
//                    imageVector = Icons.Default.Person,
                    imageVector = Icons.Default.Person,
                    contentDescription = "用户名图标",
                    tint = Color.Blue,
                )
            },
            label = { Text("用户名") },
            placeholder = { Text("请输入用户名") }, singleLine = true
        )

        Spacer(modifier = Modifier.padding(6.dp))
        var password by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "密码图标",
                    tint = Color.Blue,
                )
            },
            label = { Text("密码") },
            placeholder = { Text("请输入密码") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "密码图标",
                        tint = Color.Blue
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true
        )
    }
}

@Composable
@Preview
fun Add_count() {
    Column {
//        var count = remember { mutableStateOf(0) }
        // 使用委托
        var count by remember { mutableStateOf(0) }
        BasicText("$count")
        Button(onClick = { count++ }) {
            BasicText("增加")
        }
    }
}


@Composable
@Preview
fun Add_count_less(count: Int, onChange: () -> Unit) {
    Column {
        BasicText("${count}")
        Button(onClick = { onChange() }) {
            BasicText("改变")
        }
    }
}

@Composable
@Preview
fun LazyColumnSample() {
    val data: SnapshotStateList<Int> = remember {
        mutableStateListOf<Int>().apply {
            addAll(1..100)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.LightGray),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { data.add(data.size) }) { Text("add") }
                Button(onClick = { data.removeLast() }) { Text("remove") }
                Button(onClick = { data.clear() }) { Text("clear") }
            }
        }
        items(data) {
            Text("${it}", fontSize = 40.sp)
        }
    }
}

class MyViewModel : ViewModel() {

    var count by mutableStateOf(0)
        private set

    fun updateCount() {
        count++
    }

}


@Composable
@Preview
fun CountSample(
    count: Int,
    updateCount: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText("${count}")
        Button(onClick = updateCount) {
            BasicText("改变")
        }
    }

}

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),// 充满整个屏幕
            verticalArrangement = Arrangement.Center, // 水平居中
            horizontalAlignment = Alignment.CenterHorizontally, // 垂直居中
        ) {
            Text("我是世界", fontSize = 16.sp)

            BasicText("Hello, World!")
            Button(
                onClick = { println("Hello, World!") },
                modifier = Modifier.padding(32.dp)
            ) {
                BasicText("按钮")
            }
            Add_count()
            // 无状态
            var count by remember { mutableStateOf(0) }
            Add_count_less(count) {
                count += 2
            }

            test_input()
//
//            Column(
//                modifier = Modifier
//                    .padding(32.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                repeat(100) {
//                    Text("${it}", fontSize = 32.sp)
//                }
//            }

//            LazyColumnSample()

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            // 消息显示的位置
            SnackbarHost(hostState = snackbarHostState)
            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "这是一条消息",
                            actionLabel = "确认", duration = SnackbarDuration.Short
                        )
                    }
                },
                content = {
                    Text("这是一条消息")
                }
            )

        }
    }
}

@Composable
@Preview
//fun App2(myViewModel: MyViewModel) {
//fun App2() {
fun App2(myViewModel: MyViewModel = viewModel()) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),// 充满整个屏幕
            verticalArrangement = Arrangement.Center, // 水平居中
            horizontalAlignment = Alignment.CenterHorizontally, // 垂直居中
        ) {
//            val myViewModel: MyViewModel = viewModel() // 现在可以正常获取
            CountSample(
                count = myViewModel.count, updateCount = myViewModel::updateCount,
                modifier = Modifier
            )

//            Image(
//                painter = painterResource("drawable/19.gif"),
//                contentDescription = "Sample",
//                modifier = Modifier.fillMaxSize()
//            )
        }
    }
}

// 1. 创建自定义 ViewModelStoreOwner
class DesktopViewModelStoreOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {

        // 创建一个 ViewModelStoreOwner（例如使用 AndroidX 的 ViewModelStoreOwner）
//        val owner = DesktopViewModelStoreOwner()
//        val owner by remember { mutableStateOf(DesktopViewModelStoreOwner()) }
        val owner = remember { DesktopViewModelStoreOwner() }

        // 将其提供给 Compose 树
        CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
            MaterialTheme {
//                val myViewModel: MyViewModel = viewModel() // 现在可以正常获取
                // 使用你的 UI
//                App2(myViewModel)
                App2()
            }
        }

//        App()
    }
}