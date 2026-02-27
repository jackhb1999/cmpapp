package tab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object SettingTab : Tab {
    @Composable
    override fun Content() {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.fillMaxSize()
//        ){
//            BasicText("SettingTab")
//        }
        Card {
            Row(modifier = Modifier.fillMaxWidth()) {
                SelectionContainer {
                    Text(text = "什么的还是大红i阿黄都爱活动i啊活动i啊哈ihi哦吼i吼吼吼后ihi哦hi黑乎乎ik")
                }
            }

        }

    }

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 2u,
                title = "Setting",
                icon = null
            )
        }
}
