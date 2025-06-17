package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers

class FakeAbortSessionApiTest {
    val configStore = FakeConfigStore()
    private val testScope = TestScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    val fakeApi =
        FakeAbortSessionApi(
            configStore = configStore,
            coroutineDispatchers = CoroutineDispatchers.from(UnconfinedTestDispatcher(testScope.testScheduler)),
        )

    @Test
    fun `given config is success, it returns success`() =
        testScope.runTest {
            configStore.write(
                entry =
                    Config.Entry<Config.Value.StringValue>(
                        key = SdkConfigKey.BypassAbortSessionApiCall,
                        value =
                            Config.Value.StringValue(
                                SdkConfigKey.BypassAbortSessionApiCall.OPTION_SUCCESS,
                            ),
                    ),
            )
            val result = fakeApi.abortSession("sessionId")
            assertEquals(ApiResponse.Success(""), result)
        }

    @Test
    fun `given config is offline, it returns offline`() =
        testScope.runTest {
            configStore.write(
                entry =
                    Config.Entry<Config.Value.StringValue>(
                        key = SdkConfigKey.BypassAbortSessionApiCall,
                        value =
                            Config.Value.StringValue(
                                SdkConfigKey.BypassAbortSessionApiCall.OPTION_OFFLINE,
                            ),
                    ),
            )
            val result = fakeApi.abortSession("sessionId")
            assertEquals(ApiResponse.Offline, result)
        }

    @Test
    fun `given config is unrecoverable error, it returns unrecoverable error`() =
        testScope.runTest {
            configStore.write(
                entry =
                    Config.Entry<Config.Value.StringValue>(
                        key = SdkConfigKey.BypassAbortSessionApiCall,
                        value =
                            Config.Value.StringValue(
                                SdkConfigKey.BypassAbortSessionApiCall.OPTION_UNRECOVERABLE_ERROR,
                            ),
                    ),
            )
            val result = fakeApi.abortSession("sessionId")
            assertEquals(
                "Simulated exception",
                (result as ApiResponse.Failure).error.message,
            )
        }
}
