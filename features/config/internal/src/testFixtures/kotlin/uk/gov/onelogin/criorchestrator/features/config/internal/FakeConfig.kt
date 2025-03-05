package uk.gov.onelogin.criorchestrator.features.config.internal

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl

object FakeConfig {
    const val ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE = "https://test.backend.url"

    fun create(): Config =
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
