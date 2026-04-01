package uk.gov.onelogin.criorchestrator.sdk.internal.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import java.time.Clock

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object ClockBindings {
    @Provides
    fun clock(): Clock = Clock.systemDefaultZone()
}
