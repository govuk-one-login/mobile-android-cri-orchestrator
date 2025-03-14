package uk.gov.onelogin.criorchestrator.libraries.kotlinutils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class CoroutineDispatchers(
    val io: CoroutineDispatcher = Dispatchers.IO,
    val default: CoroutineDispatcher = Dispatchers.Default,
    val main: CoroutineDispatcher = Dispatchers.Main,
    val unconfined: CoroutineDispatcher = Dispatchers.Unconfined,
) {
    companion object {
        fun from(dispatcher: CoroutineDispatcher) =
            CoroutineDispatchers(
                io = dispatcher,
                default = dispatcher,
                main = dispatcher,
                unconfined = dispatcher,
            )
    }
}
