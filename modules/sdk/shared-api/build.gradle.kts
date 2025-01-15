import com.android.build.api.dsl.LibraryExtension
import uk.gov.onelogin.criorchestrator.extensions.setNamespace

plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.ui-config",
    ).forEach {
        id(it)
    }
}

configure<LibraryExtension> {
    setNamespace(suffix = ".sdk.sharedApi")
}

// TODO(DCMAW-10843): write meaningful description
mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator SDK Shared API",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator coordinates identity proofing capability.
            """.trimIndent(),
        )
    }
}
