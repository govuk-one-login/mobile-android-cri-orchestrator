package uk.gov.onelogin.criorchestrator.testwrapper

import android.content.res.Resources
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey

object TestWrapperConfig {
    fun provideConfig(resources: Resources) =
        Config(
            entries =
                persistentListOf(
                    Config.Entry(
                        key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                        Config.Value.StringValue(
                            value = resources.getString(R.string.backendAsyncUrl),
                        ),
                    ),
                    Config.Entry(
                        key = SdkConfigKey.BypassIdCheckAsyncBackend,
                        Config.Value.BooleanValue(
                            value = false,
                        ),
                    ),
                    Config.Entry(
                        key = NfcConfigKey.StubNcfCheck,
                        Config.Value.BooleanValue(
                            value = false,
                        ),
                    ),
                    Config.Entry(
                        key = NfcConfigKey.IsNfcAvailable,
                        Config.Value.BooleanValue(
                            value = false,
                        ),
                    ),
                ),
        )
}
