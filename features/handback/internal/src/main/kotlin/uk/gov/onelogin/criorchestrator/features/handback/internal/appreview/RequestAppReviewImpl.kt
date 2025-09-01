package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class)
class RequestAppReviewImpl
    @Inject
    constructor(
        private val configStore: ConfigStore,
        private val androidRequestAppReview: Provider<AndroidRequestAppReview>,
        private val debugRequestAppReview: Provider<DebugRequestAppReview>,
    ) : RequestAppReview {
        override suspend fun invoke() =
            when (configStore.readSingle(SdkConfigKey.DebugAppReviewPrompts).value) {
                true -> debugRequestAppReview().invoke()
                false -> androidRequestAppReview().invoke()
            }
    }
