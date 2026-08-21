// Apply snapshot test filtering logic after task graph is ready.
// Only apply this to modules that contain both unit tests and screenshot tests (e.g. sdk). Applying
// this filter to modules with only screenshot tests (e.g. ui-component) will result in no tests
// being discovered during unit test runs.
gradle.taskGraph.whenReady {
    project.tasks.withType<Test>().configureEach {
        val isSnapshotTask = gradle.startParameter.taskNames.any {
            it.contains("paparazzi", ignoreCase = true)
        }
        logger.lifecycle("🔍 Snapshot task detected: $isSnapshotTask")
        if (isSnapshotTask) {
            logger.lifecycle("📸 Running snapshot tests only in task: $name")
            filter {
                includeTestsMatching("**/*ScreenshotTest.class")
            }
        } else {
            logger.lifecycle("🚫 Excluding snapshot tests in task: $name")
            filter {
                excludeTestsMatching("**/*ScreenshotTest.class")
            }
        }
    }
}
