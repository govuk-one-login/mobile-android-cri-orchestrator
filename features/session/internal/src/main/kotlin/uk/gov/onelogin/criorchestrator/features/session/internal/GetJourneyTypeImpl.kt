package uk.gov.onelogin.criorchestrator.features.session.internal

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.journeyType
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@ContributesBinding(CriOrchestratorScope::class, boundType = GetJourneyType::class)
class GetJourneyTypeImpl
    @Inject
    constructor(
        private val sessionStore: SessionStore,
    ) : GetJourneyType {
        @OptIn(FlowPreview::class)
        override suspend operator fun invoke(): JourneyType {
            val session =
                sessionStore
                    .read()
                    .filterNotNull()
                    .timeout(5.seconds)
                    .first()

            return session.journeyType
        }
    }
