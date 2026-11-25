package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.filters.androidUnitTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.doesNotImportJUnit4
import uk.gov.onelogin.criorchestrator.konsisttest.filters.doesNotImportJUnit5
import uk.gov.onelogin.criorchestrator.konsisttest.filters.instrumentationTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.moleculePaparazziUnitTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.screenshotUnitTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.unitTestFiles

class JUnitImportsKonsistTest {
    @Test
    fun `unit tests do not use JUnit 4`() {
        unitTestFiles.assertTrue {
            it.doesNotImportJUnit4()
        }
    }

    @Test
    fun `local ui tests do not use JUnit 5`() {
        androidUnitTestFiles.assertTrue {
            it.doesNotImportJUnit5()
        }
    }

    @Test
    fun `screenshot tests do not use JUnit 5`() {
        screenshotUnitTestFiles.assertTrue {
            it.doesNotImportJUnit5()
        }
    }

    @Test
    fun `android tests do not use JUnit 5`() {
        instrumentationTestFiles.assertTrue {
            it.doesNotImportJUnit5()
        }
    }

    // https://github.com/cashapp/paparazzi/issues/1149
    @Test
    fun `molecule paparazzi compat tests do not use JUnit 5`() {
        moleculePaparazziUnitTestFiles.assertTrue {
            it.doesNotImportJUnit5()
        }
    }
}
