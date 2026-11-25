package uk.gov.onelogin.criorchestrator.konsisttest.scopes

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.container.KoScopeCreator
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.ext.list.classes
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


/**
 * @return All classes that have a package matching [packageRegex].
 */
fun KoScope.classesWithinRoot(packageRegex: Regex): List<KoClassDeclaration> = files
    .withPackageMatching(packageRegex)
    .classes()

/**
 * @return a List of kotlin functions that have the `@Preview` annotation.
 */
fun KoScope.onComposablePreviewAnnotations() = functions()
    .withComposablePreviewAnnotations()