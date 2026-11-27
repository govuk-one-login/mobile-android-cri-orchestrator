package uk.gov.onelogin.criorchestrator.konsisttest

import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.assertHasNameEndingWith
import uk.gov.onelogin.criorchestrator.konsisttest.filters.SCREENSHOT_TEST_CLASS_NAME_SUFFIX
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.classesWithParent
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.classesWithPrefix
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

class ScreenshotTestKonsistTest {
    @Test
    fun `screenshot tests use ScreenshotTest suffix`() {
        defaultScope
            .classesWithPrefix("Paparazzi", ignoreCase = true)
            .assertHasNameEndingWith(SCREENSHOT_TEST_CLASS_NAME_SUFFIX)
    }

    @Test
    fun `screenshot test subclasses use ScreenshotTest suffix`() {
        defaultScope.classesWithParent {
            it.hasNameEndingWith(SCREENSHOT_TEST_CLASS_NAME_SUFFIX)
        }.assertHasNameEndingWith(SCREENSHOT_TEST_CLASS_NAME_SUFFIX)
    }
}
