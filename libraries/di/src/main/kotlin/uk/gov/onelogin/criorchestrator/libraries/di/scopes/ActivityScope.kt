package uk.gov.onelogin.criorchestrator.libraries.di.scopes

import javax.inject.Scope

/**
 * Dagger scope intended for use at Activity level.
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
