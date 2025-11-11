package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.IdCheckWrapperConfigKey

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
                    Config.Entry<Config.Value.BooleanValue>(
                        key = SdkConfigKey.BypassIdCheckAsyncBackend,
                        value =
                            Config.Value.BooleanValue(false),
                    ),
                    Config.Entry<Config.Value.BooleanValue>(
                        key = IdCheckWrapperConfigKey.EnableManualLauncher,
                        value = Config.Value.BooleanValue(false),
                    ),
                    Config.Entry<Config.Value.BooleanValue>(
                        key = IdCheckWrapperConfigKey.ExperimentalComposeNavigation,
                        value = Config.Value.BooleanValue(false),
                    ),
                ),
        )
}
