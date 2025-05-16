package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.journeyType
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

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
                    .first() ?: error("Session is not set")

            return session.journeyType
        }
    }
