package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoImportDeclaration
import com.lemonappdev.konsist.api.ext.list.withImport
import com.lemonappdev.konsist.api.provider.KoImportProvider
import java.util.Collections.singleton
import kotlin.collections.plus

fun KoImportProvider.doesNotImportJUnit5(): Boolean = hasAllImports { import -> !import.isJUnit5() }

fun KoImportProvider.doesNotImportJUnit4(): Boolean = hasAllImports { import -> !import.isJUnit4() }

fun KoImportDeclaration.isJUnit4() = hasNameStartingWith("org.junit") && !hasNameContaining("jupiter")

fun KoImportDeclaration.isJUnit5() = hasNameStartingWith("org.junit.jupiter")

/**
 * Assertion against a `konsist` object capable of importing.
 *
 * Validates that Importing objects don't contain any of the [disallowedPackages].
 */
fun KoImportProvider.doesNotImport(disallowedPackages: Iterable<Regex>): Boolean = imports.none { import ->
    disallowedPackages.any { import.hasNameMatching(it) }
}

/**
 * Assertion against a `konsist` object capable of importing.
 *
 * Validates that Importing objects don't contain any of the [disallowedPackages].
 */
fun KoImportProvider.doesNotImport(disallowedPackage: Regex, vararg disallowedPackages: Regex) =
    doesNotImport(singleton(disallowedPackage) + disallowedPackages)

fun <Importable : KoImportProvider> List<Importable>.withImportNamesMatching(regex: Regex): List<Importable> =
    withImport {
        it.hasNameMatching(regex)
    }
