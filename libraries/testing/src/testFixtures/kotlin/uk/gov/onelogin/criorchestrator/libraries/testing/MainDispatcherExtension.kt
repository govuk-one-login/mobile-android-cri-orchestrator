package uk.gov.onelogin.criorchestrator.libraries.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * Use this extension in local tests that need the main dispatcher.
 *
 * This is useful when testing view models which are hardcoded to use the
 * main dispatcher.
 *
 * https://developer.android.com/kotlin/coroutines/test#setting-main-dispatcher
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherExtension(
    val mainDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : BeforeEachCallback,
    AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(mainDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
