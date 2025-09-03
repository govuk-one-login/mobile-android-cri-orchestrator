package uk.gov.onelogin.criorchestrator

import uk.gov.onelogin.criorchestrator.extensions.customisePublications
import uk.gov.onelogin.criorchestrator.extensions.modulePathAsArtifactId
import uk.gov.onelogin.criorchestrator.extensions.modulePathAsGroupId

listOf(
    "uk.gov.publishing.config"
).forEach {
    project.plugins.apply(it)
}

configure<PublishingExtension> {
    customisePublications {
        groupId = project.modulePathAsGroupId()
        artifactId = project.modulePathAsArtifactId()
    }
}
