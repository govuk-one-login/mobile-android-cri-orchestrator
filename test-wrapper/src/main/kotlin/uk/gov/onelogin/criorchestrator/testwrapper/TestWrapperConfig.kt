package uk.gov.onelogin.criorchestrator.testwrapper

import android.content.res.Resources
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigProvider

object TestWrapperConfig {
    fun provideConfig(resources: Resources) =
        ConfigProvider(
            backendAsyncUrl = resources.getString(R.string.backendAsyncUrl),
        )
}
