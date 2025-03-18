package uk.gov.onelogin.criorchestrator.libraries.testing

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 * A JUnit 4 test rule that logs analytics events to a file in the build directory.
 *
 * See [TestReportAnalyticsLogger] for details.
 */
class ReportingAnalyticsLoggerRule : TestRule {
    val analyticsLogger = TestReportAnalyticsLogger()

    override fun apply(
        base: Statement,
        description: Description,
    ): Statement {
        val className = description.testClass.simpleName
        val methodName = description.methodName
        return object : Statement() {
            override fun evaluate() {
                analyticsLogger.prepareReport(
                    testClassName = className,
                    testMethodName = methodName,
                )
                base.evaluate()
                analyticsLogger.writeReport(
                    testClassName = className,
                    testMethodName = methodName,
                )
            }
        }
    }
}

/**
 * Analytics logger that can write events to a report file.
 *
 * Reports are written to the build/reports/analytics directory.
 */
class TestReportAnalyticsLogger : AnalyticsLogger {
    private var isEnabled = true
    val loggedEvents: MutableList<AnalyticsEvent> = mutableListOf()

    override fun logEvent(
        shouldLogEvent: Boolean,
        vararg events: AnalyticsEvent,
    ) {
        if (!isEnabled) {
            return
        }
        loggedEvents.addAll(events)
    }

    override fun setEnabled(isEnabled: Boolean) {
        this.isEnabled = isEnabled
    }

    fun prepareReport(
        testClassName: String,
        testMethodName: String,
    ) {
        reportFile(testClassName, testMethodName).bufferedWriter().use { writer ->
            writer.write("")
            writer.appendLine("test_class_name: $testClassName")
            writer.appendLine("test_method_name: $testMethodName")
        }
    }

    fun writeReport(
        testClassName: String,
        testMethodName: String,
    ) {
        val reportFile = reportFile(testClassName, testMethodName)
        BufferedWriter(FileWriter(reportFile, true)).use { writer ->
            writer.appendLine("total_events: ${loggedEvents.size}")
            writer.appendLine("events:")
            loggedEvents.forEachIndexed { index, event ->
                writer.appendLine("  - event_type: ${event.eventType}")
                writer.appendLine("    parameters:")
                event.parameters.forEach { (key, value) ->
                    writer.appendLine("      $key: $value")
                }
            }
        }
    }

    private fun reportFile(
        testClassName: String,
        testMethodName: String,
    ): File {
        val buildDir = File("build/reports/analytics")
        if (!buildDir.exists() && !buildDir.mkdirs()) {
            error("Failed to create build directory")
        }

        val testMethodNameEscaped =
            testMethodName
                .replace(" ", "-")
                .replace(",", "")
        val fileName = "$testClassName-$testMethodNameEscaped.yml"

        return File(buildDir, fileName)
    }
}
