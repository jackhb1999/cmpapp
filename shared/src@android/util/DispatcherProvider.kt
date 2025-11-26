package util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class AndroidDispatcherProvider : DispatcherProvider {

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
}

internal actual fun provideDispatcher(): DispatcherProvider = AndroidDispatcherProvider()
