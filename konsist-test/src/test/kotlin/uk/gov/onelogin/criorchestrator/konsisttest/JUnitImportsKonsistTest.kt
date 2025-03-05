package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.filters.doesNotImportJUnit4
import uk.gov.onelogin.criorchestrator.konsisttest.filters.doesNotImportJUnit5
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withAndroidTestSourceSet
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withMoleculePaparazziCompatTests
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withScreenshotTestClass
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withTestSourceSet
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withUiTestClass
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withUnitTestClass
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withoutMoleculePaparazziCompatTests
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

class JUnitImportsKonsistTest {
    @Test
    fun `unit tests do not use JUnit 4`() {
        Konsist
            .defaultScope()
            .files
            .withTestSourceSet()
            .withUnitTestClass()
            .withoutMoleculePaparazziCompatTests()
            .assertTrue {
                it.doesNotImportJUnit4()
            }
    }

    @Test
    fun `local ui tests do not use JUnit 5`() {
        Konsist
            .defaultScope()
            .files
            .withTestSourceSet()
            .withUiTestClass()
            .assertTrue {
                it.doesNotImportJUnit5()
            }
    }

    @Test
    fun `screenshot tests do not use JUnit 5`() {
        Konsist
            .defaultScope()
            .files
            .withTestSourceSet()
            .withScreenshotTestClass()
            .assertTrue {
                it.doesNotImportJUnit5()
            }
    }

    @Test
    fun `android tests do not use JUnit 5`() {
        Konsist
            .defaultScope()
            .files
            .withAndroidTestSourceSet()
            .assertTrue {
                it.doesNotImportJUnit5()
            }
    }

    // https://github.com/cashapp/paparazzi/issues/1149
    @Test
    fun `molecule paparazzi compat tests do not use JUnit 5`() {
        Konsist
            .defaultScope()
            .files
            .withTestSourceSet()
            .withMoleculePaparazziCompatTests()
            .assertTrue {
                it.doesNotImportJUnit5()
            }
    }
}
