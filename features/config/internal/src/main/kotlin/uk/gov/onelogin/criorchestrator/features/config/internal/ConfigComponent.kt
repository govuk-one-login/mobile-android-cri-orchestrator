package uk.gov.onelogin.criorchestrator.features.config.internal

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@ContributesTo(CriOrchestratorSingletonScope::class)
interface ConfigComponent {
    fun configStore(): ConfigStore
}
