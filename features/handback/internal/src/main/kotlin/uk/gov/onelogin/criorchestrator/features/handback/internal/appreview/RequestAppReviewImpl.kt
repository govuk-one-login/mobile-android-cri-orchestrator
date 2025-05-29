package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Provider

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
                true -> debugRequestAppReview.get().invoke()
                false -> androidRequestAppReview.get().invoke()
            }
    }
