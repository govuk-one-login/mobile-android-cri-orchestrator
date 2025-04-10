package uk.gov.onelogin.criorchestrator.libraries.di

import javax.inject.Scope

/**
 * Dagger scope for objects that live no longer than the Compose UI composition.
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class CompositionScope
