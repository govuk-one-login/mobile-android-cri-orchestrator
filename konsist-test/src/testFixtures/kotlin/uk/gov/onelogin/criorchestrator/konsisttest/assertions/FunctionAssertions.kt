package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.verify.assertFalse

/**
 * Assertion against functions collected by `konsist`.
 *
 * Validates that the functions have a defined, non-public visibility modifier.
 */
fun List<KoFunctionDeclaration>.areNotVisible(additionalMessage: String? = null, testName: String? = null) =
    assertFalse(
        additionalMessage = additionalMessage,
        testName = testName,
    ) { function ->
        function.hasPublicOrDefaultModifier
    }
