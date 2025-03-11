package uk.gov.onelogin.criorchestrator.features.config.publicapi

/**
 * Data class for configuration properties written to the [ConfigStore]. Each parameter of this
 * [ConfigProvider] is either a feature flag or a configuration property. When adding new feature
 * flags or configuration properties, add another parameter to the [ConfigProvider] and then create a
 * corresponding object in the sealed class [ConfigField].
 *
 * Also, ensure any testing or test fixtures configuration is updated with required values.
 *
 * @property backendAsyncUrl The backend base URL for asynchronous API calls.
 */
data class ConfigProvider(
    val backendAsyncUrl: String,
)

/**
 * Sealed class for the configuration stored in the [ConfigStore], enabling the types of data stored
 * in the [ConfigStore] to be constrained. Each object within this [ConfigField] class is either a
 * feature flag or configuration property. When adding new feature flags or configuration
 * properties, add another object to the [ConfigField] sealed class and then add a corresponding
 * parameter to the data class [ConfigProvider].
 *
 * Fields:
 *
 * [BackendAsyncUrl] - The backend base URL for asynchronous API calls.
 */
sealed class ConfigField {
    object BackendAsyncUrl : ConfigField()
}
