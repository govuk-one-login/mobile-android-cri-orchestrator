package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import uk.gov.onelogin.criorchestrator.konsisttest.filters.packagesDoNotContain

/**
 * Assertion against files collected by `konsist`.
 *
 * Validates that the files within [packagesToSearch] don't contain anything matching [disallowedPackage].
 *
 * @see KoFileDeclaration.packagesDoNotContain
 */
fun List<KoFileDeclaration>.assertPackagesDoNotContain(
    packagesToSearch: Regex,
    disallowedPackage: Regex,
    additionalMessage: String? = null,
    strict: Boolean = false,
    testName: String? = null,
) = assertTrue(
    additionalMessage = additionalMessage,
    strict = strict,
    testName = testName,
) { file ->
    file.packagesDoNotContain(
        packagesToSearch = packagesToSearch,
        disallowedPackages = disallowedPackage,
    )
}
