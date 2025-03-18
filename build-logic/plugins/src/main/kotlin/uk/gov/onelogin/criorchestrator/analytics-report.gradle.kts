package uk.gov.onelogin.criorchestrator

/**
 * This plugin registers the `collectAnalyticsReports` task which
 * copies analytics reports to the root project build directory.
 *
 * It also configures the task to run after tests are completed.
 */

private val Project.reportDir
    get() =
        this.layout.buildDirectory.dir("reports/analytics")

val collectAnalyticsReports = tasks.register<Copy>("collectAnalyticsReports") {
    into(project.rootProject.reportDir)
    with(copySpec {
        from(project.reportDir)
        include("**/*.yml")
    })
}

tasks.withType<Test>().configureEach {
    finalizedBy(collectAnalyticsReports)
    outputs.dir(project.reportDir)
}

