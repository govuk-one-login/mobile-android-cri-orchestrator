package uk.gov.onelogin.criorchestrator.konsisttest.scopes

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.Konsist.scopeFromTest
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.container.KoScopeCreator
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.declaration.KoImportDeclaration
import com.lemonappdev.konsist.api.declaration.KoParentDeclaration
import com.lemonappdev.konsist.api.ext.list.classes
import com.lemonappdev.konsist.api.ext.list.withParent
import com.lemonappdev.konsist.api.ext.list.withProperty
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withComposablePreviewAnnotations
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withPackageMatching

/**
 * Scope for the whole project, excluding the `mobile-android-pipelines` submodule.
 */
fun KoScopeCreator.defaultScope(): KoScope = with(this) {
    scopeFromProject() - scopeFromDirectory("mobile-android-pipelines")
}

val defaultScope by lazy {
    Konsist.defaultScope()
}

val productionCodeScope by lazy {
    defaultScope - scopeFromTest()
}

/**
 * @return All classes that have a package matching [packageRegex].
 */
fun KoScope.classesWithinPackage(packageRegex: Regex): List<KoClassDeclaration> = filesWithinPackage(packageRegex)
    .classes()

/**
 * @param ignoreCase Whether the [prefix] is considered case-insensitive. Defaults to `false`.
 * @return A [List] of [KoClassDeclaration] objects that have a class name prefixed by [prefix].
 */
fun KoScope.classesWithPrefix(prefix: String, ignoreCase: Boolean = false): List<KoClassDeclaration> =
    classes().withProperty {
        it.hasNameStartingWith(prefix, ignoreCase = ignoreCase)
    }

fun KoScope.classesWithParent(indirectParents: Boolean = false, predicate: (KoParentDeclaration) -> Boolean) = classes()
    .withParent(indirectParents = indirectParents, predicate)

/**
 * @return All files that have a package matching [packageRegex].
 */
fun KoScope.filesWithinPackage(packageRegex: Regex): List<KoFileDeclaration> = files
    .withPackageMatching(packageRegex)

/**
 * @return a List of kotlin functions that have the `@Preview` annotation.
 */
fun KoScope.onComposablePreviewAnnotations() = functions()
    .withComposablePreviewAnnotations()
