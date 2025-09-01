package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.journeyType
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class, binding = binding<GetJourneyType>())
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
                    .first() ?: error("Session is not set")

            return session.journeyType
        }
    }
