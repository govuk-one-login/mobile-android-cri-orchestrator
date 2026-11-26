package uk.gov.onelogin.criorchestrator.konsisttest.assertions

import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import java.util.Collections.singleton
import uk.gov.onelogin.criorchestrator.konsisttest.filters.SCREENSHOT_TEST_CLASS_NAME_SUFFIX
import uk.gov.onelogin.criorchestrator.konsisttest.filters.featurePackageAny
import uk.gov.onelogin.criorchestrator.konsisttest.filters.featurePackageInternal
import uk.gov.onelogin.criorchestrator.konsisttest.filters.hasPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.filters.packagesDoNotContain

fun KoFileDeclaration.allClassesMatchAtLeastOneOf(validations: Iterable<KoClassDeclaration.() -> Boolean>): Boolean =
    classes().all { clazz ->
        validations.any { it(clazz) }
    }

fun KoFileDeclaration.allClassesMatchAtLeastOneOf(
    validation: KoClassDeclaration.() -> Boolean,
    vararg validations: KoClassDeclaration.() -> Boolean,
) = allClassesMatchAtLeastOneOf(singleton(validation) + validations)

fun List<KoFileDeclaration>.assertHasPackageMatching(regex: Regex) = assertTrue {
    it.hasPackageMatching(regex)
}

fun List<KoFileDeclaration>.assertAllClassesMatchAtLeastOneOf(
    additionalMessage: String? = null,
    testName: String? = null,
    strict: Boolean = false,
    validations: Iterable<KoClassDeclaration.() -> Boolean>,
) = assertTrue(
    additionalMessage = additionalMessage,
    strict = strict,
    testName = testName,
) { file ->
    file.allClassesMatchAtLeastOneOf(validations)
}

fun List<KoFileDeclaration>.assertDoesNotImport(
    additionalMessage: String? = null,
    testName: String? = null,
    disallowedPackages: Iterable<Regex>,
    strict: Boolean = false,
) = assertTrue(
    strict = strict,
    additionalMessage = additionalMessage,
    testName = testName,
) { file ->
    file.doesNotImport(disallowedPackages)
}

fun List<KoFileDeclaration>.assertDoesNotImport(
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

fun KoFileDeclaration.doesNotImport(disallowedPackages: Iterable<Regex>): Boolean = imports.none { import ->
    disallowedPackages.any { import.hasNameMatching(it) }
}

fun KoFileDeclaration.doesNotImport(disallowedPackage: Regex, vararg disallowedPackages: Regex) =
    doesNotImport(singleton(disallowedPackage) + disallowedPackages)
