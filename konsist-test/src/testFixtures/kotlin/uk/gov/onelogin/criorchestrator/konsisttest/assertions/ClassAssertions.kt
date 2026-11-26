package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue

fun List<KoClassDeclaration>.assertHasNameEndingWith(
    suffix: String,
    additionalMessage: String? = null,
    strict: Boolean = false,
    testName: String? = null,
) = assertTrue(
    additionalMessage = additionalMessage,
    strict = strict,
    testName = testName,
) {
    it.hasNameEndingWith(suffix)
}
