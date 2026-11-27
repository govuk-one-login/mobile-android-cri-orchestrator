package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.provider.KoClassProvider
import java.util.Collections.singleton
import kotlin.collections.plus

/**
 * Assertion against a file by `konsist`.
 *
 * Validates that every class within a file matches at least one of the [validations].
 */
fun KoClassProvider.allClassesMatchAtLeastOneOf(validations: Iterable<KoClassDeclaration.() -> Boolean>): Boolean =
    classes().all { clazz ->
        validations.any { it(clazz) }
    }

/**
 * Assertion against a file collected by `konsist`.
 *
 * Validates that every class within a file has at least one match when transformed into [Boolean]
 * results.
 *
 * Requires at least * one [validation]. Is capable of receiving many, due to the [validations]
 * `vararg`.
 */
fun KoClassProvider.allClassesMatchAtLeastOneOf(
    validation: KoClassDeclaration.() -> Boolean,
    vararg validations: KoClassDeclaration.() -> Boolean,
) = allClassesMatchAtLeastOneOf(singleton(validation) + validations)
