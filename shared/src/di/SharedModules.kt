package di

import org.koin.dsl.module
import util.provideDispatcher


private val utilityModule = module {
    factory { provideDispatcher() }
}

fun getSharedModules() = listOf(utilityModule)
