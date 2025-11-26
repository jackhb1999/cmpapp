package di

import org.koin.dsl.module
import util.SettingsWrapper

val otherModule= module {
    single { SettingsWrapper().createSettings() }
}