// Apply snapshot test filtering logic after task graph is ready.
// Only apply this to modules that contain both unit tests and screenshot tests (e.g. sdk). Applying
// this filter to modules with only screenshot tests (e.g. ui-component) will result in no tests
// being discovered during unit test runs.
gradle.taskGraph.whenReady {
    val snapshotKeywords = listOf("recordPaparazzi", "verifyPaparazzi")
    val isSnapshotTask = allTasks.any { task -> snapshotKeywords.any(task.name::contains) }
    logger.lifecycle("🔍 Snapshot task detected: $isSnapshotTask")

    project.tasks.withType(Test::class.java).configureEach {
        doFirst {
            if (isSnapshotTask) {
                logger.lifecycle("📸 Running snapshot tests only in task: $name")
                include("**/*ScreenshotTest.class")
            } else {
                logger.lifecycle("🚫 Excluding snapshot tests in task: $name")
                exclude("**/*ScreenshotTest.class")
            }
        }
    }
}
