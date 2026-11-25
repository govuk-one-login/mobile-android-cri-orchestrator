package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoImportDeclaration
import com.lemonappdev.konsist.api.provider.KoImportProvider

fun KoImportProvider.doesNotImportJUnit5(): Boolean = hasAllImports { import -> !import.isJUnit5() }

fun KoImportProvider.doesNotImportJUnit4(): Boolean = hasAllImports { import -> !import.isJUnit4() }

fun KoImportDeclaration.isJUnit4() = hasNameStartingWith("org.junit") && !hasNameContaining("jupiter")

fun KoImportDeclaration.isJUnit5() = hasNameStartingWith("org.junit.jupiter")
