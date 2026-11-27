package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.provider.KoImportProvider
import com.lemonappdev.konsist.api.verify.assertTrue
import java.util.Collections.singleton
import kotlin.collections.plus
import uk.gov.onelogin.criorchestrator.konsisttest.filters.doesNotImport

/**
 * Assertion against files collected by `konsist`.
 *
 * Validates that the files do not import any of the [disallowedPackages].
 *
 * @see KoFileDeclaration.doesNotImport
 */
fun List<KoImportProvider>.assertDoesNotImport(
    additionalMessage: String? = null,
    testName: String? = null,
    disallowedPackages: Iterable<Regex>,
    strict: Boolean = false,
) = assertTrue(
    strict = strict,
    additionalMessage = additionalMessage,
    testName = testName,
) { imports ->
    imports.doesNotImport(disallowedPackages)
}

/**
 * Assertion against files collected by `konsist`.
 *
 * Validates that the files do not import any of the [disallowedPackages].
 *
 * @see KoFileDeclaration.doesNotImport
 */
fun List<KoImportProvider>.assertDoesNotImport(
    disallowedPackage: Regex,
    additionalMessage: String? = null,
    testName: String? = null,
    strict: Boolean = false,
    vararg disallowedPackages: Regex,
) = assertDoesNotImport(
    disallowedPackages = singleton(disallowedPackage) + disallowedPackages,
    additionalMessage = additionalMessage,
    testName = testName,
    strict = strict,
)
