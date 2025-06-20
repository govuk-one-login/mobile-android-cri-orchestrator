package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.features.session.internal.SessionReader
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSession
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorSingletonScope::class)
class RefreshActiveSessionImpl
    @Inject
    constructor(
        private val sessionReader: SessionReader,
        private val sessionStore: SessionStore,
    ) : RefreshActiveSession {
        override suspend fun invoke() {
            val result = sessionReader.isActiveSession()

            when (result) {
                is SessionReader.Result.IsActive -> sessionStore.write(result.session)
                SessionReader.Result.IsNotActive -> sessionStore.updateToDocumentSelected()
                SessionReader.Result.Unknown -> {
                    // Couldn't determine the state of the session; do nothing.
                }
            }
        }
    }
