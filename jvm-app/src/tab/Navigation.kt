package tab

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator


@Composable
fun Navigation() {
    TabNavigator(HomeTab) {
        Scaffold(bottomBar = {
            BottomNavigation {
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
    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { tab.options.icon?.let { Icon(it, contentDescription = null) } },
        label = { Text(tab.options.title) }
    )
}