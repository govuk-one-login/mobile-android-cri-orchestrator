package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.verify.assertFalse

fun List<KoFunctionDeclaration>.areNotVisible(
    additionalMessage: String? = null,
    testName: String? = null
) = assertFalse(
    additionalMessage = additionalMessage,
    testName = testName
) { function ->
    function.hasPublicOrDefaultModifier
}