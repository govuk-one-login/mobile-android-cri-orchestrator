package uk.gov.onelogin.criorchestrator.features.session.internal

import io.gatehill.imposter.openapi.embedded.OpenApiImposterBuilder
import io.gatehill.imposter.openapi.embedded.OpenApiMockEngine

class Imposter {
    val path =
        System
            .getProperty("user.dir")
            ?.substring(
                0,
                System.getProperty("user.dir")!!.indexOf("mobile-android-cri-orchestrator"),
            ) + "mobile-android-cri-orchestrator/config/imposter"

    class OpenApiImposterBuilderImpl : OpenApiImposterBuilder<OpenApiMockEngine, OpenApiImposterBuilderImpl>()

    fun createImposter(): OpenApiMockEngine =
        OpenApiImposterBuilderImpl()
            .withConfigurationDir(path)
            .startBlocking()
}
