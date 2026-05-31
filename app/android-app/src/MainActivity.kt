package hello.world

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import di.getDI
import getMain


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDI()
        setContent {
            getMain()
        }
    }
}