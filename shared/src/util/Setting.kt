package util

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings


expect class SettingsWrapper {
    @OptIn(ExperimentalSettingsApi::class)
    fun createSettings(): FlowSettings
}