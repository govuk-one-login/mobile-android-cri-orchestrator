package uk.gov.onelogin.criorchestrator.testwrapper

import android.content.res.Resources
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigProvider

class TestWrapperConfig(
    resources: Resources,
) : ConfigProvider {
    override val configMap =
        mutableMapOf<String, Any>(
            "backendAsyncUrl" to resources.getString(R.string.backendAsyncUrl),
        )
}
