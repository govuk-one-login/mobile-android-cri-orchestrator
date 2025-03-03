package uk.gov.onelogin.criorchestrator.features.config.internal

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl

/**
 * Convenience object that provides a dummy value for the backend asynchronous base URL.
 */
object StubConfig {
    const val ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE = "https://test.backend.url"

    /**
     * Convenience object that provides a dummy value for the backend asynchronous base URL.
     */
    val config: Config =
        Config(
            entries =
                persistentListOf(
                    Config.Entry<Config.Value.StringValue>(
                        key = IdCheckAsyncBackendBaseUrl,
                        value =
                            Config.Value.StringValue(
                                ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE,
                            ),
                    ),
                ),
        )
}
