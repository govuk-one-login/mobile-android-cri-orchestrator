package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.provider.KoNameProvider
import com.lemonappdev.konsist.api.verify.assertTrue

/**
 * Assertion against class files collected by `konsist`.
 *
 * Checks that a given class name ends with the provided [suffix].
 */
fun List<KoNameProvider>.assertHasNameEndingWith(
    suffix: String,
    additionalMessage: String? = null,
    strict: Boolean = false,
    testName: String? = null,
) = assertTrue(
    additionalMessage = additionalMessage,
    strict = strict,
    testName = testName,
) { name ->
    name.hasNameEndingWith(suffix)
}