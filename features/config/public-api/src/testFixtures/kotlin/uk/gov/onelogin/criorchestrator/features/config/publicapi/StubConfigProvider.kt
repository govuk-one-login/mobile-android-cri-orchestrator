package uk.gov.onelogin.criorchestrator.features.config.publicapi

class StubConfigProvider : ConfigProvider {
    companion object {
        const val BACKEND_ASYNC_URL_TEST_KEY = "backendAsyncUrl"
        const val BACKEND_ASYNC_URL_TEST_VALUE = "https://test.backend.url"
    }

    override val configMap: MutableMap<String, Any>
        get() =
            mutableMapOf(
                BACKEND_ASYNC_URL_TEST_KEY to BACKEND_ASYNC_URL_TEST_VALUE,
            )
}
