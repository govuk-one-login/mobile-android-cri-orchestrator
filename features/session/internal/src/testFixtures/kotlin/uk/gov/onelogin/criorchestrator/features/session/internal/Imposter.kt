package uk.gov.onelogin.criorchestrator.features.session.internal

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

    fun createImposter(): OpenApiMockEngine =
        OpenApiImposterBuilderImpl()
            .withConfigurationDir(path)
            .startBlocking()
}
