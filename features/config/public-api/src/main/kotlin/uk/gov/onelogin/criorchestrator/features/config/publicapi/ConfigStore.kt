package uk.gov.onelogin.criorchestrator.features.config.publicapi

interface ConfigStore {
    fun readValueFromKey(key: String): Any

    fun writeProvidedConfig(configProvider: ConfigProvider)
}
