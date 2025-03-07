package uk.gov.onelogin.criorchestrator.extensions

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.withType
import uk.gov.publishing.MavenPublishingConfigPlugin

/**
 * Customise all Maven publications that have already been registered using
 * [MavenPublishingConfigPlugin].
 *
 * @param[config] Configuration applied to each Maven publication found.
 */
fun PublishingExtension.customisePublications(config: MavenPublication.() -> Unit) {
    publications {
        this.withType<MavenPublication>().forEach(config)
    }
}

// https://govukverify.atlassian.net/browse/DCMAW-11888
// https://github.com/Kotlin/dokka/issues/2956
internal fun Project.disableJavadocGeneration() {
    tasks
        .matching { task ->
            task.name.contains("javaDocReleaseGeneration") ||
                    task.name.contains("javaDocDebugGeneration")
        }.configureEach {
            enabled = false
        }
}
