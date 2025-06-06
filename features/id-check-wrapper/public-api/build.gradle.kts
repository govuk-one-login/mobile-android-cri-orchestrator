plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    // If the consuming app has a dependency on the ID Check SDK hilt config, prefer that the
    // version is compatible with the version of the SDK used in this project.
    constraints {
        // This is just a constraint, not a dependency.
        api("uk.gov.onelogin.idcheck:hilt-config") {
            version {
                prefer(
                    libs.versions.gov.uk.idcheck
                        .get(),
                )
            }
        }
    }
    implementation(projects.features.config.publicApi)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator ID Check Wrapper public API",
        )
    }
}
