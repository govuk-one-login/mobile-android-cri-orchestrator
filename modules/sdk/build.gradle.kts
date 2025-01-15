import com.android.build.api.dsl.LibraryExtension
import uk.gov.onelogin.criorchestrator.extensions.setNamespace

plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.ui-config",
    ).forEach {
        id(it)
    }

    listOf(
        libs.plugins.compose.compiler,
    ).forEach {
        id(it.get().pluginId)
    }
}

configure<LibraryExtension> {
    setNamespace(suffix = ".sdk")
    buildFeatures {
        compose = true
    }
}

dependencies {
    listOf(
        libs.androidx.test.espresso.accessibility,
    ).forEach {
        androidTestImplementation(it)
    }
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator SDK",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator coordinates identity proofing capability.
            """.trimIndent(),
        )
    }
}
