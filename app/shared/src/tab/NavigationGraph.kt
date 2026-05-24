package tab

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import components.LargeSpacing
import components.MediumSpacing
import components.SmallSpacing
import tab.TabNavigationItem


@Composable
fun Navigation() {
    TabNavigator(HomeTab) {
        Scaffold(
//            topBar = { AppBar() },
            bottomBar = {
                NavigationBar(modifier = Modifier.height(LargeSpacing + SmallSpacing)) {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(ProfileTab)
                    TabNavigationItem(SettingTab)
                }

            }) {
            CurrentTab()
        }
    }
}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { tab.options.icon?.let { Icon(it, contentDescription = null) } },
        label = { Text(tab.options.title) }
    )
}