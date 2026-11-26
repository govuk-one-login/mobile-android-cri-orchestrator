package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.provider.KoPackageProvider
import com.lemonappdev.konsist.api.verify.assertTrue
import uk.gov.onelogin.criorchestrator.konsisttest.filters.hasPackageMatching

/**
 * Assertion against files collected by `konsist`.
 *
 * Validates that the provided [regex] matches the package associated with the list of files.
 *
 * @see KoFileDeclaration.hasPackageMatching
 */
fun List<KoPackageProvider>.assertHasPackageMatching(
    regex: Regex,
    additionalMessage: String? = null,
    strict: Boolean = false,
    testName: String? = null,
) = assertTrue(
    additionalMessage = additionalMessage,
    strict = strict,
    testName = testName
) { currentPackage ->
    currentPackage.hasPackageMatching(regex)
}
