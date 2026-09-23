package uk.gov.onelogin.criorchestrator.konsisttest.scopes

import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.container.KoScopeCreator

/**
 * Scope for the whole project, excluding the `mobile-android-pipelines` submodule.
 */
fun KoScopeCreator.defaultScope(): KoScope = with(this) {
    scopeFromProject() - scopeFromDirectory("mobile-android-pipelines")
}
