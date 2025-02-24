package uk.gov.onelogin.criorchestrator.features.config.publicapi

interface ConfigProvider {
    val configMap: MutableMap<String, Any>
}
