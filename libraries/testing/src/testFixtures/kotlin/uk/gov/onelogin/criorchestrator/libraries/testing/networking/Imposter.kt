package uk.gov.onelogin.criorchestrator.libraries.testing.networking

import io.gatehill.imposter.openapi.embedded.OpenApiImposterBuilder
import io.gatehill.imposter.openapi.embedded.OpenApiMockEngine

private const val CONFIG_DIR_PROPERTY = "uk.gov.onelogin.criorchestrator.imposterConfigDir"

object Imposter {
    fun createMockEngine(): OpenApiMockEngine =
        OpenApiImposterBuilderImpl()
            .withConfigurationDir(System.getProperty(CONFIG_DIR_PROPERTY))
            .startBlocking()
}

private class OpenApiImposterBuilderImpl : OpenApiImposterBuilder<OpenApiMockEngine, OpenApiImposterBuilderImpl>()
