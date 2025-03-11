package uk.gov.onelogin.criorchestrator.features.config.publicapi

/**
 * Convenience object that provides a dummy value for the backend asynchronous base URL.
 */
object StubConfig {
    const val BACKEND_ASYNC_URL_TEST_VALUE = "https://test.backend.url"

    fun provideConfig() =
        ConfigProvider(
            backendAsyncUrl = BACKEND_ASYNC_URL_TEST_VALUE,
        )
}
