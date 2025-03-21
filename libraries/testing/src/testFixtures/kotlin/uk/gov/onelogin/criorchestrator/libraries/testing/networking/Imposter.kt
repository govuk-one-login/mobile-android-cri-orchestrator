package uk.gov.onelogin.criorchestrator.libraries.testing.networking

import io.gatehill.imposter.openapi.embedded.OpenApiImposterBuilder
import io.gatehill.imposter.openapi.embedded.OpenApiMockEngine

class Imposter {
    val currentDir: String? = System.getProperty("user.dir")
    val rootSubstring = "mobile-android-cri-orchestrator"
    val path =
        currentDir
            ?.substring(
                0,
                currentDir.lastIndexOf(rootSubstring) + rootSubstring.length,
            ) + "/config/imposter"

    class OpenApiImposterBuilderImpl : OpenApiImposterBuilder<OpenApiMockEngine, OpenApiImposterBuilderImpl>()

    fun createImposterBackend(): OpenApiMockEngine =
        OpenApiImposterBuilderImpl()
            .withConfigurationDir(path)
            .startBlocking()
}
