package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.provider.KoClassProvider
import com.lemonappdev.konsist.api.verify.assertTrue
import uk.gov.onelogin.criorchestrator.konsisttest.filters.allClassesMatchAtLeastOneOf

/**
 * Assertion against files collected by `konsist`.
 *
 * Validates that every class within a file matches at least one of the [validations].
 *
 * @see KoFileDeclaration.allClassesMatchAtLeastOneOf
 */
fun List<KoClassProvider>.assertAllClassesMatchAtLeastOneOf(
    additionalMessage: String? = null,
    testName: String? = null,
    strict: Boolean = false,
    validations: Iterable<KoClassDeclaration.() -> Boolean>,
) = assertTrue(
    additionalMessage = additionalMessage,
    strict = strict,
    testName = testName,
) { clazz ->
    clazz.allClassesMatchAtLeastOneOf(validations)
}
