package hello.world

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import getDI
import getMain
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import viewmodel.MainActivityUiState
import viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDI()
        setContent {
            getMain()
        }
    }
}


//class MainActivity : ComponentActivity() {
//    private val viewModel: MainActivityViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()
//        super.onCreate(savedInstanceState)
//        getDI()
//
//        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.onEach { uiState = it }.collect()
//            }
//        }
//
//        splashScreen.setKeepOnScreenCondition {
//            uiState == MainActivityUiState.Loading
//        }
//
//        setContent {
//            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                val authState = viewModel.uiState.collectAsStateWithLifecycle(null)
//                getMain()
//            }
//
//        }
//    }
//}