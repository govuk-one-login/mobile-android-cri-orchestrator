package uk.gov.onelogin.criorchestrator.testwrapper

import android.content.res.Resources
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl

object TestWrapperConfig {
    fun provideConfig(resources: Resources) =
        Config(
            entries =
                persistentListOf(
                    Config.Entry(
                        key = IdCheckAsyncBackendBaseUrl,
                        Config.Value.StringValue(
                            value = resources.getString(R.string.backendAsyncUrl),
                        ),
                    ),
                ),
        )
}
